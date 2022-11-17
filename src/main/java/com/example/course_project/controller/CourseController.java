package com.example.course_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.course_project.Interface.CourseService;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseRequest;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentRequest;
import com.example.course_project.vo.StudentResponse;

//接收請求與給予回應
@RestController
public class CourseController {

	// 到Service尋求方法用
	@Autowired
	private CourseService courseService;
	
	@PostMapping(value = "/api/createcourse")
	public CourseResponse createCourse(@RequestBody CourseRequest req) {
		// 新增容器裝透過方法輸入的參數
		return courseService.createCourse(req);
	}

	@PostMapping(value = "/api/revisecourse")
	public CourseResponse reviseCourse(@RequestBody CourseRequest req) {
		return courseService.reviseCourse(req);
	}

	@PostMapping(value = "/api/deletecourse")
	public CourseResponse deleteById(@RequestBody CourseRequest req) {
		return courseService.deleteCourseById(req.getCourseCode());
	}

	@PostMapping(value = "/api/findcoursebycode")
	public AddCourseResponse findCourse(@RequestBody AddCourseRequest req) {
		return courseService.findCourseByCode(req.getCourseCode());
	}

	@PostMapping(value = "/api/findcoursebyname")
	public AddCourseResponse findCourseByName(@RequestBody AddCourseRequest req) {
		return courseService.findCourseByName(req.getCourseName());
	}

	@PostMapping(value = "/api/addcourse")
	public AddCourseResponse addCourse(@RequestBody AddCourseRequest req) {
		return courseService.addCourse(req);
	}

	@PostMapping(value = "/api/dropcourse")
	public AddCourseResponse dropCourse(@RequestBody AddCourseRequest req) {
		return courseService.dropCourse(req.getStudentId(), req.getCourseCode());
	}

	@PostMapping(value = "/api/findstudentlesson")
	public StudentResponse findStudentLesson(@RequestBody StudentRequest req) {
		return courseService.findStudentLesson(req.getStudentId());
	}

}
