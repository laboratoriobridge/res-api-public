package br.ufsc.bridge.res.service.rest.repository;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.net.ssl.SSLContext;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import br.ufsc.bridge.res.domain.Sort;
import br.ufsc.bridge.res.service.rest.repository.dto.ItemDTO;
import br.ufsc.bridge.res.service.rest.repository.dto.RestRepositorySaveDTO;
import br.ufsc.bridge.res.service.rest.repository.dto.ResultDTO;
import br.ufsc.bridge.res.service.rest.repository.dto.SaveDTO;

@Slf4j
public class RestRepositoryService {

	private static final String URL_TEMPLATE = "%s?subject=%s&_sort=%sdate";

	private final String url;
	private final HttpComponentsClientHttpRequestFactory httpRequestFactory;

	public RestRepositoryService(String url) throws NoSuchAlgorithmException, KeyManagementException {
		this.url = url;

		SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
		sslContext.init(null, null, null);

		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
				sslContext,
				new String[] { "TLSv1.2", "TLSv1.1", "TLSv1" },
				null,
				SSLConnectionSocketFactory.getDefaultHostnameVerifier());

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
		this.httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	public String save(SaveDTO dto) {
		RestRepositorySaveDTO restDto = new RestRepositorySaveDTO(dto);
		if (log.isDebugEnabled()) {
			log.debug(restDto.stringfy());
		}
		ResponseEntity<RestRepositorySaveDTO> response = new RestTemplate(this.httpRequestFactory)
				.exchange(this.url, HttpMethod.POST, new HttpEntity<>(restDto), RestRepositorySaveDTO.class);
		String docId = response.getHeaders().getLocation().getRawPath();

		log.debug(docId);
		return docId.substring(docId.lastIndexOf("/") + 1);

	}

	public SaveDTO read(String uuid) {
		ResponseEntity<RestRepositorySaveDTO> response = new RestTemplate(this.httpRequestFactory)
				.getForEntity(this.url + "/" + uuid, RestRepositorySaveDTO.class);
		if (log.isDebugEnabled()) {
			log.debug(response.getBody().stringfy());
		}
		return response.getBody().toDto();
	}

	public List<ItemDTO> list(String pacienteId, Sort sort) {
		ResponseEntity<ResultDTO> response = new RestTemplate(this.httpRequestFactory)
				.getForEntity(String.format(URL_TEMPLATE, this.url, pacienteId, sort.getCode()), ResultDTO.class);
		return response.getBody().toItems();
	}

}
