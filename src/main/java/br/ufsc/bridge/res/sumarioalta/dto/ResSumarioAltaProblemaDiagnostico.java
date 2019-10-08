package br.ufsc.bridge.res.sumarioalta.dto;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.ufsc.bridge.res.dab.domain.ResABTipoProblemaDiagnostico;
import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
public class ResSumarioAltaProblemaDiagnostico implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonPathProperty("[?(@.name.value == 'Diagnóstico')]"
			+ ".value.value")
	private String descricao;

	@JsonPathProperty("[?(@.name.value == 'Diagnóstico')]"
			+ ".value.defining_code.code_string")
	private String codigo;

	@JsonPathProperty(value = "[?(@.name.value == 'Diagnóstico')]"
			+ ".value.defining_code.terminology_id.value",
			converter = ResABTipoProblemaDiagnostico.ResTipoProblemaDiagnosticoJsonPathConveter.class)
	private ResABTipoProblemaDiagnostico tipo;

	public ResSumarioAltaProblemaDiagnostico(XPathFactoryAssist xPathProblemaDiagnostico) throws XPathExpressionException {
		this.descricao = xPathProblemaDiagnostico.getString("./data/Diagnóstico/value/value");
		this.codigo = xPathProblemaDiagnostico.getString("./data/Diagnóstico/value/defining_code/code_string");
		this.tipo = ResABTipoProblemaDiagnostico.getByTipo(xPathProblemaDiagnostico.getString("./data/Diagnóstico/value/defining_code/terminology_id/value"));
	}
}
