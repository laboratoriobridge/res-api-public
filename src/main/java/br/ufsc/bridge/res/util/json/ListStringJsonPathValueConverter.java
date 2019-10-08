package br.ufsc.bridge.res.util.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ListStringJsonPathValueConverter implements JsonPathValueConverter<List<String>, String> {

	@Override
	public List<String> convert(String value) {
		List<String> values = new ArrayList<>();
		if (value != null) {
			for (String medicamento : value.split(";")) {
				if (StringUtils.isNotBlank(medicamento)) {
					values.add(medicamento);
				}
			}
		}
		return values;
	}

}
