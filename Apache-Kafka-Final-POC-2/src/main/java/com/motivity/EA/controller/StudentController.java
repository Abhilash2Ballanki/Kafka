package com.motivity.EA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.motivity.EA.model.StudentDetails;
import com.motivity.EA.repository.StudentDetailsRepository;
import com.motivity.EA.service.StudentDetailsConsumer;

@RestController
@RequestMapping("/db")
public class StudentController {
	
	@Autowired
	private StudentDetailsConsumer consumer;
	
	@GetMapping("/getAll")
	public ResponseEntity<List<StudentDetails>> getAll(){
		return new ResponseEntity<List<StudentDetails>>(consumer.getAllStudents(), HttpStatus.OK);
	}
	
	@GetMapping("/getById")
	public ResponseEntity<StudentDetails> getById(@RequestParam int studentId){
		return new ResponseEntity<StudentDetails>(consumer.getById(studentId), HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteById")
	public ResponseEntity<String> deleteById(@RequestParam int studentId){
		return new ResponseEntity<String>(consumer.deleteById(studentId), HttpStatus.OK);
	}

}
