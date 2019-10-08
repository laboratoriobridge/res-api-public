package br.ufsc.bridge.res.service.rest.repository.dto;

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
public class ItemDTO {

	private String uuid;

	private String url;

	private TipoDocumento tipoDocumento;

}
