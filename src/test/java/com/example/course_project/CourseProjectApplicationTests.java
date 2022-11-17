//package com.example.course_project;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.example.course_project.Interface.CourseService;
//import com.example.course_project.entity.Course;
//import com.example.course_project.entity.StudentCourse;
//import com.example.course_project.repository.CourseDao;
//import com.example.course_project.repository.StudentCourseDao;
//
//@SpringBootTest
//class CourseProjectApplicationTests {
//
//	@Autowired
//	private CourseService courseService;
//	
//	@Autowired
//	private CourseDao courseDao;
//	
//	@Autowired
//	private StudentCourseDao studentCourseDao;
//	
//	@Test
//	public void createCourse() {
//		Course course = new Course();
//		course.setCourseCode("123");
//		course.setCourseDay("1");
//		course.setCourseName("AAA");
//		course.setCourseStart(null);
//		course.setCourseEnd(null);
//		course.setCredit(0);
//		
//		courseDao.save(course);
//	}
//	@Test
//	public void createStu() {
//		StudentCourse course = new StudentCourse();
//		course.setCourseCode("123");
//		course.setCourseDay("1");
//		course.setCourseName("AAA");
//		course.setCourseStart(null);
//		course.setCourseEnd(null);
//		course.setCredit(0);
//		course.setId("0");
//		course.setStudentId("0");
//		course.setStudentName("0");
//		
//		
//		studentCourseDao.save(course);
//	}
//}
