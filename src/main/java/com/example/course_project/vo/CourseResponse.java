package com.example.course_project.vo;

import java.time.LocalTime;
import java.util.List;

import com.example.course_project.entity.Course;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponse {
	
	private String coursecode;

	private String coursename;

	private String courseday;

	private LocalTime coursestart;

	private LocalTime courseend;

	private Integer credit;

	private String message;
	
	private List<Course> courseList;
	
	public CourseResponse() {
	}

	public CourseResponse(List<Course> courseList) {
		this.courseList = courseList;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}

}
