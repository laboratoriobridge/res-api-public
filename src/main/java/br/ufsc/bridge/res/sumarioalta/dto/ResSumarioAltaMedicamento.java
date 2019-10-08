package br.ufsc.bridge.res.sumarioalta.dto;

import java.io.Serializable;

import javax.xml.xpath.XPathExpressionException;

import br.ufsc.bridge.soap.xpath.XPathFactoryAssist;

// medicamento estruturado nao ira aparecer por enquanto, deixarei comentado so para manter historico
public class ResSumarioAltaMedicamento implements Serializable {

	private static final long serialVersionUID = 1L;
	// se voltar a fazer isso daqui, verificar a questão dose estruturada ser 0..*, não chequei se no AB é assim tbm, mas lá não foi considerado
	private String nomeMedicamento;
	private String descricaoViaAdministracao;
	private String descricaoDose;
	private String duracaoTratamento;

	public ResSumarioAltaMedicamento(XPathFactoryAssist xPathMedicamentoEstruturado) throws XPathExpressionException {
		String startXPath = "./data/Lista_de_medicamentos_da_alta__openBrkt_estruturada_closeBrkt_";
		this.nomeMedicamento = xPathMedicamentoEstruturado.getString(startXPath + "/Medicamento/value/value");
		this.descricaoViaAdministracao = xPathMedicamentoEstruturado.getString(startXPath + "/Via_de_administração/value/value");
		this.descricaoDose = xPathMedicamentoEstruturado.getString(startXPath + "/Dose_estruturada/Dose/value/value");
		this.duracaoTratamento = xPathMedicamentoEstruturado.getString(startXPath + "/Dose_estruturada/Duração_de_uso_do_medicamento/value/value");
	}

}
