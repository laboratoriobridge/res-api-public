package br.ufsc.bridge.res.util.json;

import java.util.Date;

import br.ufsc.bridge.res.util.RDateUtil;

public class DateJsonPathValueConverter implements JsonPathValueConverter<Date, String> {
	@Override
	public Date convert(String value) {
		return RDateUtil.isoEHRToDate(value);
	}
}
