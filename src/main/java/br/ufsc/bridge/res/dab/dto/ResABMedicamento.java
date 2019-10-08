package br.ufsc.bridge.res.dab.dto;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import br.ufsc.bridge.res.dab.domain.ResABEstadoMedicamentoEnum;
import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ResABMedicamento implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nomeMedicamento;
	private String codigoMedicamentoCatmat;
	private String descricaoFormaFarmaceutica;
	private String codigoFormaFarmaceutica;
	private String descricaoViaAdministracao;
	private String codigoViaAdministracao;
	private String descricaoDose;
	private String duracaoTratamento;
	private ResABEstadoMedicamentoEnum estadoMedicamento;

	public ResABMedicamento(XPathFactoryAssist xPathMedicamento) throws XPathExpressionException {
		this.nomeMedicamento = xPathMedicamento.getString("./Medicamento/value/value");
		this.codigoMedicamentoCatmat = xPathMedicamento.getString("./Medicamento/value/defining_code/code_string");
		this.descricaoFormaFarmaceutica = xPathMedicamento.getString("./Forma_farmacêutica/value/value");
		this.codigoFormaFarmaceutica = xPathMedicamento.getString("./Forma_farmacêutica/value/defining_code/code_string");
		this.descricaoViaAdministracao = xPathMedicamento.getString("./Via_de_administração/value/value");
		this.codigoViaAdministracao = xPathMedicamento.getString("./Via_de_administração/value/defining_code/code_string");
		this.descricaoDose = xPathMedicamento.getString("./Dose_estruturada/Dose/value/value");
		this.duracaoTratamento = xPathMedicamento.getString("./Dose_estruturada/Duração_do_tratamento/value/value");
		this.estadoMedicamento = ResABEstadoMedicamentoEnum
				.getByCodigo(xPathMedicamento.getString("./Detalhes_do_processo_medicação/Estado_do_medicamento/value/defining_code/code_string"));
	}
}
