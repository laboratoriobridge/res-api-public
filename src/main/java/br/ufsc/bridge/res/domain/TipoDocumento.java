package br.ufsc.bridge.res.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumento {

	REGISTRO_ATENDIMENTO_CLINICO("cn4_registro_atendimento_clinico_v1.0"),
	REGISTRO_IMUNOBIOLOGICO("cn6_registro_imunobiologico_v1.0"),
	SUMARIO_ALTA("cn2_sumario_alta_internacao_v1.0"),
	DISPENSACAO_MEDICAMENTOS("cn10_dispensacao_medicamentos_v1.0");

	private String codigo;

	public static TipoDocumento getByCodigo(String codigo) {
		for (TipoDocumento tipoDocumento : TipoDocumento.values()) {
			if (tipoDocumento.getCodigo().equals(codigo)) {
				return tipoDocumento;
			}
		}
		return null;
	}

}
