package com.motivity.EA.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.motivity.EA.kafka.KafkaJsonProducer;
import com.motivity.schema.Student;
@RestController
@RequestMapping("/student")
public class StudentDetailsController {
	
	@Autowired
	private KafkaJsonProducer jsonProducer;
	
	@PostMapping("/create")
	public ResponseEntity<String> publishDetails(@RequestBody Student details){
		jsonProducer.publishMessage(details);
		return new ResponseEntity<String>("Your message is succesfully sent to kafka server", HttpStatus.OK);
	}

}
