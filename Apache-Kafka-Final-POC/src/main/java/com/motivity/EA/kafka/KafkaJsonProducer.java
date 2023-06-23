package com.motivity.EA.kafka;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.motivity.schema.Student;

@Service
public class KafkaJsonProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaJsonProducer.class);

	@Value("${spring.kafka.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, Student> kafkaTemplate;

	public KafkaJsonProducer(KafkaTemplate<String, Student> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	public void publishMessage(Student studentData) {

		LOGGER.info("Message Object is being creating");
		LOGGER.info("Message is ready");
		try {
			ProducerRecord<String, Student> producerRecord = new ProducerRecord<String, Student>(topicName, studentData);
			ListenableFuture<SendResult<String, Student>> futureResult = kafkaTemplate.send(producerRecord);
			futureResult.addCallback(new ListenableFutureCallback<SendResult<String,Student>>(){

				@Override
				public void onSuccess(SendResult<String, Student> result) {
					LOGGER.info("Your message is successfully sent ");
				}

				@Override
				public void onFailure(Throwable ex) {
					LOGGER.error("Your message is not sent successfully and cause is {}",ex.getMessage());
				}
				
			});
		} catch (Exception exception) {
			LOGGER.error("Error is encountered and error message is {}", exception.getMessage());
		}
		LOGGER.info("Message is sent to {} with message {}", topicName, studentData.toString());
	}

}
