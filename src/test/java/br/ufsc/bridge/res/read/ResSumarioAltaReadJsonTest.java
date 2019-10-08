package br.ufsc.bridge.res.read;

import static br.ufsc.bridge.res.dab.domain.ResABCriticidadeEnum.BAIXO;
import static br.ufsc.bridge.res.dab.domain.ResABTipoProblemaDiagnostico.CID10_201812;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.bridge.res.sumarioalta.dto.ResSumarioAlta;

public class ResSumarioAltaReadJsonTest {

	private ResSumarioAlta form;

	@Before
	public void setup() throws IOException {
		this.form = ResSumarioAlta.readJsonBase64(
				Base64.encodeBase64String(IOUtils.toString(ResSumarioAltaReadJsonTest.class.getResourceAsStream("/exemplo-sumario-alta.json")).getBytes()));
	}

	@Test
	public void checkReadValues() {
		assertEquals(new Date(1483228800000L), this.form.getDataAtendimento());
		assertEquals("Hospital Universitário Walter Cantídio", this.form.getEstabelecimento());
		assertEquals("0", this.form.getEquipe());
		assertEquals(1, this.form.getProblemasDiagnostico().size());
		assertEquals("Infarto agudo transmural da parede inferior do miocárdio", this.form.getProblemasDiagnostico().get(0).getDescricao());
		assertEquals("I21.1", this.form.getProblemasDiagnostico().get(0).getCodigo());
		assertEquals(CID10_201812, this.form.getProblemasDiagnostico().get(0).getTipo());
		assertEquals(1, this.form.getProcedimentos().size());
		assertEquals("TRATAMENTO DE INFARTO AGUDO DO MIOCÁRDIO", this.form.getProcedimentos().get(0).getNome());
		assertEquals("03.03.06.019-0", this.form.getProcedimentos().get(0).getCodigo());
		assertEquals("SIGTAP", this.form.getProcedimentos().get(0).getClassificacao());
		assertEquals("Concluído", this.form.getProcedimentos().get(0).getStatus());
		assertEquals(1, this.form.getResumos().size());
		assertEquals("Paciente internado com precordialgia à esquerda, durante o atendimento apresentou IAM inferior Killip II.", this.form.getResumos().get(0).getTexto());
		assertEquals(1, this.form.getAlergias().size());
		assertEquals("Lactose", this.form.getAlergias().get(0).getAgente());
		assertEquals("Alimento", this.form.getAlergias().get(0).getCategoria());
		assertEquals(BAIXO, this.form.getAlergias().get(0).getCriticidade());
		assertEquals(1, this.form.getAlergias().get(0).getEventoReacao().size());
		assertEquals("Inflamação na pele", this.form.getAlergias().get(0).getEventoReacao().get(0).getEvolucaoAlergia());
		assertEquals("Pele alergica e espinhas", this.form.getAlergias().get(0).getEventoReacao().get(0).getManifestacao());
		assertEquals(new Date(1483228800000L), this.form.getAlergias().get(0).getEventoReacao().get(0).getDataInstalacao());
		assertEquals(1, this.form.getMedicamentosNaoEstruturados().size());
		assertEquals(5, this.form.getMedicamentosNaoEstruturados().get(0).getDescricoes().size());
		assertEquals("1) captopril 25 mg, oral, 1x/dia", this.form.getMedicamentosNaoEstruturados().get(0).getDescricoes().get(0));
		assertEquals(" 2) clopidogrel 75mg, oral,1x/dia, por 30 dias", this.form.getMedicamentosNaoEstruturados().get(0).getDescricoes().get(1));
		assertEquals(" 3) AAS 100mg, oral, 1x/dia pela manhã", this.form.getMedicamentosNaoEstruturados().get(0).getDescricoes().get(2));
		assertEquals(" 4) metoprolol 25 mg, oral, 1x/dia", this.form.getMedicamentosNaoEstruturados().get(0).getDescricoes().get(3));
		assertEquals(" 5) sinvastatina 20 mg, oral, 2x/dia.", this.form.getMedicamentosNaoEstruturados().get(0).getDescricoes().get(4));
		assertEquals("Evitar consumir alimentos salgados", this.form.getPlanoCuidado());
		assertEquals("Alta clínica", this.form.getMotivoAlta());
		assertEquals("162338254590005", this.form.getProfissional().getCns());
		assertEquals("Médico cirurgião cardiovascular", this.form.getProfissional().getCbo());
		assertEquals(new Date(1483228800000L), this.form.getDataAlta());

	}

}
