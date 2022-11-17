package com.example.course_project.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddCourseRequest {

	@JsonProperty("id")
	private String id;

	@JsonProperty("studentid")
	private String studentid;

	@JsonProperty("studentname")
	private String studentname;
	
	@JsonProperty("coursecode")
	private String coursecode;

	@JsonProperty("coursename")
	private String coursename;
	
	public AddCourseRequest() {
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStudentid() {
		return studentid;
	}

	public void setStudentid(String studentid) {
		this.studentid = studentid;
	}

	public String getStudentname() {
		return studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getCoursecode() {
		return coursecode;
	}

	public void setCoursecode(String coursecode) {
		this.coursecode = coursecode;
	}

	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

}
