package br.ufsc.bridge.res.util.json;

import br.ufsc.bridge.res.util.json.JsonPathValueConverter;

public class ResSigtapJsonConverter implements JsonPathValueConverter<String, String> {

	private static final String SIGTAP = "SIGTAP";

	@Override
	public String convert(String value) {
		return value.contains(SIGTAP) ? SIGTAP : value;
	}
}
