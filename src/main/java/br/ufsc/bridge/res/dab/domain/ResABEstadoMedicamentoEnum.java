package br.ufsc.bridge.res.dab.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResABEstadoMedicamentoEnum {

	ATIVA("at0050"),
	DESCONTINUADA("at0051"),
	NUNCA_ATIVA("at0052"),
	TRATAMENTO_COMPLETO("at0053"),
	SUBSTITUIDO("at0054");

	private String codigo;

	public static ResABEstadoMedicamentoEnum getByCodigo(String codigo) {
		for (ResABEstadoMedicamentoEnum value : values()) {
			if (value.getCodigo().equals(codigo)) {
				return value;
			}
		}
		return null;
	}
}
