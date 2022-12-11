package com.example.course_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_project.ifs.CourseService;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseRequest;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentRequest;
import com.example.course_project.vo.StudentResponse;

@CrossOrigin
@RestController
//接收請求與給予回應
public class CourseController {

	// 連接Service方法
	@Autowired
	private CourseService courseService;
	
	@PostMapping(value = "createCourse")
	public CourseResponse createCourse(@RequestBody CourseRequest req) {
		return courseService.createCourse(req);
	}

	@PostMapping(value = "reviseCourse")
	public CourseResponse reviseCourse(@RequestBody CourseRequest req) {
		return courseService.reviseCourse(req);
	}

	@PostMapping(value = "deleteCourseByCode")
	public CourseResponse deleteById(@RequestBody CourseRequest req) {
		return courseService.deleteCourseByCode(req.getCourseCode());
	}

	@PostMapping(value = "findCourseByCode")
	public AddCourseResponse findCourse(@RequestBody AddCourseRequest req) {
		return courseService.findCourseByCode(req.getCourseCode());
	}

	@PostMapping(value = "findCourseByName")
	public AddCourseResponse findCourseByName(@RequestBody AddCourseRequest req) {
		return courseService.findCourseByName(req.getCourseName());
	}

	@PostMapping(value = "addStudentCourse")
	public AddCourseResponse addCourse(@RequestBody AddCourseRequest req) {
		return courseService.addStudentCourse(req);
	}

	@PostMapping(value = "dropStudentCourse")
	public AddCourseResponse dropCourse(@RequestBody AddCourseRequest req) {
		return courseService.dropStudentCourse(req.getStudentId(), req.getCourseCode());
	}

	@PostMapping(value = "findStudentCourse")
	public StudentResponse findStudentCourse(@RequestBody StudentRequest req) {
		return courseService.findStudentCourse(req.getStudentId());
	}

}
