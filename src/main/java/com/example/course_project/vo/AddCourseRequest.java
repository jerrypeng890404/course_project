package com.example.course_project.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCourseRequest {

	@JsonProperty("id")
	private String id;

	@JsonProperty("studentid")
	private String studentId;

	@JsonProperty("studentname")
	private String studentName;
	
	@JsonProperty("coursecode")
	private String courseCode;

	@JsonProperty("coursename")
	private String courseName;
	
	public AddCourseRequest() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
}
