package br.com.yanaga.javaone.sqs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.integration.annotation.MessageEndpoint;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

@MessageEndpoint
public class SqsReceivingService {

	@Autowired
	private AmazonSQS amazonSQS;

	@Autowired
	private Environment env;

	public String receive() {
		String queueUrl = amazonSQS.getQueueUrl(new GetQueueUrlRequest(env.getProperty("aws.queue"))).getQueueUrl();
		ReceiveMessageResult receiveMessageResult = amazonSQS.receiveMessage(new ReceiveMessageRequest(queueUrl)
				.withMaxNumberOfMessages(1));
		List<Message> messages = receiveMessageResult.getMessages();
		if (messages.isEmpty()) {
			return null;
		}
		Message message = messages.iterator().next();
		String body = message.getBody();
		amazonSQS.deleteMessage(new DeleteMessageRequest(queueUrl, message.getReceiptHandle()));
		return body;
	}

}