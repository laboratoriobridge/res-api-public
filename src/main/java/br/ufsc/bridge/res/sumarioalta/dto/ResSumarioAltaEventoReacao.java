package br.ufsc.bridge.res.sumarioalta.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.xpath.XPathExpressionException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.ufsc.bridge.res.util.RDateUtil;
import br.ufsc.bridge.res.util.json.DateJsonPathValueConverter;
import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
public class ResSumarioAltaEventoReacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonPathProperty(value = "@.items[?(@.name.value == 'Manifestação')]"
			+ ".value.value")
	private String manifestacao;

	@JsonPathProperty(value = "@.items[?(@.name.value == 'Data da instalação da reação adversa')]"
			+ ".value.value",
			converter = DateJsonPathValueConverter.class)
	private Date dataInstalacao;

	@JsonPathProperty(value = "@.items[?(@.name.value == 'Evolução da alergia/reação adversa')]"
			+ ".value.value")
	private String evolucaoAlergia;

	public ResSumarioAltaEventoReacao(XPathFactoryAssist xPathEventoReacao) throws XPathExpressionException {
		this.manifestacao = xPathEventoReacao.getString("./Manifestação/value/value");
		this.dataInstalacao = RDateUtil.isoEHRToDate(xPathEventoReacao.getString("./Data_da_instalação_da_reação_adversa/value/value"));
		this.evolucaoAlergia = xPathEventoReacao.getString("./Evolução_da_alergia_fslash_reação_adversa/value/value");
	}

}
