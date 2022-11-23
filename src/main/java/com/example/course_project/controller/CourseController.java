package com.example.course_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_project.Ifs.CourseService;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseRequest;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentRequest;
import com.example.course_project.vo.StudentResponse;

@CrossOrigin
//接收請求與給予回應
@RestController
public class CourseController {

	// 連接Service方法
	@Autowired
	private CourseService courseService;
	
	@PostMapping(value = "/api/createcourse")
	public CourseResponse createCourse(@RequestBody CourseRequest req) {
		return courseService.createCourse(req);
	}

	@PostMapping(value = "/api/revisecourse")
	public CourseResponse reviseCourse(@RequestBody CourseRequest req) {
		return courseService.reviseCourse(req);
	}

	@PostMapping(value = "/api/deletecoursebycode")
	public CourseResponse deleteById(@RequestBody CourseRequest req) {
		return courseService.deleteCourseByCode(req.getCourseCode());
	}

	@PostMapping(value = "/api/findcoursebycode")
	public AddCourseResponse findCourse(@RequestBody AddCourseRequest req) {
		return courseService.findCourseByCode(req.getCourseCode());
	}

	@PostMapping(value = "/api/findcoursebyname")
	public AddCourseResponse findCourseByName(@RequestBody AddCourseRequest req) {
		return courseService.findCourseByName(req.getCourseName());
	}

	@PostMapping(value = "/api/addstudentcourse")
	public AddCourseResponse addCourse(@RequestBody AddCourseRequest req) {
		return courseService.addStudentCourse(req);
	}

	@PostMapping(value = "/api/dropstudentcourse")
	public AddCourseResponse dropCourse(@RequestBody AddCourseRequest req) {
		return courseService.dropStudentCourse(req.getStudentId(), req.getCourseCode());
	}

	@PostMapping(value = "/api/findstudentcourse")
	public StudentResponse findStudentCourse(@RequestBody StudentRequest req) {
		return courseService.findStudentCourse(req.getStudentId());
	}

}
