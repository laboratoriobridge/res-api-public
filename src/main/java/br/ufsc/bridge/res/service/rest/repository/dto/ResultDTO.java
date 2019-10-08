package br.ufsc.bridge.res.service.rest.repository.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import br.ufsc.bridge.res.domain.TipoDocumento;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

	private Long total;

	private List<EntryDTO> entry;

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class EntryDTO {

		private String fullUrl;

		private ResourceDTO resource;

	}

	@Getter
	@Setter
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ResourceDTO {

		private String id;

		private MetaDTO meta;

		private String status;

		private TypeDTO type;

		private ReferenceDTO subject;

		private Date date;

		private List<ReferenceDTO> author;

		private List<AttachmentDTO> content;

	}

	public List<ItemDTO> toItems() {
		List<ItemDTO> items = new ArrayList<>();
		if (this.entry != null) {
			for (EntryDTO entry : this.entry) {
				ItemDTO item = new ItemDTO();
				item.setUuid(entry.getResource().getId());
				item.setUrl(entry.getFullUrl());
				if (entry.getResource().getType() != null) {
					item.setTipoDocumento(TipoDocumento.getByCodigo(entry.getResource().getType().getCoding().get(0).getCode()));
				}
				items.add(item);
			}
		}
		return items;
	}
}
