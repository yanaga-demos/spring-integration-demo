package br.com.yanaga.javaone.sqs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

@MessageEndpoint
public class SqsSendingService {

	@Autowired
	private AmazonSQS amazonSQS;

	@Autowired
	private Environment env;

	@ServiceActivator
	public void send(String url) {
		String queueUrl = amazonSQS.getQueueUrl(new GetQueueUrlRequest(env.getProperty("aws.queue"))).getQueueUrl();
		amazonSQS.sendMessage(new SendMessageRequest(queueUrl, url));
	}

}