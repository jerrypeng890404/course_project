package com.example.course_project.Implement;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.course_project.Interface.CourseService;
import com.example.course_project.entity.Course;
import com.example.course_project.entity.StudentCourse;
import com.example.course_project.repository.CourseDao;
import com.example.course_project.repository.StudentCourseDao;
import com.example.course_project.vo.AddCourseResponse;
import com.example.course_project.vo.CourseResponse;
import com.example.course_project.vo.StudentResponse;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private StudentCourseDao studentCourseDao;

	// ===================================================================================================
	// �s�W�ҵ{
	@Override
	public CourseResponse createCourse(String coursecode, String coursename, String courseday, LocalTime coursestart,
			LocalTime courseend, Integer credit) {
		// �s�W�^���^�Ǯe��
		CourseResponse res = new CourseResponse();
		// �s�Wentity�^�Ǯe��
		Course course = new Course();
		// ���b:�ҵ{��T���o����
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("�п�J�ҵ{�N�X!!");
			return res;
		}
		if (!StringUtils.hasText(coursename)) {
			res.setMessage("�п�J�ҵ{�W��!!");
			return res;
		}
		if (!StringUtils.hasText(courseday)) {
			res.setMessage("�п�J�ҵ{��!!");
			return res;
		}
		// ����ϥΪ̿�J�P��
		List<String> checkDay = new ArrayList<>();
		checkDay.add("MON");
		checkDay.add("TUE");
		checkDay.add("WED");
		checkDay.add("THU");
		checkDay.add("FRI");
		// lambda��F��
		if (!checkDay.stream().anyMatch(day -> day.equalsIgnoreCase(courseday))) {
			res.setMessage("�п�J���T�ҵ{��!!");
			return res;
		}
		if (coursestart == null) {
			res.setMessage("�п�J�W�Үɶ�!!");
			return res;
		}
		if (courseend == null) {
			res.setMessage("�п�J�U�Үɶ�!!");
			return res;
		}
		if (credit == null) {
			res.setMessage("�п�J�Ǥ�!!");
			return res;
		}

		// �Ȧs�H�D���쪺����T
		List<Course> courseOp = courseDao.findByCourseCode(coursecode);
		// �M����Ʈw����쪺����T�÷s�W�e��"courselist"��J
		for (Course courselist : courseOp) {
			// ���b:�ҵ{�N�X���i�ۦP �p�G��J�N�X�����Ʈw���N�X�ɶi�J //�b�s�W�e��"courselist"��������T�̨��ҵ{�N�X
			if (coursecode.equalsIgnoreCase(courselist.getCourseCode())) {
				res.setMessage("�ҵ{�N�X����!!");
				return res;
			}
		}

		// �s�W�e���]�w���x�s�Ҧ�����T
		course.setCourseCode(coursecode);
		course.setCourseName(coursename);
		course.setCourseDay(courseday);
		course.setCourseStart(coursestart);
		course.setCourseEnd(courseend);
		course.setCredit(credit);
		// �s�J��Ʈw
		courseDao.save(course);
		// �]�w����T
		res.setCoursecode(course.getCourseCode());
		res.setCoursename(course.getCourseName());
		res.setCourseday(course.getCourseDay());
		res.setCoursestart(course.getCourseStart());
		res.setCourseend(course.getCourseEnd());
		res.setCredit(course.getCredit());
		res.setMessage("�ҵ{�s�W���\!!");

		// �^�Ǧ^���e��
		return res;
	}

	// ===================================================================================================
	// �ק�ҵ{
	@Override
	public CourseResponse reviseCourse(String coursecode, String coursename, String courseday, LocalTime coursestart,
			LocalTime courseend, Integer credit) {
		// �s�W�^���^�Ǯe��
		CourseResponse res = new CourseResponse();
		// ���b:�ҵ{��T���o����
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("�п�J�ҵ{�N�X!!");
			return res;
		}
		if (!StringUtils.hasText(coursename)) {
			res.setMessage("�п�J�ҵ{�W��!!");
			return res;
		}
		if (!StringUtils.hasText(courseday)) {
			res.setMessage("�п�J�ҵ{��!!");
			return res;
		}
		// ����ϥΪ̿�J�P��
		List<String> checkDay = new ArrayList<>();
		checkDay.add("MON");
		checkDay.add("TUE");
		checkDay.add("WED");
		checkDay.add("THU");
		checkDay.add("FRI");
		// lambda��F��
		if (!checkDay.stream().anyMatch(day -> day.equalsIgnoreCase(courseday))) {
			res.setMessage("�п�J���T�W�Ҥ�!!");
			return res;
		}
		if (coursestart == null) {
			res.setMessage("�п�J�W�Үɶ�!!");
			return res;
		}
		if (courseend == null) {
			res.setMessage("�п�J�U�Үɶ�!!");
			return res;
		}
		if (credit == null) {
			res.setMessage("�п�J�Ǥ�!!");
			return res;
		}

		// �s�W�e���ˤJ�z�L�D���쪺�Ҧ�����T
		Optional<Course> courseOp = courseDao.findById(coursecode);
		// �P�_�e������Ʀs���s�b
		// �s�b�ɶi�J
		if (courseOp.isPresent()) {
			// ���o�s�W�e��������T
			Course course = courseOp.get();
			// �N�e�����즳����T�л\����J��T
			course.setCourseCode(coursecode);
			course.setCourseName(coursename);
			course.setCourseDay(courseday);
			course.setCourseStart(coursestart);
			course.setCourseEnd(courseend);
			course.setCredit(credit);
			// �x�s�л\��T
			courseDao.save(course);
			// �]�w����T
			res.setCoursecode(coursecode);
			res.setCoursename(coursename);
			res.setCourseday(courseday);
			res.setCoursestart(coursestart);
			res.setCourseend(courseend);
			res.setCredit(credit);
			res.setMessage("�ҵ{��令�\!!");
			// �^�ǧ���T
			return res;
		}
		// ���s�b
		res.setMessage("�L���ҵ{�N�X!!");
		// �^�Ǯe����
		return res;
	}

	// ===================================================================================================
	// �R���ҵ{
	@Override
	public CourseResponse deleteCourseById(String coursecode) {
		// �s�W�^���^�Ǯe��
		CourseResponse res = new CourseResponse();
		// �P�_��J�ȬO�_����
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("�п�J���R���ҵ{�N�X!!");
			return res;
		}
		// �z�L�ҵ{�N�X���ҵ{��J�Ȧs�e��
		Optional<Course> courseOp = courseDao.findById(coursecode);
		if (!courseOp.isPresent()) {
			res.setMessage("���R���ҵ{���s�b!!");
			return res;
		}
		// �p�G���e��������� //StringUtils.hasText --> �p�G�Ȭ�null�ΪŦr��^��false
		if (courseOp.isPresent() || !StringUtils.hasText(coursecode)) {
			// �R��
			courseDao.deleteById(coursecode);
		}
		res.setMessage("�ҵ{" + coursecode + "�R�����\!!");
		// �^�Ǯe����
		return res;
	}

	// ===================================================================================================
	// ��J�ҵ{�N�X������ҵ{
	@Override
	public AddCourseResponse findCourseByCode(String coursecode) {
		// �s�W�^���^�Ǯe��
		AddCourseResponse res = new AddCourseResponse();
		// �P�_��J�Ȭ��Ů�
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("�п�J���d�߽ҵ{�N�X!!");
			return res;
		}
		// �z�L�ҵ{�N�X(PK)��۹�ҵ{��T�s�J
		Optional<Course> courseOp = courseDao.findById(coursecode);
		// �p�G�ҵ{����T���s�b
		if (!courseOp.isPresent()) {
			res.setMessage("�L���ҵ{�N�X!!");
			return res;
		}
		// �s�W���Ӹˬd�ߨ�ҵ{���e��
		Course course = courseOp.get();
		// �]�w�ШD���ҵ{��T
		res.setCoursecode(course.getCourseCode());
		res.setCoursename(course.getCourseName());
		res.setCourseday(course.getCourseDay());
		res.setCoursestart(course.getCourseStart());
		res.setCourseend(course.getCourseEnd());
		res.setCredit(course.getCredit());
		// �^��
		return res;
	}

	// ===================================================================================================
	// ��J�ҵ{�W�٧�����ҵ{
	@Override
	public AddCourseResponse findCourseByName(String coursename) {
		// �s�W�^���^�Ǹ�T
		AddCourseResponse res = new AddCourseResponse();
		// �p�G�S��J�ҵ{�W�ٮɶi�J
		if (!StringUtils.hasText(coursename)) {
			res.setMessage("�п�J���d�߽ҵ{�W��!!");
			return res;
		}
		// �z�L�ҵ{�W�٧��Ҧ��ۦP�ҵ{�W��
		List<Course> coursenamelist = courseDao.findAllByCourseName(coursename);
		// �ҵ{��T���s�b����
		if (coursenamelist.isEmpty()) {
			res.setMessage("�L���ҵ{�W��!!");
			return res;
		}

		// �]�w�Ҧ��ŦX�ҵ{�W�٪��ҵ{��T
		res.setCourseList(coursenamelist);
		// �^��
		return res;
	}

	// ===================================================================================================
//	// �s�W�ǥ�
//	@Override
//	public StudentCourse addStudent(String id, String studentId, String studentName) {
//		// �ͦ��e��
//		StudentCourse studentCourse = new StudentCourse();
//		// �z�Lid���ǥͩ�J�Ȧs�e��
//		Optional<StudentCourse> studentOp = studentCourseDao.findById(id);
//		// �p�G�����ǥ͸�� //StringUtils.hasText --> �p�G�Ȭ�null�ΪŦr��^��false
//		if (studentOp.isPresent() || !StringUtils.hasText(id)) {
//			// ??????�^�ǪŮe��??????
//			return new StudentCourse();
//		} else {
//			// �s�W�ǥ͸��
//			studentCourse.setId(id);
//			studentCourse.setStudentId(studentId);
//			studentCourse.setStudentName(studentName);
//			// �x�s�ǥ͸��
//			studentCourseDao.save(studentCourse);
//		}
//		// �^�Ǯe����
//		return studentCourse;
//	}

	// ===================================================================================================
	// �ǥͥ[��
	@Override
	public AddCourseResponse addCourse(String id, String studentid, String studentname, String coursecode) {
		StudentCourse studentCourseEntity = new StudentCourse();
		AddCourseResponse res = new AddCourseResponse();
		// �����J�ŭ�
		if (!StringUtils.hasText(id)) {
			res.setMessage("�п�J��s��!!");
			return res;
		}
		if (!StringUtils.hasText(studentid)) {
			res.setMessage("�п�J�ǥ;Ǹ�!!");
			return res;
		}
		if (!StringUtils.hasText(studentname)) {
			res.setMessage("�п�J�ǥͩm�W!!");
			return res;
		}
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("�п�J���[��ҵ{�N�X!!");
			return res;
		}

		// ��J�D���������T
		Optional<Course> courseOp = courseDao.findById(coursecode);
		// �p�G�ҵ{�N�X���s�b
		if (!courseOp.isPresent()) {
			res.setMessage("�L���ҵ{!!");
		}
		// �p�G�ҵ{�s�b
		// ���Ӹˤw�g��n���ҵ{
		Course course = courseOp.get();
		// �s�W�e���ˤJ�w��ҵ{�Ǥ�
		int totalCredit = course.getCredit();
		// �P�_���L��J�ҵ{�N�X
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("�L���ҵ{�N�X!!");
			return res;
		}

		// �z�L�ǥ;Ǹ����Ʈw���۹諸�Ҧ������
		List<StudentCourse> studentCourseList = studentCourseDao.findAllByStudentId(studentid);
		// ���Ӹ˥��b�諸�ҵ{ �M�����b�諸�ҵ{
		for (StudentCourse studentCourse : studentCourseList) {
			// �C����ҫ�Ǥ��s�J�w��ҵ{�Ǥ��e��
			totalCredit += studentCourse.getCredit();
			// �P�_"��n���ҵ{"�M"���b�諸�ҵ{"����id�O�_����
			if (id.equalsIgnoreCase(studentCourse.getId())) {
				res.setMessage("id����!!");
				return res;
			}
			// �P�_��J�ǥ;Ǹ��O�_�ۦP(�i�H�ۦP)
			if (studentCourse.getStudentId().equals(studentid)) {
				// �P�_��J�ǥͩm�W�O�_�ۦP
				if (!studentCourse.getStudentName().equals(studentname)) {
					// �ǥ;Ǹ��ۦP���ǥͩm�W���ۦP�ɶi�J
					res.setMessage("�ۦP�Ǹ����i�����P�m�W!!");
					return res;
				}
			}
			// �P�_"��n���ҵ{"�M"���b�諸�ҵ{"�����ҵ{�N�X�O�_����
			if (coursecode.equalsIgnoreCase(studentCourse.getCourseCode())) {
				// ���i��P�@��ҵ{
				res.setMessage("�ҵ{�N�X����!!");
				return res;
			}
			// �P�_"��n���ҵ{"�M"���b�諸�ҵ{"�����ҵ{�W�٬O�_����
			if (course.getCourseName().equalsIgnoreCase(studentCourse.getCourseName())) {
				// ���i��P�W�٪��ҵ{
				res.setMessage("�ҵ{�W�٭���!!");
				return res;
			}
			// �P�_�O�_�W�L�Q�Ǥ�
			if (totalCredit > 10) {
				res.setMessage("��ҤW����10�Ǥ�!!");
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
					res.setMessage("�P�{���ҵ{�ɶ����|!!");
					return res;
				}
			}
		}
		// �]�w��J��
		studentCourseEntity.setId(id);
		studentCourseEntity.setStudentId(studentid);
		studentCourseEntity.setStudentName(studentname);
		studentCourseEntity.setCourseCode(course.getCourseCode());
		studentCourseEntity.setCourseName(course.getCourseName());
		studentCourseEntity.setCourseDay(course.getCourseDay());
		studentCourseEntity.setCourseStart(course.getCourseStart());
		studentCourseEntity.setCourseEnd(course.getCourseEnd());
		studentCourseEntity.setCredit(course.getCredit());
		// �s�J��Ʈw
		studentCourseDao.save(studentCourseEntity);
		// �]�w�^�Ǹ�ƤΰT��
		res.setStudentid(studentid);
		res.setCoursecode(studentCourseEntity.getCourseCode());
		res.setMessage("�ҵ{�s�W����!!");
		// �^�Ǥj�A�����Ҧ��ʧ@�L���e��
		return res;

	}

	// ===================================================================================================
	// �ǥͰh��
	@Override
	public AddCourseResponse dropCourse(String studentid, String coursecode) {
		// �s�W�^���^�Ǯe��
		AddCourseResponse res = new AddCourseResponse();
		// �P�_��J�ȬO�_����
		if (!StringUtils.hasText(studentid)) {
			res.setMessage("�п�J���R���ǥ;Ǹ�!!");
			return res;
		}
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("�п�J���R���ҵ{�N�X!!");
			return res;
		}
		// �z�L�ǥ;Ǹ����h���ǥͿ�Ҹ�T�s�Jlist�e��(�ǥͤ��u��@��ҷ|���h�ӬۦP�ǥ;Ǹ��ҥH��list)
		List<StudentCourse> studentlist = studentCourseDao.findByStudentId(studentid);
		// �p�G��Ʈw�S����J�ǥ;Ǹ��ɶi�J
		if (studentlist.isEmpty()) {
			res.setMessage("���R���ǥͤ��s�b!!");
			return res;
		}

		// �p�G�����ܹM���h���ۦP�ǥ;Ǹ���쪺�ҵ{��T�ˤJstudentInfo
		for (StudentCourse studentInfo : studentlist) {
			// �p�G���ǥͽҵ{�N�X�ŦX��J�ҵ{�N�X
			if (studentInfo.getCourseCode().equals(coursecode)) {
				// �R���ҵ{
				studentCourseDao.deleteByCourseCode(coursecode);
				res.setMessage("�ҵ{�R�����\!!");
				return res;
			}
			// �p�G���ǥͽҵ{�N�X���ŦX��J�ҵ{�N�X
			res.setMessage("�ӾǥͨS����צ���!!");
		}
		return res;
	}

	// ===================================================================================================
	// �d�߾ǥͤw����ҵ{
	@Override
	public StudentResponse findStudentLesson(String studentId) {
		// �s�W�^���^�Ǯe��
		StudentResponse res = new StudentResponse();
		// �P�_��J���e�O�_����
		if (!StringUtils.hasText(studentId)) {
			res.setMessage("�п�J���d�߾ǥ;Ǹ�!!");
			return res;
		}
		// �N�Q�ξǥ;Ǹ���즹�ǥͿ諸�Ҧ��ҵ{�s�J���e��
		List<StudentCourse> studentList = studentCourseDao.findAllByStudentId(studentId);
		// �P�_���e���O�_����
		if (studentList.isEmpty()) {
			res.setMessage("�d�L���ǥ�!!");
			return res;
		}
		// ���ҵ{���ܸ˶ilist��
		res.setStudentlist(studentList);
		// �^��list
		return res;
	}

}
