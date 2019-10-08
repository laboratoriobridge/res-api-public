package br.ufsc.bridge.res.dab.writer.json;

import java.util.Date;

import br.ufsc.bridge.res.dab.domain.ResABAleitamentoMaternoEnum;
import br.ufsc.bridge.res.util.RDateUtil;
import br.ufsc.bridge.res.util.json.BaseJsonBuilder;

public class MedicoesObservacoesJsonBuilder<T extends BaseJsonBuilder<?>> extends BaseJsonBuilder<T> {

	private boolean hasPeso;
	private boolean hasAltura;
	private boolean hasPerimetroCefalico;

	private boolean hasCicloMenstrual;
	private boolean hasGestacao;
	private boolean hasGestasPrevias;
	private boolean hasPartos;
	private boolean hasAleitamentoMaterno;

	public MedicoesObservacoesJsonBuilder(T parent) {
		super(parent, "medicoes-observacoes");
	}

	public MedicoesObservacoesJsonBuilder<T> pesoCorporal(Date data, String peso) {
		this.hasPeso = peso != null;
		if (this.hasPeso) {
			this.document.set("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Peso corporal')].data..time.value", RDateUtil.dateToISOEHR(data));
			this.document.set("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Peso corporal')].data..magnitude", peso);
		}
		return this;
	}

	public MedicoesObservacoesJsonBuilder<T> altura(Date data, String altura) {
		this.hasAltura = altura != null;
		if (this.hasAltura) {
			this.document.set("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Altura / comprimento')].data..time.value", RDateUtil.dateToISOEHR(data));
			this.document.set("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Altura / comprimento')].data..magnitude", altura);
		}
		return this;
	}

	public MedicoesObservacoesJsonBuilder<T> perimetroCefalico(Date data, String perimetro) {
		this.hasPerimetroCefalico = perimetro != null;
		if (this.hasPerimetroCefalico) {
			this.document.set("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Perímetro cefálico')].data..time.value", RDateUtil.dateToISOEHR(data));
			this.document.set("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Perímetro cefálico')].data..magnitude", perimetro);
		}
		return this;
	}

	public MedicoesObservacoesJsonBuilder<T> cicloMenstrual(Date data, Date dum) {
		this.hasCicloMenstrual = dum != null;
		if (this.hasCicloMenstrual) {
			this.document.set("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Ciclo menstrual')].data..time.value", RDateUtil.dateToISOEHR(data));
			this.document.set("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Ciclo menstrual')].data.events.data.items.value.value", RDateUtil.dateToISOEHR(dum));
		}
		return this;
	}

	public MedicoesObservacoesJsonBuilder<T> gestacao(Date data, String idadeGestacional) {
		this.hasGestacao = idadeGestacional != null;
		if (this.hasGestacao) {
			this.document.set("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Gestação')].data..time.value", RDateUtil.dateToISOEHR(data));
			this.document.set("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Gestação')].data.events.data.items.value.value", idadeGestacional);
		}
		return this;
	}

	public MedicoesObservacoesJsonBuilder<T> sumarioObstetrico(String gestasPrevias, String partos) {
		this.hasGestasPrevias = gestasPrevias != null;
		if (this.hasGestasPrevias) {
			this.document.set("$.items[?(@.name.value == 'Informações adicionais')]..items[?(@.name.value == 'Quantidade de gestas prévias')].value.magnitude", gestasPrevias);
		}
		this.hasPartos = partos != null;
		if (this.hasPartos) {
			this.document.set("$.items[?(@.name.value == 'Informações adicionais')]..items[?(@.name.value == 'Quantidade de partos')].value.magnitude", partos);
		}
		return this;
	}

	public MedicoesObservacoesJsonBuilder<T> aleitamentoMaterno(Date data, ResABAleitamentoMaternoEnum aleitamentoMaterno) {
		this.hasAleitamentoMaterno = aleitamentoMaterno != null;
		if (this.hasAleitamentoMaterno) {
			this.document.set("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Alimentação da criança menor de 2 anos')].data..time.value", RDateUtil.dateToISOEHR(data));
			this.document.set(
					"$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Alimentação da criança menor de 2 anos')].data.events.data.items.value.value",
					aleitamentoMaterno.getDescricao());
		}
		return this;
	}

	@Override
	public Object json() {
		boolean hasMedicoes = this.hasPeso || this.hasAltura || this.hasPerimetroCefalico;
		boolean hasSumarioObstetrico = this.hasGestasPrevias || this.hasPartos;
		boolean hasInfoAdicionais = this.hasCicloMenstrual || this.hasGestacao || hasSumarioObstetrico || this.hasAleitamentoMaterno;

		if (!hasMedicoes && !hasInfoAdicionais) {
			return null;
		}

		if (!hasMedicoes) {
			this.document.delete("$.items[?(@.name.value == 'Medições')]");
		} else {
			if (!this.hasPeso) {
				this.document.delete("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Peso corporal')]");
			}
			if (!this.hasAltura) {
				this.document.delete("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Altura / comprimento')]");
			}
			if (!this.hasPerimetroCefalico) {
				this.document.delete("$.items[?(@.name.value == 'Medições')].items[?(@.name.value == 'Perímetro cefálico')]");
			}
		}

		if (!hasInfoAdicionais) {
			this.document.delete("$.items[?(@.name.value == 'Informações adicionais')]");
		} else {
			if (!this.hasCicloMenstrual) {
				this.document.delete("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Ciclo menstrual')]");
			}
			if (!this.hasGestacao) {
				this.document.delete("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Gestação')]");
			}

			if (!hasSumarioObstetrico) {
				this.document.delete("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Sumário obstétrico')]");
			} else {
				if (!this.hasGestasPrevias) {
					this.document.delete("$.items[?(@.name.value == 'Informações adicionais')]..items[?(@.name.value == 'Quantidade de gestas prévias')]");
				}
				if (!this.hasPartos) {
					this.document.delete("$.items[?(@.name.value == 'Informações adicionais')]..items[?(@.name.value == 'Quantidade de partos')]");
				}
			}

			if (!this.hasAleitamentoMaterno) {
				this.document.delete("$.items[?(@.name.value == 'Informações adicionais')].items[?(@.name.value == 'Alimentação da criança menor de 2 anos')]");
			}
		}
		return super.json();
	}
}
