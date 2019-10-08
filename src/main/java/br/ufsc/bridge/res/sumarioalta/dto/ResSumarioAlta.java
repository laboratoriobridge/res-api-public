package br.ufsc.bridge.res.sumarioalta.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.apache.commons.lang3.StringUtils;

import br.ufsc.bridge.res.util.ResDocument;
import br.ufsc.bridge.res.util.json.DateJsonPathValueConverter;
import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.res.util.json.ResJsonUtils;

@Getter
@Setter
@NoArgsConstructor
public class ResSumarioAlta extends ResDocument implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonPathProperty(value = "$.content[?(@.name.value == 'Caracterização do atendimento')]"
			+ ".items[?(@.name.value == 'Admissão do paciente')]"
			+ ".data.items[?(@.name.value == 'Data e hora da internação')]"
			+ ".value.value",
			converter = DateJsonPathValueConverter.class)
	private Date dataAtendimento;

	@JsonPathProperty(value = "$.content[?(@.name.value == 'Caracterização do atendimento')]"
			+ ".items[?(@.name.value == 'Admissão do paciente')]"
			+ ".data.items[?(@.name.value == 'Localização atribuída ao paciente')]"
			+ ".items[?(@.name.value == 'Instituição')]"
			+ ".items[?(@.name.value == 'Estabelecimento de saúde')]"
			+ ".value.value")
	private String estabelecimento;

	@JsonPathProperty(value = "$.content[?(@.name.value == 'Caracterização do atendimento')]"
			+ ".items[?(@.name.value == 'Admissão do paciente')]"
			+ ".data.items[?(@.name.value == 'Localização atribuída ao paciente')]"
			+ ".items[?(@.name.value == 'Instituição')]"
			+ ".items[?(@.name.value == 'Identificação da equipe de saúde')]"
			+ ".value.value")
	private String equipe;

	@JsonPathProperty(value = "$.content[?(@.name.value == 'Motivo da admissão, diagnósticos relevantes e patologias associadas desenvolvidas na internação')]"
			+ ".items[?(@.name.value == 'Problema Diagnóstico')]"
			+ ".data.items")
	private List<ResSumarioAltaProblemaDiagnostico> problemasDiagnostico = new ArrayList<>();

	@JsonPathProperty(value = "$.content[?(@.name.value == 'Procedimento(s) realizado(s) ou solicitado(s)')]")
	private List<ResSumarioAltaProcedimento> procedimentos = new ArrayList<>();

	@JsonPathProperty(value = ".content[?(@.name.value == 'Resumo da evolução clínica do indivíduo durante a internação')]"
			+ ".items")
	private List<ResSumarioAltaResumoEvolucaoClinica> resumos = new ArrayList<>();

	@JsonPathProperty(value = ".content[?(@.name.value == 'Alergias e/ou reações adversas na internação')]"
			+ ".items")
	private List<ResSumarioAltaAlergia> alergias = new ArrayList<>();
	// medicamento estruturado nao ira aparecer por enquanto, deixarei comentado so para manter historico
	// private List<ResSumarioDeAltaMedicamento> medicamentos = new ArrayList<>();

	@JsonPathProperty(value = ".content[?(@.name.value == 'Prescrição da alta')]")
	private List<ResSumarioAltaMedicamentoNaoEstruturado> medicamentosNaoEstruturados = new ArrayList<>();

	@JsonPathProperty(value = ".content[?(@.name.value == 'Plano de cuidados, instruções e recomendações (na alta)')]"
			+ ".items.activities.description"
			+ ".items.value.value")
	private String planoCuidado;

	@JsonPathProperty(value = ".content[?(@.name.value == 'Informações da alta')]"
			+ ".items.data.items[?(@.name.value == 'Motivo do desfecho')]"
			+ ".value.value")
	private String motivoAlta;

	@JsonPathProperty(value = ".content[?(@.name.value == 'Informações da alta')]"
			+ ".items.data.items[?(@.name.value == 'Profissional responsável pela alta')]"
			+ ".items")
	private ResSumarioAltaIdentificacaoProfissional profissional;

	@JsonPathProperty(value = ".content[?(@.name.value == 'Informações da alta')]"
			+ ".items.data.items[?(@.name.value == 'Data e hora da saída da internação')]"
			+ ".value.value",
			converter = DateJsonPathValueConverter.class)
	private Date dataAlta;

	public static ResSumarioAlta readJsonBase64(String jsonBase64) {
		ResSumarioAlta sumarioAlta = ResJsonUtils.readJson(jsonBase64, ResSumarioAlta.class);
		List<ResSumarioAltaMedicamentoNaoEstruturado> medicamentosNaoEstruturados = sumarioAlta.getMedicamentosNaoEstruturados();

		for (ResSumarioAltaMedicamentoNaoEstruturado medicamento : medicamentosNaoEstruturados) {
			List<String> descricoes = new ArrayList<>();
			for (String medicamentoDescricao : medicamento.getDescricoes()) {
				if (StringUtils.isNotBlank(medicamentoDescricao)) {
					descricoes.addAll(Arrays.asList(medicamentoDescricao.split(";")));
				}
			}
			medicamento.setDescricoes(descricoes);
		}
		return sumarioAlta;
	}
}
