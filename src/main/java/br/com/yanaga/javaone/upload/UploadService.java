package br.com.yanaga.javaone.upload;

import java.io.ByteArrayInputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@MessageEndpoint
public class UploadService {

	@Autowired
	private AmazonS3 amazonS3;

	@Autowired
	private Environment env;

	public boolean notContains(String url) {
		ObjectListing objectListing = amazonS3.listObjects(env.getProperty("aws.bucket"), convertFilename(url));
		return objectListing.getObjectSummaries().isEmpty();
	}

	private String convertFilename(String url) {
		return DigestUtils.shaHex(url);
	}

	@ServiceActivator
	public void upload(String url) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> responseEntity = restTemplate.getForEntity(url, byte[].class);
		String contentType = responseEntity.getHeaders().getContentType().toString();
		byte[] bytes = responseEntity.getBody();

		ObjectMetadata objectMetadata = new ObjectMetadata();
		objectMetadata.setContentType(contentType);
		objectMetadata.setContentLength(bytes.length);
		PutObjectRequest putObjectRequest = new PutObjectRequest(env.getProperty("aws.bucket"), convertFilename(url),
				new ByteArrayInputStream(bytes), objectMetadata);
		amazonS3.putObject(putObjectRequest);
	}

}