package br.ufsc.bridge.res.dab.writer.json;

import java.io.IOException;
import java.util.Date;

import br.ufsc.bridge.res.util.RDateUtil;
import br.ufsc.bridge.res.util.json.BaseJsonBuilder;

public class ResumoConsultaABJsonBuilder extends BaseJsonBuilder<ResumoConsultaABJsonBuilder> {

	public ResumoConsultaABJsonBuilder() throws IOException {
		super("resumo-consulta");
	}

	@Override
	protected String childJsonPath() {
		return "$.content";
	}

	public ResumoConsultaABJsonBuilder data(Date date) {
		this.document.set("$.context.start_time.value", RDateUtil.dateToISOEHR(date));
		return this;
	}

	public CaracterizacaoConsultaABJsonBuilder<ResumoConsultaABJsonBuilder> caracterizacaoConsulta() {
		return new CaracterizacaoConsultaABJsonBuilder<>(this);
	}

	public MedicoesObservacoesJsonBuilder<ResumoConsultaABJsonBuilder> medicoesObservacoes() {
		return new MedicoesObservacoesJsonBuilder<>(this);
	}

	public ProblemaDiagnosticoJsonBuilder<ResumoConsultaABJsonBuilder> problemaDiagnostico() {
		return new ProblemaDiagnosticoJsonBuilder<>(this);
	}

	public AlergiaReacoesAdversasJsonBuilder<ResumoConsultaABJsonBuilder> alergiaReacao() {
		return new AlergiaReacoesAdversasJsonBuilder<>(this);
	}

	public ProcedimentoRealizadoJsonBuilder<ResumoConsultaABJsonBuilder> procedimentoRealizado() {
		return new ProcedimentoRealizadoJsonBuilder<>(this);
	}

	public PrescricaoAtendimentoJsonBuilder<ResumoConsultaABJsonBuilder> prescricaoAtendimento() {
		return new PrescricaoAtendimentoJsonBuilder<>(this);
	}

	public DadosDesfechoJsonBuilder<ResumoConsultaABJsonBuilder> dadosDesfecho() {
		return new DadosDesfechoJsonBuilder<>(this);
	}
}
