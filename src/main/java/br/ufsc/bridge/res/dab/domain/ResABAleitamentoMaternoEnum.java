package br.ufsc.bridge.res.dab.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import br.ufsc.bridge.res.util.json.JsonPathValueConverter;

@Getter
@AllArgsConstructor
public enum ResABAleitamentoMaternoEnum {
	EXCLUSIVO("Exclusivo"),
	PREDOMINANTE("Predominante"),
	COMPLEMENTADO("Complementado"),
	INEXISTENTE("Inexistente");

	private String descricao;

	public static ResABAleitamentoMaternoEnum getByDescricao(String jsonValue) {
		for (ResABAleitamentoMaternoEnum value : ResABAleitamentoMaternoEnum.values()) {
			if (value.getDescricao().equalsIgnoreCase(jsonValue)) {
				return value;
			}
		}
		return null;
	}

	public static class ResABAleitamentoMaternoEnumJsonPathConverter implements JsonPathValueConverter<ResABAleitamentoMaternoEnum, String> {

		@Override
		public ResABAleitamentoMaternoEnum convert(String jsonValue) {
			return ResABAleitamentoMaternoEnum.getByDescricao(jsonValue);
		}
	}
}
