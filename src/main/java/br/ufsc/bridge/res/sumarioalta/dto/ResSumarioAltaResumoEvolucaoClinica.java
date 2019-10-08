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
public class ResSumarioAltaResumoEvolucaoClinica implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonPathProperty(value = ".data.items.items.value.value")
	private String texto;

	public ResSumarioAltaResumoEvolucaoClinica(XPathFactoryAssist xPathResumoEvolucaoClinica) throws XPathExpressionException {
		this.texto = xPathResumoEvolucaoClinica.getString("./data/Texto_livre/Descrição_da_evolução_clínica_do_indivíduo_durante_a_internação/value/value");
	}

}
