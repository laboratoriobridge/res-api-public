package br.ufsc.bridge.res.service.rest.repository.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import br.ufsc.bridge.res.domain.TipoDocumento;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaveDTO {

	private String id;

	private Date data;

	private String unidadeId;

	private String profissionalId;

	private String pacienteId;

	private TipoDocumento tipoDocumento;

	private String documento;
}
