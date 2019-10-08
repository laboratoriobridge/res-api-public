package br.ufsc.bridge.res.sumarioalta.dto;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.res.util.json.ResSigtapJsonConverter;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
public class ResSumarioAltaProcedimento implements Serializable {

	private static final String SIGTAP = "SIGTAP";
	private static final long serialVersionUID = 1L;

	@JsonPathProperty(value = ".items[?(@.name.value == 'Procedimento')]"
			+ ".description.items"
			+ "[?(@.name.value == 'Procedimento SUS')]"
			+ ".value.value")
	private String nome;

	@JsonPathProperty(value = ".items[?(@.name.value == 'Procedimento')]"
			+ ".description.items"
			+ "[?(@.name.value == 'Procedimento SUS')]"
			+ ".value.defining_code.code_string")
	private String codigo;

	@JsonPathProperty(value = ".items[?(@.name.value == 'Procedimento')]"
			+ ".description.items"
			+ "[?(@.name.value == 'Procedimento SUS')]"
			+ ".value.defining_code.terminology_id.value"
			, converter =  ResSigtapJsonConverter.class)
	private String classificacao;

	@JsonPathProperty(value = ".items[?(@.name.value == 'Procedimento')]"
			+ ".description.items"
			+ "[?(@.name.value == 'Status do procedimento')]"
			+ ".value.value")
	private String status;

	public ResSumarioAltaProcedimento(XPathFactoryAssist xPathProcedimento) throws XPathExpressionException {
		String startXPath = "./description/Procedimento_SUS/value";

		this.nome = xPathProcedimento.getString(startXPath + "/value");
		this.codigo = xPathProcedimento.getString(startXPath + "/defining_code/code_string");

		this.classificacao = xPathProcedimento.getString(startXPath + "/defining_code/terminology_id/value");
		if (this.classificacao != null && this.classificacao.contains(SIGTAP)) {
			this.classificacao = SIGTAP;
		}

		this.status = xPathProcedimento.getString("./description/Status_do_procedimento/value/value");
	}
}
