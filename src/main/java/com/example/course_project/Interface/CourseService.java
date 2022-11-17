package com.example.course_project.Interface;

import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseRequest;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;

public interface CourseService {

	//新增課程
	public CourseResponse createCourse(CourseRequest req);

	//修改課程
	public CourseResponse reviseCourse(CourseRequest req);

	//刪除課程
	public CourseResponse deleteCourseById(String courseCode);

	//透過課程代碼找課程
	public AddCourseResponse findCourseByCode(String courseCode);

	//透過課程名稱找課程
	public AddCourseResponse findCourseByName(String courseName);

	//學生選課
	public AddCourseResponse addCourse(AddCourseRequest req);

	//學生退課
	public AddCourseResponse dropCourse(String studentId, String courseCode);

	//透過學生學號找學生所選課程
	public StudentResponse findStudentLesson(String studentId);

}
