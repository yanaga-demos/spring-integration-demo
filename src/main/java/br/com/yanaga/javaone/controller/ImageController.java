package br.com.yanaga.javaone.controller;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;

@Component
public class ImageController {

	@Autowired
	private AmazonS3 amazonS3;

	public List<String> getImages() {
		List<String> response = new LinkedList<String>();
		ObjectListing objectListing = amazonS3.listObjects("javaone.yanaga.com.br");
		List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();
		for (S3ObjectSummary s3ObjectSummary : objectSummaries) {
			response.add(s3ObjectSummary.getKey());
		}
		return response;
	}

}