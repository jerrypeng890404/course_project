package com.example.course_project.Interface;

import java.time.LocalTime;

import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;

public interface CourseService {

	// ½Òµ{
	public CourseResponse createCourse(String coursecode, String coursename, String courseday, LocalTime coursestart,
			LocalTime courseend, Integer credit);

	public CourseResponse reviseCourse(String coursecode, String coursename, String courseday, LocalTime coursestart,
			LocalTime courseend, Integer credit);

	public CourseResponse deleteCourseById(String coursecode);

	public AddCourseResponse findCourseByCode(String coursecode);

	public AddCourseResponse findCourseByName(String coursename);
//===============================================================

	// ¾Ç¥Í
//	public StudentCourse addStudent(String id, String studentId, String studentName);

	public AddCourseResponse addCourse(String id, String studentid, String studentname, String coursecode);

	public AddCourseResponse dropCourse(String studentid, String coursecode);

	public StudentResponse findStudentLesson(String studentId);

}
