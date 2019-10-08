package br.ufsc.bridge.res.dab.dto;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import br.ufsc.bridge.res.util.json.BooleanJsonPathValueConverter;
import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResABIdentificacaoProfissional implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonPathProperty("$.items[?(@.name.value == 'CNS do profissional')].value.value")
	private String cns;
	@JsonPathProperty("$.items[?(@.name.value == 'Ocupação do profissional')].value.defining_code.code_string")
	private String cbo;
	@JsonPathProperty("$.items[?(@.name.value == 'Nome do profissional')].value.value")
	private String nome;
	@JsonPathProperty(value = "$.items[?(@.name.value == 'É o responsável pelo atendimento?')].value.value", converter = BooleanJsonPathValueConverter.class)
	private boolean responsavel;

	public ResABIdentificacaoProfissional(XPathFactoryAssist xPathprofissional) throws XPathExpressionException {
		this.cns = xPathprofissional.getString("./CNS_do_profissional/value/value");
		this.cbo = xPathprofissional.getString("./Ocupação_do_profissional/value//code_string");
		this.nome = xPathprofissional.getString("./Nome_do_profissional/value/value");
		this.responsavel = xPathprofissional.getBoolean("./É_o_responsável_pelo_atendimento_quest_/value/value");
	}
}
