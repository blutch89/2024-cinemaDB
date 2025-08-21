package ch.blutch.cinemadb.consumer.impl.dao.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import ch.blutch.cinemadb.consumer.properties.ApiProperties;

public abstract class AbstractWsDaoImpl {

	@Autowired
	protected ApiProperties apiProperties;
	
	protected WebClient createWebClient() {
		String apiUrl = this.apiProperties.getUrl();
		return WebClient.create(apiUrl);
	}
	
	protected String createUri(String uri, String[] queryParams) {
		String queryParamsStr = String.join("&", queryParams);
		String apiKey = this.apiProperties.getKey();
		String newUri = uri + "?api_key=" + apiKey + "&language=fr-FR&" + queryParamsStr;
		
		return newUri;
	}
	
}
