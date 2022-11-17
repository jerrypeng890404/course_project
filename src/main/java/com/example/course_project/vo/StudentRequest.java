package com.example.course_project.vo;

import java.time.LocalTime;
import java.util.List;

import com.example.course_project.entity.Course;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StudentRequest {

	//在外部輸入的請求
	@JsonProperty("id")
	private String id;

	@JsonProperty("student_id")
	private String studentid;
	
	@JsonProperty("studentId")
	private String studentId;

	@JsonProperty("student_name")
	private String studentname;

	@JsonProperty("course_code")
	private String coursecode;

	@JsonProperty("course_name")
	private String coursename;

	@JsonProperty("course_day")
	private String courseday;

	@JsonProperty("course_start")
	private LocalTime coursestart;

	@JsonProperty("course_end")
	private LocalTime courseend;

	@JsonProperty("credit")
	private Integer credit;
	
	private List<Course> courseList;
	
	public StudentRequest() {
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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}
	
}
