package br.ufsc.bridge.res.write;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import br.ufsc.bridge.res.dab.domain.ResABAleitamentoMaternoEnum;
import br.ufsc.bridge.res.dab.domain.ResABCondutaEnum;
import br.ufsc.bridge.res.dab.domain.ResABCriticidadeEnum;
import br.ufsc.bridge.res.dab.domain.ResABTipoAtendimentoEnum;
import br.ufsc.bridge.res.dab.dto.ResABResumoConsulta;
import br.ufsc.bridge.res.dab.writer.json.CaracterizacaoConsultaABJsonBuilder;
import br.ufsc.bridge.res.dab.writer.json.ResumoConsultaABJsonBuilder;
import br.ufsc.bridge.res.util.json.ResJsonUtils;

public class ResumoConsultaABJsonTest {

	@Test
	public void test() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IOException {

		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("exemplo-clinico.json");
		ResABResumoConsulta resumo = ResJsonUtils.readJson(Base64.encodeBase64String(IOUtils.toString(resourceAsStream).getBytes()), ResABResumoConsulta.class);
		System.out.println(resumo);
	}

	@Test
	public void test2() throws IOException {
		ResumoConsultaABJsonBuilder abBuilder = new ResumoConsultaABJsonBuilder().data(new Date());
		//@formatter:off
		CaracterizacaoConsultaABJsonBuilder<ResumoConsultaABJsonBuilder> caracterizacaoConsulta = abBuilder.caracterizacaoConsulta();
		caracterizacaoConsulta
				.tipoAtendimento(ResABTipoAtendimentoEnum.CONSULTA_AGENDADA_PROGRAMADA_CUIDADO_CONTINUADO)
				.localizacaoAtribuidaPaciente("1234567", "0123456789")
				.profissional()
					.nome("Mario da Silva")
					.cns("012345678901234")
					.cbo("1234567")
					.cboDescricao("Médico")
					.responsavel(true)
				.close()
				.profissional()
					.nome("Mario da Silva 2")
					.cns("432109876543210")
					.cbo("7654321")
					.cboDescricao("Médico da familia")
					.responsavel(false)
				.close()
				.dataHoraAdmissao(new Date())
			.close()
			.medicoesObservacoes()
				.pesoCorporal(new Date(), "11")
				.altura(new Date(), "22")
				.perimetroCefalico(new Date(), "33")
				.cicloMenstrual(new Date(), new Date())
				.gestacao(new Date(), "44")
				.sumarioObstetrico("555", "666")
				.aleitamentoMaterno(new Date(), ResABAleitamentoMaternoEnum.COMPLEMENTADO)
			.close()
			.problemaDiagnostico()
				.problema()
					.descricao("Gripe")
					.tipo("CID10")
					.codigo("123456")
				.close()
				.problema()
					.descricao("Fumante")
					.tipo("CID10")
					.codigo("654321")
				.close()
			.close()
			.alergiaReacao()
				.alergia()
					.agente("Camarão")
					.categoria("Animal")
					.criticidade(ResABCriticidadeEnum.ALTO)
					.evento()
						.dataInstalacao(new Date())
						.evolucao("melhor")
						.manifestacao("mancha da pele")
					.close()
					.evento()
						.evolucao("pior")
					.close()
				.close()
				.alergia()
					.agente("Arroz")
					.categoria("Vegetal")
					.evento()
						.dataInstalacao(null)
						.evolucao(null)
						.dataInstalacao(null)
					.close()
				.close()
			.close()
			.procedimentoRealizado()
				.procedimento()
					.descricao("CONSULTA MEDICA EM ATENÇAO BASICA")
					.data(new Date())
					.codigo("0301010064")
				.close()
				.procedimento()
					.descricao("Ultrasonografia")
					.data(new Date())
					.codigo("007")
				.close()
			.close()
			.prescricaoAtendimento()
				.medicamentoNaoEstruturado()
					.medicamentos(Arrays.asList("medi 1", "medi 2"))
				.close()
			.close()
//			.listaMedicamentos()
//				.itemMedicacao()
//					.medicamento("LAMIVUDINA 10 mg solução oral", "BR0328810")
//					.formaFarmaceutica("LAMIVUDINA 10 mg solução oral", "BR0328810")
//					.viaAdministracao("Oral", "25")
//					.dose("1 comp 30 min antes do almoço")
//					.doseEstruturada("P30D")
//				.close()
//				.itemMedicacao()
//					.medicamento("LERCANIDIPINO 10 mg comprimido", "BR0272229")
//					.formaFarmaceutica("LERCANIDIPINO 10 mg comprimido", "BR0272229")
//					.viaAdministracao("Oral", "25")
//					.dose("1 comp 30 min antes do almoço")
//					.doseEstruturada("P30D")
//				.close()
//			.close()
			.dadosDesfecho()
				.desfecho(ResABCondutaEnum.ENCAMINHAMENTO)
				.desfecho(ResABCondutaEnum.EVASAO)
			.close();

		String jsonString = abBuilder.getJsonString();
		System.out.println("--------------------------");
		System.out.println(jsonString);
	}
}
