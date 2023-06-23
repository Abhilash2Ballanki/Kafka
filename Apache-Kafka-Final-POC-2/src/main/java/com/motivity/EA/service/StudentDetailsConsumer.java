package com.motivity.EA.service;

import java.util.List;
import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motivity.EA.model.StudentDetails;
import com.motivity.EA.repository.StudentDetailsRepository;
import com.motivity.schema.Student;


@Service
public class StudentDetailsConsumer {

	@Value("${topic.name}")
	private String prodTopicName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public StudentDetailsConsumer(KafkaTemplate<String, String> kafkaTemplate) {
		super();
		this.kafkaTemplate = kafkaTemplate;
	}

	@Autowired
	private StudentDetailsRepository detailsRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentDetailsConsumer.class);

	@KafkaListener(topics = "Details-student", groupId = "${spring.kafka.consumer.group-id}")
	public void consumeAndSaveMessage(ConsumerRecord<String, Student> record) {
		System.out.println(record.value());
		ObjectMapper map = new ObjectMapper();
		//map.readValues(record.value(), StudentDetails.class);
//		Student details = record.value();
		LOGGER.info("entered into consumerMethod");
		LOGGER.info("Message recieved is {} ", record.value());
		ModelMapper mapper = new ModelMapper();
		StudentDetails changedData = mapper.map(record.value(), StudentDetails.class);
		StudentDetails studentDetails = detailsRepository.save(changedData);
		if (studentDetails != null) {
			LOGGER.info("Student is successfully saved and we are sending the acknowledgment");
			String message = "Data is successfully saved in the database";
			kafkaTemplate.send(prodTopicName, message);
		} else {
			LOGGER.info("Student is not successfully saved and we are sending the acknowledgment");
			String message = "We got an issuse while saving the data please enter valid data";
			kafkaTemplate.send(prodTopicName, message);
		}
	}

	public List<StudentDetails> getAllStudents() {
		return detailsRepository.findAll();
	}

	public StudentDetails getById(int studentId) {
		Optional<StudentDetails> student = detailsRepository.findById(studentId);
		if (student.isPresent()) {
			return student.get();
		} else {
			throw new RuntimeException("Student details is not found with id " + studentId);
		}
	}

	public String deleteById(int studentId) {
		detailsRepository.deleteById(studentId);
		return "successfully deleted";
	}
}
