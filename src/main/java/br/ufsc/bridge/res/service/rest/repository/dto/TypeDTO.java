package br.ufsc.bridge.res.service.rest.repository.dto;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.ufsc.bridge.res.domain.TipoDocumento;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeDTO {

	public static final TypeDTO DEFAULT = new TypeDTO();

	private List<CodingDTO> coding = Arrays.asList(new CodingDTO());

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	public static class CodingDTO {

		private String system = "http://rnds.saude.gov.br/fhir/r4/CodeSystem/rnds-openehrdocumenttype-1.0";

		private String code = TipoDocumento.REGISTRO_ATENDIMENTO_CLINICO.getCodigo();
	}
}
