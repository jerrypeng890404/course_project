package com.example.course_project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.course_project.entity.Course;

@Repository                                   //³s±µEntity
public interface CourseDao extends JpaRepository<Course, String> {
	
	public List<Course> findByCourseCode(String courseCode);
	
	public List<Course> findAllByCourseName(String courseName);
	
}
