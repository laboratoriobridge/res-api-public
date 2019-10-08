package br.ufsc.bridge.res.dab.dto;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResABProcedimento implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonPathProperty("$.description.items[?(@.name.value == 'Procedimento SUS')].value.value")
	private String nome;

	@JsonPathProperty("$.description.items[?(@.name.value == 'Procedimento SUS')].value.defining_code.code_string")
	private String codigo;
	//	private List<String> resultadoObservacoes = new ArrayList<>();

	public ResABProcedimento(XPathFactoryAssist xPathProcedimento) throws XPathExpressionException {
		this.codigo = xPathProcedimento.getString("./description/Procedimento_SUS/value/defining_code/code_string");
		this.nome = xPathProcedimento.getString("./description/Procedimento_SUS/value/value");

		//		for (XPathFactoryAssist xPathResultado : xPathProcedimento.iterable(".//description/Resultado_e_fslash_ou_observações_do_procedimento_ou_pequena_cirurgia")) {
		//			this.resultadoObservacoes.add(xPathResultado.getString("./value/value"));
		//		}

	}
}
