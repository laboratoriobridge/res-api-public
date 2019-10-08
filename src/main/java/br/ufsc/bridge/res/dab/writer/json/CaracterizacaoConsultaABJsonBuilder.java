package br.ufsc.bridge.res.dab.writer.json;

import java.util.Date;

import br.ufsc.bridge.res.dab.domain.ResABTipoAtendimentoEnum;
import br.ufsc.bridge.res.util.RDateUtil;
import br.ufsc.bridge.res.util.json.BaseJsonBuilder;

public class CaracterizacaoConsultaABJsonBuilder<T extends BaseJsonBuilder<?>> extends BaseJsonBuilder<T> {

	public CaracterizacaoConsultaABJsonBuilder(T parent) {
		super(parent, "caracterizacao-atendimento");
	}

	public CaracterizacaoConsultaABJsonBuilder<T> tipoAtendimento(ResABTipoAtendimentoEnum value) {
		this.document.set("$.items.data.items[?(@.name.value == 'Tipo de atendimento')].value.value", value.getDescricao());
		this.document.set("$.items.data.items[?(@.name.value == 'Tipo de atendimento')].value..code_string", value.getCodigo());
		return this;
	}

	public CaracterizacaoConsultaABJsonBuilder<T> localizacaoAtribuidaPaciente(String cnes, String ine) {
		this.document.set("$.items.data.items[*].items[*].items[?(@.name.value == 'Estabelecimento de saúde')].value.value", cnes);

		if (ine != null) {
			this.document.set("$.items.data.items[*].items[*].items[?(@.name.value == 'Identificação da equipe de saúde')].value.value", ine);
		} else {
			this.document.delete("$.items.data.items[*].items[*].items[?(@.name.value == 'Identificação da equipe de saúde')].value.value");
		}

		return this;
	}

	public CaracterizacaoConsultaABJsonBuilder<T> dataHoraAdmissao(Date data) {
		this.document.set("$.items.data.items[?(@.name.value == 'Data/hora da admissão')].value.value", RDateUtil.dateToISOEHR(data));
		return this;
	}

	public ProfissionalAtendimentoJsonBuilder profissional() {
		return new ProfissionalAtendimentoJsonBuilder(this);
	}

	@Override
	protected String childJsonPath() {
		return "$.items.data.items";
	}

	public class ProfissionalAtendimentoJsonBuilder extends BaseJsonBuilder<CaracterizacaoConsultaABJsonBuilder<T>> {

		public ProfissionalAtendimentoJsonBuilder(CaracterizacaoConsultaABJsonBuilder<T> parent) {
			super(parent, "profissional");
		}

		public ProfissionalAtendimentoJsonBuilder nome(String nome) {
			this.document.set("$.items[?(@.name.value == 'Nome do profissional')].value.value", nome);
			return this;
		}

		public ProfissionalAtendimentoJsonBuilder cns(String cns) {
			this.document.set("$.items[?(@.name.value == 'CNS do profissional')].value.value", cns);
			return this;
		}

		public ProfissionalAtendimentoJsonBuilder cbo(String cbo) {
			this.document.set("$.items[?(@.name.value == 'Ocupação do profissional')].value..code_string", cbo);
			return this;
		}

		public ProfissionalAtendimentoJsonBuilder cboDescricao(String cboDescricao) {
			this.document.set("$.items[?(@.name.value == 'Ocupação do profissional')].value.value", cboDescricao);
			return this;
		}

		public ProfissionalAtendimentoJsonBuilder responsavel(Boolean responsavel) {
			this.document.set("$.items[?(@.name.value == 'É o responsável pelo atendimento?')].value.value", responsavel.toString());
			return this;
		}
	}

}
