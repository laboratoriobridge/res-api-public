package br.ufsc.bridge.res.dab.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import br.ufsc.bridge.res.util.json.JsonPathValueConverter;

@Getter
@AllArgsConstructor
public enum ResABCriticidadeEnum {
	BAIXO("Baixo", "at0102"),
	ALTO("Alto", "at0103"),
	INDETERMINADO("Indeterminado", "at0124");

	private String descricao;
	private String codigo;

	public static ResABCriticidadeEnum getByCodigo(String codigo) {
		for (ResABCriticidadeEnum value : values()) {
			if (value.getCodigo().equals(codigo)) {
				return value;
			}
		}
		return null;
	}

	public static class ResCriticidadeEnumJsonPathConveter implements JsonPathValueConverter<ResABCriticidadeEnum, String> {

		@Override
		public ResABCriticidadeEnum convert(String value) {
			return ResABCriticidadeEnum.getByCodigo(value);
		}
	}
}
