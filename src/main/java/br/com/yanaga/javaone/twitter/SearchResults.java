package br.com.yanaga.javaone.twitter;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResults {

	private List<Result> results;

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Result {

		@JsonProperty("profile_image_url")
		private String profileImageUrl;

		public String getProfileImageUrl() {
			return profileImageUrl;
		}

		public void setProfileImageUrl(String profileImageUrl) {
			this.profileImageUrl = profileImageUrl;
		}

	}

}