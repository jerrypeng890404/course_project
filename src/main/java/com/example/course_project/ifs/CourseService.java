package com.example.course_project.ifs;

import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseRequest;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;

public interface CourseService {

	//�s�W�ҵ{
	public CourseResponse createCourse(CourseRequest req);

	//�ק�ҵ{
	public CourseResponse reviseCourse(CourseRequest req);

	//�R���ҵ{
	public CourseResponse deleteCourseByCode(String courseCode);

	//�z�L�ҵ{�N�X���ҵ{
	public AddCourseResponse findCourseByCode(String courseCode);

	//�z�L�ҵ{�W�٧��ҵ{
	public AddCourseResponse findCourseByName(String courseName);

	//�ǥͿ��
	public AddCourseResponse addStudentCourse(AddCourseRequest req);

	//�ǥͰh��
	public AddCourseResponse dropStudentCourse(String studentId, String courseCode);

	//�z�L�ǥ;Ǹ����ǥͩҿ�ҵ{
	public StudentResponse findStudentCourse(String studentId);

}
