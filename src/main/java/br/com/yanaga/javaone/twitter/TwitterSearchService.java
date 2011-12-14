package br.com.yanaga.javaone.twitter;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.web.client.RestTemplate;

@MessageEndpoint
public class TwitterSearchService {

	public String search() {
		String uri = "http://search.twitter.com/search.json?q={query}&include_entities=true";
		return new RestTemplate().getForObject(uri, String.class, "#javaonebrasil");
	}

}