package br.ufsc.bridge.res.dab.dto;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.apache.commons.lang3.StringUtils;

import br.ufsc.bridge.res.dab.domain.ResABAleitamentoMaternoEnum;
import br.ufsc.bridge.res.dab.domain.ResABAleitamentoMaternoEnum.ResABAleitamentoMaternoEnumJsonPathConverter;
import br.ufsc.bridge.res.dab.domain.ResABCondutaEnum;
import br.ufsc.bridge.res.dab.domain.ResABCondutaEnum.ResABCondutaEnumJsonPathConverter;
import br.ufsc.bridge.res.dab.domain.ResABTipoAtendimentoEnum;
import br.ufsc.bridge.res.dab.domain.ResABTipoAtendimentoEnum.ResABTipoAtendimentoEnumJsonPathValueConverter;
import br.ufsc.bridge.res.dab.writer.json.AlergiaReacoesAdversasJsonBuilder;
import br.ufsc.bridge.res.dab.writer.json.AlergiaReacoesAdversasJsonBuilder.AlergiaJsonBuilder;
import br.ufsc.bridge.res.dab.writer.json.CaracterizacaoConsultaABJsonBuilder;
import br.ufsc.bridge.res.dab.writer.json.DadosDesfechoJsonBuilder;
import br.ufsc.bridge.res.dab.writer.json.ProblemaDiagnosticoJsonBuilder;
import br.ufsc.bridge.res.dab.writer.json.ProcedimentoRealizadoJsonBuilder;
import br.ufsc.bridge.res.dab.writer.json.ResumoConsultaABJsonBuilder;
import br.ufsc.bridge.res.util.ResDocument;
import br.ufsc.bridge.res.util.json.DateJsonPathValueConverter;
import br.ufsc.bridge.res.util.json.JsonPathProperty;
import br.ufsc.bridge.res.util.json.JsonPathProperty.Group;
import br.ufsc.bridge.res.util.json.ResJsonUtils;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class ResABResumoConsulta extends ResDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonPathProperty(group = Group.ADMISSAO_DO_PACIENTE,
			value = ".data.items[?(@.name.value == 'Data/hora da admissão')].value.value",
			converter = DateJsonPathValueConverter.class)
	private Date dataAtendimento;

	@JsonPathProperty(group = Group.ADMISSAO_DO_PACIENTE,
			value = ".data.items[?(@.name.value == 'Tipo de atendimento')].value.defining_code.code_string",
			converter = ResABTipoAtendimentoEnumJsonPathValueConverter.class)
	private ResABTipoAtendimentoEnum tipoAtendimento;

	@JsonPathProperty(group = Group.INSTITUICAO,
			value = ".items[?(@.name.value == 'Estabelecimento de saúde')].value.value")
	private String cnes;

	@JsonPathProperty(group = Group.INSTITUICAO,
			value = ".items[?(@.name.value == 'Identificação da equipe de saúde')].value.value")
	private String ine;

	// nao presente mais, comentado para manter historico
	// private ResABTurnoEnum turno;

	@JsonPathProperty(group = Group.ADMISSAO_DO_PACIENTE,
			value = ".data.items[?(@.name.value == 'Profissionais do atendimento')]")
	private List<ResABIdentificacaoProfissional> profissionais = new ArrayList<>();

	@JsonPathProperty(group = Group.OBSERVACOES_MEDICOES,
			value = ".items[?(@.name.value == 'Peso corporal')].data..magnitude")
	private String peso;

	@JsonPathProperty(group = Group.OBSERVACOES_MEDICOES,
			value = ".items[?(@.name.value == 'Altura / comprimento')].data..magnitude")
	private String altura;

	@JsonPathProperty(group = Group.OBSERVACOES_MEDICOES,
			value = ".items[?(@.name.value == 'Perímetro cefálico')].data..magnitude")
	private String perimetroCefalico;

	@JsonPathProperty(group = Group.OBSERVACOES_INFORMACOES_ADICIONAIS,
			value = ".items[?(@.name.value == 'Alimentação da criança menor de 2 anos')].data.events.data.items.value.value",
			converter = ResABAleitamentoMaternoEnumJsonPathConverter.class)
	private ResABAleitamentoMaternoEnum aleitamentoMaterno;

	@JsonPathProperty(group = Group.OBSERVACOES_INFORMACOES_ADICIONAIS,
			value = ".items[?(@.name.value == 'Ciclo menstrual')].data.events.data.items.value.value",
			converter = DateJsonPathValueConverter.class)
	private Date dum;

	@JsonPathProperty(group = Group.OBSERVACOES_INFORMACOES_ADICIONAIS,
			value = ".items[?(@.name.value == 'Gestação')].data.events.data.items.value.value")
	private String idadeGestacional;

	@JsonPathProperty(group = Group.OBSERVACOES_INFORMACOES_ADICIONAIS,
			value = "..items[?(@.name.value == 'Quantidade de gestas prévias')].value.magnitude")
	private String gestasPrevias;

	@JsonPathProperty(group = Group.OBSERVACOES_INFORMACOES_ADICIONAIS,
			value = "..items[?(@.name.value == 'Quantidade de partos')].value.magnitude")
	private String partos;

	@JsonPathProperty("$.content[?(@.name.value == 'Problemas/Diagnósticos avaliados')].items[?(@.name.value == 'Problema Diagnóstico')]")
	private List<ResABProblemaDiagnostico> problemasDiagnosticos = new ArrayList<>();

	@JsonPathProperty("$.content[?(@.name.value == 'Alergias e/ou reações adversas no atendimento')].items[?(@.name.value == 'Risco de reação adversa')]")
	private List<ResABAlergiaReacoes> alergias = new LinkedList<>();

	@JsonPathProperty("$.content[?(@.name.value == 'Procedimento(s) realizado(s) ou solicitado(s)')].items[?(@.name.value == 'Procedimento')]")
	private List<ResABProcedimento> procedimentos = new LinkedList<>();

	// não tem json
	private List<ResABMedicamento> medicamentos = new LinkedList<>();

	@JsonPathProperty("$.content[?(@.name.value == 'Prescrição no atendimento')]..items[?(@.name.value == 'Medicamentos prescritos no atendimento (não estruturado)')].items.value.value")
	private List<String> medicamentosNaoEstruturados = new LinkedList<>();

	@JsonPathProperty(value = "$.content[?(@.name.value == 'Dados do desfecho')]..items[?(@.name.value == 'Motivo do desfecho')].value..code_string",
			converter = ResABCondutaEnumJsonPathConverter.class)
	private List<ResABCondutaEnum> condutas = new LinkedList<>();

	// não tem json
	private List<String> encaminhamentos = new LinkedList<>();

	public static ResABResumoConsulta readJsonBase64(String jsonBase64) {
		ResABResumoConsulta resumoConsulta = ResJsonUtils.readJson(jsonBase64, ResABResumoConsulta.class);
		List<String> descricoes = new ArrayList<>();
		for (String medicamentoDescricao : resumoConsulta.getMedicamentosNaoEstruturados()) {
			if (StringUtils.isNotBlank(medicamentoDescricao)) {
				descricoes.addAll(Arrays.asList(medicamentoDescricao.split(";")));
			}
		}
		resumoConsulta.setMedicamentosNaoEstruturados(descricoes);
		return resumoConsulta;
	}

	public String getJson() throws IOException {
		ResumoConsultaABJsonBuilder abBuilder = new ResumoConsultaABJsonBuilder().data(this.dataAtendimento);

		//@formatter:off
		CaracterizacaoConsultaABJsonBuilder<ResumoConsultaABJsonBuilder> caracterizacaoConsulta = abBuilder.caracterizacaoConsulta();
		caracterizacaoConsulta
			.tipoAtendimento(this.tipoAtendimento)
			.localizacaoAtribuidaPaciente(this.cnes, this.ine)
			.dataHoraAdmissao(this.dataAtendimento);

		for (ResABIdentificacaoProfissional profissional : this.profissionais) {
			caracterizacaoConsulta.profissional()
				.cns(profissional.getCns())
				.nome(profissional.getNome())
				.cboDescricao("")
				.cbo(profissional.getCbo())
				.responsavel(profissional.isResponsavel());
		}

		abBuilder.medicoesObservacoes()
			.pesoCorporal(this.dataAtendimento, this.peso)
			.altura(this.dataAtendimento, this.altura)
			.perimetroCefalico(this.dataAtendimento, this.perimetroCefalico)
			.cicloMenstrual(this.dataAtendimento, this.dum)
			.gestacao(this.dataAtendimento, this.idadeGestacional)
			.sumarioObstetrico(this.gestasPrevias, this.partos)
			.aleitamentoMaterno(this.dataAtendimento, this.aleitamentoMaterno);

		ProblemaDiagnosticoJsonBuilder<ResumoConsultaABJsonBuilder> diagnosticoAvaliadoBuilder = abBuilder.problemaDiagnostico();
		for (ResABProblemaDiagnostico diagnostico : this.problemasDiagnosticos) {
			diagnosticoAvaliadoBuilder.problema()
				.descricao(diagnostico.getDescricao())
				.tipo(diagnostico.getTipoProblemaDiagnostico())
				.codigo(diagnostico.getCodigo());
		}

		AlergiaReacoesAdversasJsonBuilder<ResumoConsultaABJsonBuilder> alergiasBuilder = abBuilder.alergiaReacao();
		for (ResABAlergiaReacoes alergia : this.alergias) {
			AlergiaJsonBuilder alergiaBuilder = alergiasBuilder.alergia();
			alergiaBuilder
				.agente(alergia.getAgente())
				.categoria(alergia.getCategoria())
				.criticidade(alergia.getCriticidade());

			for (ResABEventoReacao evento : alergia.getEventoReacao()) {
				alergiaBuilder.evento()
					.dataInstalacao(evento.getDataInstalacao())
					.evolucao(evento.getEvolucaoAlergia())
					.manifestacao(evento.getManifestacao());
			}
		}

		ProcedimentoRealizadoJsonBuilder<ResumoConsultaABJsonBuilder> procedimentosBuilder = abBuilder.procedimentoRealizado();
		for (ResABProcedimento procedimento : this.procedimentos) {
			procedimentosBuilder.procedimento()
				.descricao(procedimento.getNome())
				.data(this.dataAtendimento)
				.codigo(procedimento.getCodigo());
		}

		abBuilder.prescricaoAtendimento()
				.medicamentoNaoEstruturado().medicamentos(this.medicamentosNaoEstruturados);

		DadosDesfechoJsonBuilder<ResumoConsultaABJsonBuilder> desfechoBuilder = abBuilder.dadosDesfecho();
		for (ResABCondutaEnum conduta : this.condutas) {
			desfechoBuilder.desfecho(conduta);
		}

		return abBuilder.getJsonString();
	}
}
