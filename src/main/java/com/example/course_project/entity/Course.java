package com.example.course_project.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {

	@Id
	@Column(name = "coursecode")
	private String coursecode;

	@Column(name = "coursename")
	private String coursename;

	@Column(name = "courseday")
	private String courseday;

	@Column(name = "coursestart")
	private LocalTime coursestart; //LocalTime�榡 Ex:09:00

	@Column(name = "courseend")
	private LocalTime courseend;
	
	@Column(name = "credit")
	private int credit;

	public Course() {
	}

	public Course(String coursecode, String coursename, String courseday, LocalTime coursestart, LocalTime courseend, int credit) {
		this.coursecode = coursecode;
		this.coursename = coursename;
		this.courseday = courseday;
		this.coursestart = coursestart;
		this.courseend = courseend;
		this.credit = credit;
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

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

}
