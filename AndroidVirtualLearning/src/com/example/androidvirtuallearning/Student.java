package com.example.androidvirtuallearning;

import java.util.ArrayList;

import android.graphics.Bitmap;

public class Student 
{
	private String studentID, lastname, firstname, middleInitial, username, password;
	private ArrayList<Subject> subjects;
	private Bitmap userImage;
	
	
	public void AddSubject(Subject subject)
	{
		if(subjects == null)
		{
			subjects = new ArrayList<Subject>();
		}
		
		subjects.add(subject);
	}
	
	public ArrayList<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(ArrayList<Subject> subjects) {
		this.subjects = subjects;
	}
	public Bitmap getUserImage() {
		return userImage;
	}

	public void setUserImage(Bitmap userImage) {
		this.userImage = userImage;
	}

	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<String> getSubjectCodes()
	{
		ArrayList<String> subjectNames = new ArrayList<String>();
		
		for(Subject sub : this.subjects)
		{
			subjectNames.add(sub.getCode());
		}
		
		return subjectNames;
	}
	
	public ArrayList<String> getSubjectNames()
	{
		ArrayList<String> subjectNames = new ArrayList<String>();
		
		for(Subject sub : this.subjects)
		{
			subjectNames.add(sub.getName());
		}
		
		return subjectNames;
	}
}
