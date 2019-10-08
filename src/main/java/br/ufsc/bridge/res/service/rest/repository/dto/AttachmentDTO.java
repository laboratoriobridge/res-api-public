package br.ufsc.bridge.res.service.rest.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentDTO {

	private DataDTO attachment;

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public static class DataDTO {

		@Builder.Default
		private String contentType = "application/json";

		private String data;

		private String url;

	}

}
