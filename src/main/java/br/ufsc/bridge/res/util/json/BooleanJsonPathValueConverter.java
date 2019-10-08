package br.ufsc.bridge.res.util.json;

public class BooleanJsonPathValueConverter implements JsonPathValueConverter<Boolean, String> {
	@Override
	public Boolean convert(String value) {
		return "true".equals(value);
	}
}
