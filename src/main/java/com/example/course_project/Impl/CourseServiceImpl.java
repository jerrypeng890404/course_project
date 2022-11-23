package com.example.course_project.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_project.Ifs.CourseService;
import com.example.course_project.constants.CourseRtnCode;
import com.example.course_project.entity.Course;
import com.example.course_project.entity.StudentCourse;
import com.example.course_project.repository.CourseDao;
import com.example.course_project.repository.StudentCourseDao;
import com.example.course_project.vo.AddCourseRequest;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseRequest;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentCourseDao studentCourseDao;

	// �s�W�ҵ{
	@Override
	public CourseResponse createCourse(CourseRequest req) {

		// �s�W�e���ӱ��p�����b��k(�����J�Ȭ���)
		CourseResponse res = checkCourseInput(req);
		// �X�{���~�T���ɶi�J
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// �P�_�ҵ{��Ʈw���ҵ{�N�X�O�_�M��J�ҵ{�N�X����
		if (courseDao.existsById(req.getCourseCode())) {
			res.setMessage(CourseRtnCode.COURSECODE_REPEAT.getMessage());
			return res;
		}

		// �s�W�^�ǫ��A��Course���غc��k��J�ҵ{��T�Ѽ�
		Course course = new Course(req);
		// �s�J��Ʈw
		courseDao.save(course);

		// �s�W�^�ǫ��A��CourseResponse���غc��k�e����J��J�ҵ{��T
		CourseResponse courseRes = new CourseResponse(req);
		courseRes.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// �^�Ǧ^���e��
		return courseRes;
	}

	// �p�����b��k(�����J�Ȭ���)
	private CourseResponse checkCourseInput(CourseRequest req) {
		CourseResponse res = new CourseResponse();

		if (!StringUtils.hasText(req.getCourseCode())) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getCourseName())) {
			res.setMessage(CourseRtnCode.COURSENAME_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getCourseDay())) {
			res.setMessage(CourseRtnCode.COURSEDAY_REQUIRED.getMessage());
			return res;
		}

		// ����ϥΪ̿�J�P���X
		List<String> checkDay = new ArrayList<>();
		checkDay.add("MON");
		checkDay.add("TUE");
		checkDay.add("WED");
		checkDay.add("THU");
		checkDay.add("FRI");

		if (!checkDay.stream().anyMatch(day -> day.equalsIgnoreCase(req.getCourseDay()))) {
			res.setMessage(CourseRtnCode.COURSEDAY_FAILED.getMessage());
			return res;
		}
		if (req.getCourseStart() == null) {
			res.setMessage(CourseRtnCode.COURSESTARTTIME_REQUIRED.getMessage());
			return res;
		}
		if (req.getCourseEnd() == null) {
			res.setMessage(CourseRtnCode.COURSEENDTIME_REQUIRED.getMessage());
			return res;
		}
		if (req.getCredit() == null) {
			res.setMessage(CourseRtnCode.CREDIT_REQUIRED.getMessage());
			return res;
		}
		return res;
	}

	// ===================================================================================================
	// �ק�ҵ{
	@Override
	public CourseResponse reviseCourse(CourseRequest req) {
		
		// �s�W�^�ǫ��A��CourseResponse���e���ӱ��p�����b��k(�����J�Ȭ���)
		CourseResponse res = checkCourseInput(req);
		// �p�X�{���~�T���ɶi�J
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// �s�W�e���ˤJ�z�L�D���쪺����T
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		// �P�_�e������Ʀs�b�ɶi�J
		if (courseOp.isPresent()) {

			// �s�W�e���N�z�L�D���쪺����T��J
			Course course = courseOp.get();

			// ��J�ҵ{��T�л\�즳�ҵ{��T
			course.setCourseName(req.getCourseName());
			course.setCourseDay(req.getCourseDay());
			course.setCourseStart(req.getCourseStart());
			course.setCourseEnd(req.getCourseEnd());
			course.setCredit(req.getCredit());
			// �x�s�л\��T
			courseDao.save(course);

			// �]�w���ҵ{�T��
			CourseResponse courseRes = new CourseResponse(req);
			courseRes.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
			// �^�ǧ���T
			return courseRes;
		}
		// ���s�b�ɪ����~�T��
		res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
		// �^��
		return res;
	}

	// ===================================================================================================
	// �R���ҵ{
	@Override
	public CourseResponse deleteCourseByCode(String courseCode) {
		// �s�W�^���^�Ǯe��
		CourseResponse res = new CourseResponse();
		
		// ���b:��J�ШD���o����
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}

		// �P�_�b��Ʈw����J�ҵ{�N�X�O�_�s�b
		if (courseDao.existsById(courseCode)) {
			// �s�b�ɧR��
			courseDao.deleteById(courseCode);
		} else {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}

		// �]�w�R�����\�T��
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// �^��
		return res;
	}

	// ===================================================================================================
	// �z�L�ҵ{�N�X���ҵ{
	@Override
	public AddCourseResponse findCourseByCode(String courseCode) {
		// �s�W�^���^�Ǯe��
		AddCourseResponse res = new AddCourseResponse();
		
		// ���b:��J�ШD���o����
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}

		// �z�L�ҵ{�N�X(PK)��۹�ҵ{��T�s�J
		Optional<Course> courseOp = courseDao.findById(courseCode);
		
		// �ҵ{����T���s�b�ɶi�J
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		
		// �s�W���Ӹˬd�ߨ�ҵ{���e��
		Course course = courseOp.get();
		// �]�w�ШD���ҵ{��T
//		res.setCourseCode(course.getCourseCode());
//		res.setCourseName(course.getCourseName());
//		res.setCourseDay(course.getCourseDay());
//		res.setCourseStart(course.getCourseStart());
//		res.setCourseEnd(course.getCourseEnd());
//		res.setCredit(course.getCredit());
		res.setCourseInfo(course);
		// �^��
		return res;
	}

	// ===================================================================================================
	// �z�L�ҵ{�W�٧��ҵ{
	@Override
	public AddCourseResponse findCourseByName(String courseName) {
		// �s�W�^���^�Ǹ�T
		AddCourseResponse res = new AddCourseResponse();
		
		// ���b:��J�ШD���o����
		if (!StringUtils.hasText(courseName)) {
			res.setMessage(CourseRtnCode.COURSENAME_REQUIRED.getMessage());
			return res;
		}

		// �z�L�ҵ{�W�٧��Ҧ��ۦP�ҵ{�W������T
		List<Course> courseNameList = courseDao.findAllByCourseName(courseName);
		
		// �ҵ{��T���s�b�ɶi�J
		if (courseNameList.isEmpty()) {
			res.setMessage(CourseRtnCode.COURSENAME_IS_EMPTY.getMessage());
			return res;
		}

		// �]�w�Ҧ��ŦX�ҵ{�W�٪��ҵ{��T
		res.setCourseList(courseNameList);
		// �^��
		return res;
	}

	// ===================================================================================================
	// �ǥͿ��(�]�t�s�W�ǥ͸�T)
	@Override
	public AddCourseResponse addStudentCourse(AddCourseRequest req) {
		
		// �p�����b��k(�����J�Ȭ���)
		AddCourseResponse res = checkCourseInput(req);
		// �p�����~�T���ɶi�J
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// �z�L�D���������T
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		
		// �p�G����T���s�b���e
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		
		// ���Ӹˤw�g��n���ҵ{
		Course course = courseOp.get();
		
		// �s�W�e���ˤJ�w��ҵ{�Ǥ�
		int totalCredit = course.getCredit();

		// �z�L�ǥ;Ǹ����Ʈw���۹諸�Ҧ�����T
		List<StudentCourse> studentCourseList = studentCourseDao.findAllByStudentId(req.getStudentId());
		
		// �M���w�諸�ҵ{
		for (StudentCourse studentCourse : studentCourseList) {
			
			// �C����ҫ�Ǥ��s�J�w��ҵ{�Ǥ��e��
			totalCredit += studentCourse.getCredit();

			// �p�����b��k(����ǥ͸�T���~)
			res = checkStudentCourseInfo(req, res, studentCourse, course, totalCredit);
			// �X�{���~�T���ɶi�J
			if (StringUtils.hasText(res.getMessage())) {
				return res;
			}
		}

		// �ˤJ�Ҧ��s�W��T
		StudentCourse studentCourseEntity = new StudentCourse(req.getId(), req.getStudentId(), req.getStudentName(),
				course.getCourseCode(), course.getCourseName(), course.getCourseDay(), course.getCourseStart(),
				course.getCourseEnd(), course.getCredit());

		// �s�J�ǥͽҵ{��Ʈw
		studentCourseDao.save(studentCourseEntity);

		// �]�w�^�Ǹ�ƤΰT��
		res.setStudentId(req.getStudentId());
		res.setCourseCode(studentCourseEntity.getCourseCode());
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// �^��
		return res;
	}

	// �p�����b��k(�����J�Ȭ���)
	private AddCourseResponse checkCourseInput(AddCourseRequest req) {
		AddCourseResponse res = new AddCourseResponse();

		if (!StringUtils.hasText(req.getId())) {
			res.setMessage(CourseRtnCode.ID_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getStudentId())) {
			res.setMessage(CourseRtnCode.STUDENTID_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getStudentName())) {
			res.setMessage(CourseRtnCode.STUDENTNAME_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(req.getCourseCode())) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}
		return res;
	}

	// �p�����b��k(����ǥ͸�T���~)
	private AddCourseResponse checkStudentCourseInfo(AddCourseRequest req, AddCourseResponse res,
			StudentCourse studentCourse, Course course, Integer totalCredit) {
		
		// �P�_"��n���ҵ{"�M"���b�諸�ҵ{"����id�O�_����
		if (req.getId().equalsIgnoreCase(studentCourse.getId())) {
			res.setMessage(CourseRtnCode.ID_REPEAT.getMessage());
			return res;
		}
		
		// �P�_��J�ǥ;Ǹ��O�_�ۦP(�i�H�ۦP)
		if (studentCourse.getStudentId().equals(req.getStudentId())) {
			// �ǥ;Ǹ��ۦP���ǥͩm�W���ۦP�ɶi�J
			if (!studentCourse.getStudentName().equals(req.getStudentName())) {
				res.setMessage(CourseRtnCode.SELECT_COURSE_FAILED.getMessage());
				return res;
			}
		}
		
		// �P�_"��n���ҵ{"�M"���b�諸�ҵ{"�����ҵ{�N�X�O�_����
		if (req.getCourseCode().equalsIgnoreCase(studentCourse.getCourseCode())) {
			// ���i��P�@��ҵ{
			res.setMessage(CourseRtnCode.COURSECODE_REPEAT.getMessage());
			return res;
		}
		
		// �P�_"��n���ҵ{"�M"���b�諸�ҵ{"�����ҵ{�W�٬O�_����
		if (course.getCourseName().equalsIgnoreCase(studentCourse.getCourseName())) {
			// ���i��P�W�٪��ҵ{
			res.setMessage(CourseRtnCode.COURSENAME_REPEAT.getMessage());
			return res;
		}
		
		// �P�_�O�_�W�L�Q�Ǥ�
		if (totalCredit > 10) {
			res.setMessage(CourseRtnCode.SELECT_COURSE_FAILED.getMessage());
			return res;
		}
		
		// �P�_"��n���ҵ{"�M"���b�諸�ҵ{"�����W�Ҥ�O�_���ƦA����W�Үɶ��O�_���|
		if (course.getCourseDay().equals(studentCourse.getCourseDay())) {
			boolean time1 = course.getCourseStart().isAfter(studentCourse.getCourseStart())
					&& course.getCourseStart().isBefore(studentCourse.getCourseEnd());

			boolean time2 = course.getCourseEnd().isAfter(studentCourse.getCourseStart())
					&& course.getCourseEnd().isBefore(studentCourse.getCourseEnd());

			boolean time3 = course.getCourseStart().isBefore(studentCourse.getCourseStart())
					&& course.getCourseEnd().isAfter(studentCourse.getCourseEnd());

			boolean time4 = course.getCourseStart().equals(studentCourse.getCourseStart())
					&& course.getCourseEnd().equals(studentCourse.getCourseEnd());

			// ��@��true�ɶi�J
			if (time1 || time2 || time3 || time4) {
				res.setMessage(CourseRtnCode.SELECT_COURSE_FAILED.getMessage());
				return res;
			}
		}
		return res;
	}

	// ===================================================================================================
	// �ǥͰh��
	@Override
	public AddCourseResponse dropStudentCourse(String studentId, String courseCode) {
		
		// �s�W�^���^�Ǯe��
		AddCourseResponse res = new AddCourseResponse();

		// ���b:��J�ШD���o����
		if (!StringUtils.hasText(studentId)) {
			res.setMessage(CourseRtnCode.STUDENTID_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}

		// �z�L�ǥ;Ǹ����h���ǥͿ�Ҹ�T�s�Jlist�e��(�ǥͤ��u��@��ҷ|���h�ӬۦP�ǥ;Ǹ��ҥH��list)
		List<StudentCourse> studentlist = studentCourseDao.findByStudentId(studentId);
		
		// �p�G��Ʈw���Ůɶi�J
		if (studentlist.isEmpty()) {
			res.setMessage(CourseRtnCode.STUDENTID_IS_EMPTY.getMessage());
			return res;
		}

		// �Ϥ��M�����ǥͩҿ諸�Ҧ��ҵ{��T
		for (StudentCourse studentInfo : studentlist) {

			// �p�G���ǥͽҵ{�N�X�ŦX��J�ҵ{�N�X
			if (studentInfo.getCourseCode().equals(courseCode)) {

				// �R���ҵ{
				studentCourseDao.deleteByCourseCode(courseCode);
				res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
				return res;
			}
			
			// �p�G���ǥͽҵ{�N�X���ŦX��J�ҵ{�N�X
			res.setMessage(CourseRtnCode.DELETE_COURSE_FAILED.getMessage());
		}
		return res;
	}

	// ===================================================================================================
	// �z�L�ǥ;Ǹ����ǥͩҿ�ҵ{
	@Override
	public StudentResponse findStudentCourse(String studentId) {
		
		// �s�W�^���^�Ǯe��
		StudentResponse res = new StudentResponse();
		
		// ���b:��J�ШD���o����
		if (!StringUtils.hasText(studentId)) {
			res.setMessage(CourseRtnCode.STUDENTID_REQUIRED.getMessage());
			return res;
		}

		// �N�Q�ξǥ;Ǹ���즹�ǥͿ諸�Ҧ��ҵ{�s�J���e��
		List<StudentCourse> studentList = studentCourseDao.findAllByStudentId(studentId);
		
		// �P�_���e���O�_����
		if (studentList.isEmpty()) {
			res.setMessage(CourseRtnCode.STUDENTID_IS_EMPTY.getMessage());
			return res;
		}
		
		// ���ҵ{���ܸ˶ilist��
		res.setStudentlist(studentList);
		// �^��list
		return res;
	}

}
