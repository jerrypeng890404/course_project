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

	// 到Service尋求方法
	@Autowired
	private CourseService courseService;

	// ===============================================================
	@PostMapping(value = "/api/createcourse")
	public CourseResponse createCourse(@RequestBody CourseRequest req) {
		// 新增容器裝透過方法輸入的參數
		return courseService.createCourse(req.getCoursecode(), req.getCoursename(), req.getCourseday(),
				req.getCoursestart(), req.getCourseend(), req.getCredit());
	}

	// ===============================================================
	@PostMapping(value = "/api/revisecourse")
	public CourseResponse reviseCourse(@RequestBody CourseRequest req) {
		return courseService.reviseCourse(req.getCoursecode(), req.getCoursename(), req.getCourseday(),
				req.getCoursestart(), req.getCourseend(), req.getCredit());
	}

	// ===============================================================
	@PostMapping(value = "/api/deletecourse")
	public CourseResponse deleteById(@RequestBody CourseRequest req) {
		return courseService.deleteCourseById(req.getCoursecode());
	}

	// ===============================================================
	@PostMapping(value = "/api/findcoursebycode")
	public AddCourseResponse findCourse(@RequestBody AddCourseRequest req) {
		return courseService.findCourseByCode(req.getCoursecode());
	}

	// ===============================================================
	@PostMapping(value = "/api/findcoursebyname")
	public AddCourseResponse findCourseByName(@RequestBody AddCourseRequest req) {
		return courseService.findCourseByName(req.getCoursename());
	}
	
	// ===============================================================

//	@PostMapping(value = "/api/addstudent")
//	public StudentResponse addStudent(@RequestBody StudentRequest req) {
//		// 取得輸入參數容器
//		StudentCourse studentCourse = courseService.addStudent(req.getId(), req.getStudentid(), req.getStudentname());
//		// 新增回應容器
//		StudentResponse res = new StudentResponse();
//		// 防呆(待改)
//		if (studentCourse == null) {
//			res.setMessage("請輸入學生資料!!");
//			return res;
//		}
//		// 取得"輸入學生資訊參數"設為"回應學生資訊"
//		res.setId(studentCourse.getId());
//		res.setStudentid(studentCourse.getStudentId());
//		res.setStudentname(studentCourse.getStudentName());
//		res.setMessage("學生資料新增成功!!");
//		// 回傳回應
//		return res;
//	}

	// ===============================================================
	@PostMapping(value = "/api/addcourse")
	public AddCourseResponse addCourse(@RequestBody AddCourseRequest req) {
		return courseService.addCourse(req.getId(), req.getStudentid(), req.getStudentname(), req.getCoursecode());
	}

	// ===============================================================
	@PostMapping(value = "/api/dropcourse")
	public AddCourseResponse dropCourse(@RequestBody AddCourseRequest req) {
		return courseService.dropCourse(req.getStudentid(), req.getCoursecode());
	}

	// ===============================================================
	@PostMapping(value = "/api/findstudentlesson")
	public StudentResponse findStudentLesson(@RequestBody StudentRequest req) {
		return courseService.findStudentLesson(req.getStudentId());
	}

}
