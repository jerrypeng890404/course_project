package com.example.course_project.Implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_project.Interface.CourseService;
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
		// �s�W�^���^�Ǯe��
		CourseResponse res = new CourseResponse();

		// ��X���b��k
		res = checkCourseInput(req, res);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// �Ȧs�H�D���쪺����T
		List<Course> courseOp = courseDao.findByCourseCode(req.getCourseCode());
		// �M����Ʈw����쪺����T�÷s�W�e��"courselist"��J
		for (Course courselist : courseOp) {
			// ���b:�ҵ{�N�X���i�ۦP �p�G��J�N�X�����Ʈw���N�X�ɶi�J //�b�s�W�e��"courselist"��������T�̨��ҵ{�N�X
			if (req.getCourseCode().equalsIgnoreCase(courselist.getCourseCode())) {
				res.setMessage(CourseRtnCode.COURSECODE_REPEAT.getMessage());
				return res;
			}
		}

		// �s�Wentity�^�Ǯe��(Course)
		Course course = new Course(req);
		// �s�J��Ʈw
		courseDao.save(course);
		
		// �]�w�^������T
		res.setCourseCode(course.getCourseCode());
		res.setCourseName(course.getCourseName());
		res.setCourseDay(course.getCourseDay());
		res.setCourseStart(course.getCourseStart());
		res.setCourseEnd(course.getCourseEnd());
		res.setCredit(course.getCredit());
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());

		// �^�Ǧ^���e��
		return res;
	}

	private CourseResponse checkCourseInput(CourseRequest req, CourseResponse res) {
		// ���b:��J�ШD���o����
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
		// ����ϥΪ̿�J�P��
		List<String> checkDay = new ArrayList<>();
		checkDay.add("MON");
		checkDay.add("TUE");
		checkDay.add("WED");
		checkDay.add("THU");
		checkDay.add("FRI");

		if (!checkDay.stream().anyMatch(day -> day.equalsIgnoreCase(req.getCourseDay()))) {
			res.setMessage(CourseRtnCode.COURSEDAY_ERROR.getMessage());
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
		// �s�W�^���^�Ǯe��
		CourseResponse res = new CourseResponse();
		// ��X���b��k
		res = checkCourseInput(req, res);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// �s�W�e���ˤJ�z�L�D���쪺����T
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		// �P�_�e������Ʀs�b�ɶi�J
		if (courseOp.isPresent()) {
			// ���o�s�W�e��������T
			Course course = courseOp.get();
			// �N�e�����즳����T�л\����J��T
			course.setCourseCode(req.getCourseCode());
			course.setCourseName(req.getCourseName());
			course.setCourseDay(req.getCourseDay());
			course.setCourseStart(req.getCourseStart());
			course.setCourseEnd(req.getCourseEnd());
			course.setCredit(req.getCredit());
			// �x�s�л\��T
			courseDao.save(course);
			// �]�w����T
			res.setCourseCode(req.getCourseCode());
			res.setCourseName(req.getCourseName());
			res.setCourseDay(req.getCourseDay());
			res.setCourseStart(req.getCourseStart());
			res.setCourseEnd(req.getCourseEnd());
			res.setCredit(req.getCredit());
			res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
			// �^�ǧ���T
			return res;
		}
		// ���s�b�ɪ����~�T��
		res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
		// �^��
		return res;
	}

	// ===================================================================================================
	// �R���ҵ{
	@Override
	public CourseResponse deleteCourseById(String courseCode) {
		// �s�W�^���^�Ǯe��
		CourseResponse res = new CourseResponse();
		// ���b:��J�ШD���o����
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}
		// �z�L�ҵ{�N�X���ҵ{��J�Ȧs�e��
		Optional<Course> courseOp = courseDao.findById(courseCode);
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		// �p�G���e��������� //StringUtils.hasText --> �p�G�Ȭ�null�ΪŦr��^��false
		if (courseOp.isPresent() || !StringUtils.hasText(courseCode)) {
			// �R��
			courseDao.deleteById(courseCode);
		}
		// �]�w�R�����\�T��
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// �^��
		return res;
	}

	// ===================================================================================================
	// �z�L�ҵ{�N�X��ҵ{
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
		// �p�G�ҵ{����T���s�b
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		// �s�W���Ӹˬd�ߨ�ҵ{���e��
		Course course = courseOp.get();
		// �]�w�ШD���ҵ{��T
		res.setCourseCode(course.getCourseCode());
		res.setCourseName(course.getCourseName());
		res.setCourseDay(course.getCourseDay());
		res.setCourseStart(course.getCourseStart());
		res.setCourseEnd(course.getCourseEnd());
		res.setCredit(course.getCredit());
		// �^��
		return res;
	}

	// ===================================================================================================
	// �z�L�ҵ{�W�٧�ҵ{
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
		List<Course> coursenamelist = courseDao.findAllByCourseName(courseName);
		// �ҵ{��T���s�b����
		if (coursenamelist.isEmpty()) {
			res.setMessage(CourseRtnCode.COURSENAME_IS_EMPTY.getMessage());
			return res;
		}

		// �]�w�Ҧ��ŦX�ҵ{�W�٪��ҵ{��T
		res.setCourseList(coursenamelist);
		// �^��
		return res;
	}

	// ===================================================================================================
	// �ǥͿ��
	@Override
	public AddCourseResponse addCourse(AddCourseRequest req) {
		AddCourseResponse res = new AddCourseResponse();

		res = checkCourseInput(req, res);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// ��J�D���������T
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		// �p�G�ҵ{�N�X���s�b
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		// ���Ӹˤw�g��n���ҵ{
		Course course = courseOp.get();
		// �s�W�e���ˤJ�w��ҵ{�Ǥ�
		int totalCredit = course.getCredit();
		// �P�_���L��J�ҵ{�N�X
		if (!StringUtils.hasText(req.getCourseCode())) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}

		// �z�L�ǥ;Ǹ����Ʈw���۹諸�Ҧ������
		List<StudentCourse> studentCourseList = studentCourseDao.findAllByStudentId(req.getStudentId());
		// ���Ӹ˥��b�諸�ҵ{ �M�����b�諸�ҵ{
		for (StudentCourse studentCourse : studentCourseList) {
			// �C����ҫ�Ǥ��s�J�w��ҵ{�Ǥ��e��
			totalCredit += studentCourse.getCredit();

			res = checkStudentCourseInfo(req, res, studentCourse, course, totalCredit);
			if (StringUtils.hasText(res.getMessage())) {
				return res;
			}
		}

		StudentCourse studentCourseEntity = new StudentCourse(req.getId(), req.getStudentId(), req.getStudentName(),
				course.getCourseCode(), course.getCourseName(), course.getCourseDay(), course.getCourseStart(),
				course.getCourseEnd(), course.getCredit());

		// �s�J��Ʈw
		studentCourseDao.save(studentCourseEntity);
		// �]�w�^�Ǹ�ƤΰT��
		res.setStudentId(req.getStudentId());
		res.setCourseCode(studentCourseEntity.getCourseCode());
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// �^�Ǥj�A�����Ҧ��ʧ@�L���e��
		return res;

	}

	private AddCourseResponse checkCourseInput(AddCourseRequest req, AddCourseResponse res) {
		// ���b:��J�ШD���o����
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

	private AddCourseResponse checkStudentCourseInfo(AddCourseRequest req, AddCourseResponse res,
			StudentCourse studentCourse, Course course, Integer totalCredit) {
		// �P�_"��n���ҵ{"�M"���b�諸�ҵ{"����id�O�_����
		if (req.getId().equalsIgnoreCase(studentCourse.getId())) {
			res.setMessage(CourseRtnCode.ID_REPEAT.getMessage());
			return res;
		}
		// �P�_��J�ǥ;Ǹ��O�_�ۦP(�i�H�ۦP)
		if (studentCourse.getStudentId().equals(req.getStudentId())) {
			// �P�_��J�ǥͩm�W�O�_�ۦP
			if (!studentCourse.getStudentName().equals(req.getStudentName())) {
				// �ǥ;Ǹ��ۦP���ǥͩm�W���ۦP�ɶi�J
				res.setMessage(CourseRtnCode.SELECT_COURSE_ERROR.getMessage());
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
			res.setMessage(CourseRtnCode.SELECT_COURSE_ERROR.getMessage());
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
			if (time1 || time2 || time3 || time4) {
				res.setMessage(CourseRtnCode.SELECT_COURSE_ERROR.getMessage());
				return res;
			}
		}
		return res;
	}

	// ===================================================================================================
	// �ǥͰh��
	@Override
	public AddCourseResponse dropCourse(String studentId, String courseCode) {
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
		// �p�G��Ʈw�S����J�ǥ;Ǹ��ɶi�J
		if (studentlist.isEmpty()) {
			res.setMessage(CourseRtnCode.STUDENTID_IS_EMPTY.getMessage());
			return res;
		}

		// �p�G�����ܹM���h���ۦP�ǥ;Ǹ���쪺�ҵ{��T�ˤJstudentInfo
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
	/// �z�L�ǥ;Ǹ���ǥͩҿ�ҵ{
	@Override
	public StudentResponse findStudentLesson(String studentId) {
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
