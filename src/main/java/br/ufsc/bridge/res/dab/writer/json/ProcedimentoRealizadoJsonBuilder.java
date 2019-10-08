package br.ufsc.bridge.res.dab.writer.json;

import java.util.Date;

import br.ufsc.bridge.res.util.RDateUtil;
import br.ufsc.bridge.res.util.json.BaseJsonBuilder;

public class ProcedimentoRealizadoJsonBuilder<T extends BaseJsonBuilder<?>> extends BaseJsonBuilder<T> {

	private String procedimentoJson;

	public ProcedimentoRealizadoJsonBuilder(T parent) {
		super(parent, "procedimento-realizado");
	}

	public ProcedimentoJsonBuilder procedimento() {
		return new ProcedimentoJsonBuilder(this);
	}

	@Override
	protected String childJsonPath() {
		return "$.items";
	}

	public class ProcedimentoJsonBuilder extends BaseJsonBuilder<ProcedimentoRealizadoJsonBuilder<T>> {

		private ProcedimentoJsonBuilder(ProcedimentoRealizadoJsonBuilder<T> parent) {
			super(parent, "procedimento");
		}

		public ProcedimentoJsonBuilder data(Date data) {
			this.document.set("$.time.value", RDateUtil.dateToISOEHR(data));
			this.document.set("$.description.items[?(@.name.value == 'Data da realização')].value.value", RDateUtil.dateToISOEHR(data));
			return this;
		}

		public ProcedimentoJsonBuilder descricao(String descricao) {
			this.document.set("$.description.items[?(@.name.value == 'Procedimento SUS')].value.value", descricao);
			return this;
		}

		public ProcedimentoJsonBuilder codigo(String codigo) {
			this.document.set("$.description.items[?(@.name.value == 'Procedimento SUS')].value.defining_code.code_string", codigo);
			return this;
		}
	}
}
