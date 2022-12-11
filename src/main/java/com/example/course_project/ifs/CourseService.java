package com.example.course_project.ifs;

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
	public CourseResponse deleteCourseByCode(String courseCode);

	//透過課程代碼找到課程
	public AddCourseResponse findCourseByCode(String courseCode);

	//透過課程名稱找到課程
	public AddCourseResponse findCourseByName(String courseName);

	//學生選課
	public AddCourseResponse addStudentCourse(AddCourseRequest req);

	//學生退課
	public AddCourseResponse dropStudentCourse(String studentId, String courseCode);

	//透過學生學號找到學生所選課程
	public StudentResponse findStudentCourse(String studentId);

}
