package br.ufsc.bridge.res.dab.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import br.ufsc.bridge.res.util.json.JsonPathValueConverter;

@Getter
@AllArgsConstructor
public enum ResABCondutaEnum {
	RETORNO_PARA_CONSULTA_AGENDADA("Retorno para consulta agendada", ""),
	RETORNO_PARA_CUIDADO_CONTINUADO_PROGRAMADO("Retorno para cuidado continuado/programado", ""),
	AGENDAMENTO_PARA_GRUPOS("Agendamento para grupos", ""),
	AGENDAMENTO_PARA_NASF("Agendamento para NASF", ""),
	ALTA_DO_EPISODIO("Alta do episódio", ""),

	ENCAMINHAMENTO("Encaminhamento", "at0053"),
	EVASAO("Evasão", "at0054"),
	OBITO("Óbito", "at0056"),
	RETORNO("Retorno", "at0058"),
	TRANSFERÊNCIA("Transferência", "at0059"),
	;

	private String descricao;
	private String codigo;

	public static ResABCondutaEnum getByCodigo(String codigo) {
		for (ResABCondutaEnum value : values()) {
			if (value.getCodigo().equals(codigo)) {
				return value;
			}
		}
		return null;
	}

	public static class ResABCondutaEnumJsonPathConverter implements JsonPathValueConverter<ResABCondutaEnum, String> {

		@Override
		public ResABCondutaEnum convert(String value) {
			return ResABCondutaEnum.getByCodigo(value);
		}
	}
}
