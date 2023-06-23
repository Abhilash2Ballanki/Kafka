package com.motivity.EA.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaDetailsConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDetailsConsumer.class);

	@KafkaListener(topics = "${topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeAcknowledgement(String message) {
		LOGGER.info("The acknowledgement received is {}", message);
	}

}
