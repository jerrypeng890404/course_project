package com.example.course_project.repository;


import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.example.course_project.entity.StudentCourse;

@Repository                                           //連接Entity
public interface StudentCourseDao extends JpaRepository<StudentCourse, String> {
	
	public List<StudentCourse> findByStudentId(String studentId);
	
	public List<StudentCourse> findAllByStudentId(String studentId);
	
	public List<StudentCourse> findCourseByCourseCode(String courseCode);
	
	//自定義刪除方法加上
	@Modifying
	@Transactional
	public Optional<StudentCourse> deleteByCourseCode(String courseCode);
	
}
