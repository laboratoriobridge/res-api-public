package br.ufsc.bridge.res.dab.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import br.ufsc.bridge.res.dab.domain.ResABCriticidadeEnum;
import br.ufsc.bridge.res.dab.domain.ResABCriticidadeEnum.ResCriticidadeEnumJsonPathConveter;
import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResABAlergiaReacoes implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonPathProperty("$.data.items[?(@.name.value == 'Agente/substância específica')].value.value")
	private String agente;

	@JsonPathProperty(value = "$.data.items[?(@.name.value == 'Criticidade')].value..code_string",
			converter = ResCriticidadeEnumJsonPathConveter.class)
	private ResABCriticidadeEnum criticidade;

	@JsonPathProperty("$.data.items[?(@.name.value == 'Categoria do agente causador da alergia ou reação adversa')].value.value")
	private String categoria;

	@JsonPathProperty("$.data.items[?(@.name.value == 'Evento da reação')]")
	private List<ResABEventoReacao> eventoReacao = new ArrayList<>();

	public ResABAlergiaReacoes(XPathFactoryAssist xPathAlergia) throws XPathExpressionException {
		this.agente = xPathAlergia.getString("./data/Agente_fslash_substância_específica/value/value");
		this.categoria = xPathAlergia.getString("./data/Categoria_do_agente_causador_da_alergia_ou_reação_adversa/value/value");
		this.criticidade = ResABCriticidadeEnum.getByCodigo(xPathAlergia.getString("./data/Criticidade/value/defining_code/code_string"));

		for (XPathFactoryAssist xPathEvento : xPathAlergia.iterable(".//Evento_da_reação")) {
			this.eventoReacao.add(new ResABEventoReacao(xPathEvento));
		}
	}
}
