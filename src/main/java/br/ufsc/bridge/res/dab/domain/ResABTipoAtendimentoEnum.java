package br.ufsc.bridge.res.dab.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import br.ufsc.bridge.res.util.json.JsonPathValueConverter;

@Getter
@AllArgsConstructor
public enum ResABTipoAtendimentoEnum {

	CONSULTA_AGENDADA_PROGRAMADA_CUIDADO_CONTINUADO("Consulta agendada programada/ cuidado continuado", "at0.143"),
	CONSULTA_AGENDADA("Consulta Agendada", "at0.144"),
	DEMANDA_ESPONTANEA_CONSULTA_NO_DIA("Demanda Espontanea Consulta no Dia", "at0.146"),
	DEMANDA_ESPONTANEA_ATENDIMENTO_DE_URGENCIA("Demanda Espontanea Atendimento de Urgencia", "at0.147");

	private String descricao;
	private String codigo;

	public static ResABTipoAtendimentoEnum getById(String codigo) {
		for (ResABTipoAtendimentoEnum value : ResABTipoAtendimentoEnum.values()) {
			if (value.getCodigo().equals(codigo)) {
				return value;
			}
		}
		return null;
	}

	public static class ResABTipoAtendimentoEnumJsonPathValueConverter implements JsonPathValueConverter<ResABTipoAtendimentoEnum, String> {

		@Override
		public ResABTipoAtendimentoEnum convert(String codigo) {
			return ResABTipoAtendimentoEnum.getById(codigo);
		}
	}
}
