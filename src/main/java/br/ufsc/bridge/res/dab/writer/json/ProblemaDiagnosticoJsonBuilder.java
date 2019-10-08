package br.ufsc.bridge.res.dab.writer.json;

import br.ufsc.bridge.res.util.json.BaseJsonBuilder;

public class ProblemaDiagnosticoJsonBuilder<T extends BaseJsonBuilder<?>> extends BaseJsonBuilder<T> {

	public ProblemaDiagnosticoJsonBuilder(T parent) {
		super(parent, "problema-diagnostico");
	}

	public DiagnosticoJsonBuilder problema() {
		return new DiagnosticoJsonBuilder(this);
	}

	@Override
	protected String childJsonPath() {
		return "$.items";
	}

	public class DiagnosticoJsonBuilder extends BaseJsonBuilder<ProblemaDiagnosticoJsonBuilder<T>> {

		private DiagnosticoJsonBuilder(ProblemaDiagnosticoJsonBuilder<T> parent) {
			super(parent, "problema");
		}

		public DiagnosticoJsonBuilder descricao(String descricao) {
			this.document.set("$.data.items[0].value.value", descricao);
			return this;
		}

		public DiagnosticoJsonBuilder tipo(String tipo) {
			this.document.set("$.data.items[0].value.defining_code..value", tipo);
			return this;
		}

		public DiagnosticoJsonBuilder codigo(String codigo) {
			this.document.set("$.data.items[0].value.defining_code.code_string", codigo);
			return this;
		}
	}
}
