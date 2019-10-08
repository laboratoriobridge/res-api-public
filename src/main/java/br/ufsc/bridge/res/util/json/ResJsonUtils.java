package br.ufsc.bridge.res.util.json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import br.ufsc.bridge.res.dab.dto.ResABResumoConsulta;
import br.ufsc.bridge.res.sumarioalta.dto.ResSumarioAlta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import net.minidev.json.JSONArray;

public class ResJsonUtils {

	private static Map<String, String> jsons = new HashMap<>();

	private static Map<Class<?>, List<SetDTO<?>>> config = new LinkedHashMap<>();

	public static DocumentContext getJsonDocument(String name) {
		return JsonPath.parse(jsons.get(name));
	}

	public static <T> T readJson(String jsonBase64, Class<T> clazz) {
		try {
			return readJsonInternal(JsonPath.parse(new String(Base64.decodeBase64(jsonBase64), "UTF-8")).json(), new SetDTO<>(clazz));
		} catch (Exception e) {
			throw new RuntimeException("erro ao carregar metadados da classe", e);
		}
	}

	static {
		try {
			readMetadata(ResABResumoConsulta.class);
			readMetadata(ResSumarioAlta.class);
		} catch (Exception e) {
			new RuntimeException("erro ao carregar metadados da classe", e);
		}
	}

	private static void readMetadata(Class<?> clazz) throws Exception {
		List<SetDTO<?>> classMetadata = new ArrayList<>();
		config.put(clazz, classMetadata);

		for (Field field : clazz.getDeclaredFields()) {
			if (field.isAnnotationPresent(JsonPathProperty.class)) {
				SetDTO<?> setDTO = new SetDTO<>(field, clazz);
				if (!setDTO.primitive) {
					readMetadata(setDTO.clazz);
				}
				classMetadata.add(setDTO);
			}
		}
	}

	private static <T> T readJsonInternal(Object value, SetDTO<T> field) throws Exception {
		if (value instanceof JSONArray) {
			if (!((JSONArray) value).isEmpty()) {
				value = ((JSONArray) value).get(0);
			} else {
				value = null;
			}
		}

		if (field.primitive) {
			return (T) value;
		}

		T returnOject = field.constructor.newInstance();
		DocumentContext document = JsonPath.parse(value);

		for (SetDTO<?> chieldField : config.get(field.clazz)) {
			JsonPathProperty annotation = chieldField.annotation;
			String jsonKey = annotation.group().getPath() + annotation.value();

			Object chieldValue = document.read(jsonKey);

			if (chieldField.list) {
				ArrayList values = new ArrayList();
				for (Object item : (JSONArray) chieldValue) {
					Object itemValue = readJsonInternal(item, chieldField);
					if (chieldField.converter != null) {
						itemValue = chieldField.converter.convert(itemValue);
					}
					values.add(itemValue);
				}
				chieldValue = values;
			} else {
				chieldValue = readJsonInternal(chieldValue, chieldField);
				if (chieldField.converter != null) {
					chieldValue = chieldField.converter.convert(chieldValue);
				}
			}

			if (chieldValue != null) {
				chieldField.method.invoke(returnOject, chieldValue);
			}
		}

		return returnOject;
	}

	static {
		try {
			putJson("resumo-consulta", null, null);
			putJson("procedimento-realizado", "procedimento", "$.items[0]");
			putJson("problema-diagnostico", "problema", "$.items[0]");
			putJson("prescricao-atendimento",
					"medicamento-nao-estruturado",
					"$.items.data.items[?(@.name.value == 'Medicamentos prescritos no atendimento (não estruturado)')]");

			putJson("medicoes-observacoes", null, null);
			putJson("dados-desfecho", "desfecho", "$.items.data.items[0]");
			putJson("caracterizacao-atendimento", "profissional", "$.items.data.items[?(@.name.value == 'Profissionais do atendimento')]");

			putJson("alergia-reacoes", "alergia", "$.items[0]");

			DocumentContext alergia = getJsonDocument("alergia");
			String eventoJson = new ObjectMapper().writeValueAsString(((JSONArray) alergia.read("$.data.items[?(@.name.value == 'Evento da reação')]")).get(0));
			alergia.delete("$.data.items[?(@.name.value == 'Evento da reação')]");
			jsons.put("alergia", alergia.jsonString());
			jsons.put("evento-alergia", eventoJson);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao carregar templates json", e);
		}
	}

	private static String putJson(String name, String childName, String childPath) throws IOException {
		InputStream resourceAsStream = ResJsonUtils.class.getClassLoader().getResourceAsStream("json.parties" + File.separator + name + ".json");
		String jsonValue = IOUtils.toString(resourceAsStream, Charset.forName("UTF-8"));
		if (childName != null) {
			DocumentContext document = JsonPath.parse(jsonValue);
			Object childObject = document.read(childPath);
			if (childObject instanceof JSONArray) {
				childObject = ((JSONArray) childObject).get(0);
			}
			jsons.put(childName, new ObjectMapper().writeValueAsString(childObject));
			document.delete(childPath);
			jsonValue = document.jsonString();
		}
		jsons.put(name, jsonValue);
		return jsonValue;
	}

	private static class SetDTO<T> {
		private JsonPathProperty annotation;
		private Method method;
		private boolean list;
		private boolean primitive;
		private Constructor<T> constructor;
		private Class<T> clazz;
		private JsonPathValueConverter converter;

		SetDTO(Class<T> clazz) throws NoSuchMethodException {
			this.clazz = clazz;
			this.constructor = clazz.getConstructor();
		}

		SetDTO(Field field, Class<?> fatherClass) throws Exception {
			this.clazz = (Class<T>) field.getType();
			this.method = fatherClass.getMethod("set" + StringUtils.capitalize(field.getName()), this.clazz);
			this.annotation = field.getAnnotation(JsonPathProperty.class);
			if (!DummyJsonPathValueConverter.class.isAssignableFrom(this.annotation.converter())) {
				this.converter = this.annotation.converter().newInstance();
			}

			this.primitive = this.clazz.isAssignableFrom(String.class) || this.clazz.isEnum() || Date.class.isAssignableFrom(this.clazz) || this.clazz.isPrimitive();

			if (!this.primitive) {
				if (Collection.class.isAssignableFrom(this.clazz)) {
					this.clazz = (Class<T>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
					this.list = true;
					this.primitive = this.clazz.isAssignableFrom(String.class) || this.clazz.isEnum() || Date.class.isAssignableFrom(this.clazz) || this.clazz.isPrimitive();
				}
				if (!this.primitive) {
					this.constructor = this.clazz.getConstructor();
				}
			}
		}
	}
}
