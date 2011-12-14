package br.com.yanaga.javaone.twitter;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;

import br.com.yanaga.javaone.twitter.SearchResults.Result;

@MessageEndpoint
public class TwitterMediaUrlSplitter {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Splitter
	public List<String> extractMediaUrlIfPresent(SearchResults searchResults) {
		LinkedList<String> mediaUrls = new LinkedList<String>();
		for (Result result : searchResults.getResults()) {
			String profileImageUrl = result.getProfileImageUrl();
			if (!profileImageUrl.isEmpty()) {
				logger.debug(String.format("Adicionando foto de perfil: '%s'", profileImageUrl));
				mediaUrls.add(profileImageUrl);
			}
		}
		return mediaUrls;
	}

}