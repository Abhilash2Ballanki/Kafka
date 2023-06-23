package com.motivity.EA.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StudentDetails {

	@Id
	private int studentId;
	private String studentName;
	private String course;
	private String address;
	private long phoneNumber;
	private String email;
	private int age;

}
