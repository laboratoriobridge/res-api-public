package br.ufsc.bridge.res.dab.dto;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import br.ufsc.bridge.res.dab.domain.ResABTipoProblemaDiagnostico;
import br.ufsc.bridge.res.dab.domain.ResABTipoProblemaDiagnostico.ResTipoProblemaDiagnosticoJsonPathConveter;
import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResABProblemaDiagnostico implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonPathProperty("$.data.items[?(@.name.value == 'Diagnóstico')].value.value")
	private String descricao;

	@JsonPathProperty(value = "$.data.items[?(@.name.value == 'Diagnóstico')].value.defining_code..value",
			converter = ResTipoProblemaDiagnosticoJsonPathConveter.class)
	private ResABTipoProblemaDiagnostico tipo;

	@JsonPathProperty("$.data.items[?(@.name.value == 'Diagnóstico')].value.defining_code.code_string")
	private String codigo;

	public ResABProblemaDiagnostico(XPathFactoryAssist xPathDiagnostico) throws XPathExpressionException {
		this.codigo = xPathDiagnostico.getString("./data/Diagnóstico/value/defining_code/code_string");
		this.descricao = xPathDiagnostico.getString("./data/Diagnóstico/value/value");
		this.tipo = ResABTipoProblemaDiagnostico.getByTipo(xPathDiagnostico.getString("./data/Diagnóstico/value/defining_code/terminology_id/value"));
	}

	public String getTipoProblemaDiagnostico() {
		if (this.tipo != null) {
			return this.tipo.getTipo();
		}
		return null;
	}
}
