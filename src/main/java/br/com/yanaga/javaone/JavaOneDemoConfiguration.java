package br.com.yanaga.javaone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;

@Configuration
@ComponentScan("br.com.yanaga.javaone")
@PropertySource("classpath:META-INF/spring/aws.properties")
@ImportResource("classpath*:META-INF/spring/integration-*.xml")
public class JavaOneDemoConfiguration {

	@Autowired
	private Environment env;

	@Bean
	public AWSCredentials awsCredentials() {
		return new BasicAWSCredentials(env.getProperty("aws.accessKey"), env.getProperty("aws.secretKey"));
	}

	@Bean
	public AmazonS3 amazonS3() {
		return new AmazonS3Client(awsCredentials());
	}

	@Bean
	public AmazonSQS amazonSQS() {
		return new AmazonSQSClient(awsCredentials());
	}

}