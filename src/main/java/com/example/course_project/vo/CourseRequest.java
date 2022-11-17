package com.example.course_project.vo;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CourseRequest {

	//在外部輸入的請求
	@JsonProperty("coursecode")
	private String coursecode;

	@JsonProperty("coursename")
	private String coursename;

	@JsonProperty("courseday")
	private String courseday;

	@JsonProperty("coursestart")
	private LocalTime coursestart;

	@JsonProperty("courseend")
	private LocalTime courseend;

	@JsonProperty("credit")
	private Integer credit;
	
	public CourseRequest() {
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

	public String getCourseday() {
		return courseday;
	}

	public void setCourseday(String courseday) {
		this.courseday = courseday;
	}

	public LocalTime getCoursestart() {
		return coursestart;
	}

	public void setCoursestart(LocalTime coursestart) {
		this.coursestart = coursestart;
	}

	public LocalTime getCourseend() {
		return courseend;
	}

	public void setCourseend(LocalTime courseend) {
		this.courseend = courseend;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

}
