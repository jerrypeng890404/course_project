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
	private String studentId;
	
//	@JsonProperty("studentId")
//	private String studentId;

	@JsonProperty("student_name")
	private String studentName;

	@JsonProperty("course_code")
	private String courseCode;

	@JsonProperty("course_name")
	private String courseName;

	@JsonProperty("course_day")
	private String courseDay;

	@JsonProperty("course_start")
	private LocalTime courseStart;

	@JsonProperty("course_end")
	private LocalTime courseEnd;

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

	public String getCourseDay() {
		return courseDay;
	}

	public void setCourseDay(String courseDay) {
		this.courseDay = courseDay;
	}

	public LocalTime getCourseStart() {
		return courseStart;
	}

	public void setCourseStart(LocalTime courseStart) {
		this.courseStart = courseStart;
	}

	public LocalTime getCourseEnd() {
		return courseEnd;
	}

	public void setCourseEnd(LocalTime courseEnd) {
		this.courseEnd = courseEnd;
	}

	public Integer getCredit() {
		return credit;
	}

	public void setCredit(Integer credit) {
		this.credit = credit;
	}

	public List<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}
	
}
