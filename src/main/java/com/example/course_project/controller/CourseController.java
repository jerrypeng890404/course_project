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

//�����ШD�P�����^��
@RestController
public class CourseController {

	// ��Service�M�D��k
	@Autowired
	private CourseService courseService;

	// ===============================================================
	@PostMapping(value = "/api/createcourse")
	public CourseResponse createCourse(@RequestBody CourseRequest req) {
		// �s�W�e���˳z�L��k��J���Ѽ�
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
//		// ���o��J�ѼƮe��
//		StudentCourse studentCourse = courseService.addStudent(req.getId(), req.getStudentid(), req.getStudentname());
//		// �s�W�^���e��
//		StudentResponse res = new StudentResponse();
//		// ���b(�ݧ�)
//		if (studentCourse == null) {
//			res.setMessage("�п�J�ǥ͸��!!");
//			return res;
//		}
//		// ���o"��J�ǥ͸�T�Ѽ�"�]��"�^���ǥ͸�T"
//		res.setId(studentCourse.getId());
//		res.setStudentid(studentCourse.getStudentId());
//		res.setStudentname(studentCourse.getStudentName());
//		res.setMessage("�ǥ͸�Ʒs�W���\!!");
//		// �^�Ǧ^��
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
