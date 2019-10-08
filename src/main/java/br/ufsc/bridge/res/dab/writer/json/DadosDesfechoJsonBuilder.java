package br.ufsc.bridge.res.dab.writer.json;

import br.ufsc.bridge.res.dab.domain.ResABCondutaEnum;
import br.ufsc.bridge.res.util.json.BaseJsonBuilder;

public class DadosDesfechoJsonBuilder<T extends BaseJsonBuilder<?>> extends BaseJsonBuilder<T> {

	private String desfechoJson;

	public DadosDesfechoJsonBuilder(T parent) {
		super(parent, "dados-desfecho");
	}

	public DadosDesfechoJsonBuilder<T> desfecho(ResABCondutaEnum desfecho) {
		new DesfechoJsonBuilder(this, this.desfechoJson, desfecho);
		return this;
	}

	@Override
	protected String childJsonPath() {
		return "$.items.data.items";
	}

	public class DesfechoJsonBuilder extends BaseJsonBuilder<DadosDesfechoJsonBuilder<T>> {

		private DesfechoJsonBuilder(DadosDesfechoJsonBuilder<T> parent, String json, ResABCondutaEnum desfecho) {
			super(parent, "desfecho");
			this.document.set("$.value.value", desfecho.getDescricao());
			this.document.set("$.value.defining_code.code_string", desfecho.getCodigo());
		}
	}
}
