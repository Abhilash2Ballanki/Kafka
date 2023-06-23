package com.motivity.EA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDetails {
	
	private int studentId;
	private String studentName;
	private String course;
	private String address;
	private long phoneNumber;
	private String email;
	private int age;

}
