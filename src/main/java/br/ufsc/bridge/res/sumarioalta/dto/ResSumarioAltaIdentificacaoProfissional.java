package br.ufsc.bridge.res.sumarioalta.dto;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
public class ResSumarioAltaIdentificacaoProfissional implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonPathProperty(value = "[?(@.name.value == 'CNS do profissional')]"
			+ ".value.value")
	private String cns;

	@JsonPathProperty(value = "[?(@.name.value == 'Ocupação do profissional responsável pela alta')]"
			+ ".value.value")
	private String cbo;

	public ResSumarioAltaIdentificacaoProfissional(XPathFactoryAssist xPathInformacoesAlta) throws XPathExpressionException {
		String startXPath = "./Profissional_responsável_pela_alta";
		this.cns = xPathInformacoesAlta.getString(startXPath + "/CNS_do_profissional/value/value");
		this.cbo = xPathInformacoesAlta.getString(startXPath + "/Ocupação_do_profissional_responsável_pela_alta/value/value");
	}

}
