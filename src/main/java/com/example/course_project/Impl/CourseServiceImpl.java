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

	// 新增課程
	@Override
	public CourseResponse createCourse(CourseRequest req) {

		// 新增容器來接私有防呆方法(防止輸入值為空)
		CourseResponse res = checkCourseInput(req);
		// 出現錯誤訊息時進入
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// 判斷課程資料庫中課程代碼是否和輸入課程代碼重複
		if (courseDao.existsById(req.getCourseCode())) {
			res.setMessage(CourseRtnCode.COURSECODE_REPEAT.getMessage());
			return res;
		}

		// 新增回傳型態為Course的建構方法放入課程資訊參數
		Course course = new Course(req);
		// 存入資料庫
		courseDao.save(course);

		// 新增回傳型態為CourseResponse的建構方法容器放入輸入課程資訊
		CourseResponse courseRes = new CourseResponse(req);
		courseRes.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// 回傳回應容器
		return courseRes;
	}

	// 私有防呆方法(防止輸入值為空)
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

		// 限制使用者輸入星期幾
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
	// 修改課程
	@Override
	public CourseResponse reviseCourse(CourseRequest req) {
		
		// 新增回傳型態為CourseResponse的容器來接私有防呆方法(防止輸入值為空)
		CourseResponse res = checkCourseInput(req);
		// 如出現錯誤訊息時進入
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// 新增容器裝入透過主鍵找到的欄位資訊
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		// 判斷容器中資料存在時進入
		if (courseOp.isPresent()) {

			// 新增容器將透過主鍵找到的欄位資訊放入
			Course course = courseOp.get();

			// 輸入課程資訊覆蓋原有課程資訊
			course.setCourseName(req.getCourseName());
			course.setCourseDay(req.getCourseDay());
			course.setCourseStart(req.getCourseStart());
			course.setCourseEnd(req.getCourseEnd());
			course.setCredit(req.getCredit());
			// 儲存覆蓋資訊
			courseDao.save(course);

			// 設定更改課程訊息
			CourseResponse courseRes = new CourseResponse(req);
			courseRes.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
			// 回傳更改資訊
			return courseRes;
		}
		// 不存在時的錯誤訊息
		res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 刪除課程
	@Override
	public CourseResponse deleteCourseByCode(String courseCode) {
		// 新增回應回傳容器
		CourseResponse res = new CourseResponse();
		
		// 防呆:輸入請求不得為空
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}

		// 判斷在資料庫中輸入課程代碼是否存在
		if (courseDao.existsById(courseCode)) {
			// 存在時刪除
			courseDao.deleteById(courseCode);
		} else {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}

		// 設定刪除成功訊息
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 透過課程代碼找到課程
	@Override
	public AddCourseResponse findCourseByCode(String courseCode) {
		// 新增回應回傳容器
		AddCourseResponse res = new AddCourseResponse();
		
		// 防呆:輸入請求不得為空
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}

		// 透過課程代碼(PK)找相對課程資訊存入
		Optional<Course> courseOp = courseDao.findById(courseCode);
		
		// 課程欄位資訊不存在時進入
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		
		// 新增拿來裝查詢到課程的容器
		Course course = courseOp.get();
		// 設定請求的課程資訊
//		res.setCourseCode(course.getCourseCode());
//		res.setCourseName(course.getCourseName());
//		res.setCourseDay(course.getCourseDay());
//		res.setCourseStart(course.getCourseStart());
//		res.setCourseEnd(course.getCourseEnd());
//		res.setCredit(course.getCredit());
		res.setCourseInfo(course);
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 透過課程名稱找到課程
	@Override
	public AddCourseResponse findCourseByName(String courseName) {
		// 新增回應回傳資訊
		AddCourseResponse res = new AddCourseResponse();
		
		// 防呆:輸入請求不得為空
		if (!StringUtils.hasText(courseName)) {
			res.setMessage(CourseRtnCode.COURSENAME_REQUIRED.getMessage());
			return res;
		}

		// 透過課程名稱找到所有相同課程名稱欄位資訊
		List<Course> courseNameList = courseDao.findAllByCourseName(courseName);
		
		// 課程資訊不存在時進入
		if (courseNameList.isEmpty()) {
			res.setMessage(CourseRtnCode.COURSENAME_IS_EMPTY.getMessage());
			return res;
		}

		// 設定所有符合課程名稱的課程資訊
		res.setCourseList(courseNameList);
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 學生選課(包含新增學生資訊)
	@Override
	public AddCourseResponse addStudentCourse(AddCourseRequest req) {
		
		// 私有防呆方法(防止輸入值為空)
		AddCourseResponse res = checkCourseInput(req);
		// 如有錯誤訊息時進入
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// 透過主鍵找到其欄位資訊
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		
		// 如果欄位資訊不存在內容
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		
		// 拿來裝已經選好的課程
		Course course = courseOp.get();
		
		// 新增容器裝入已選課程學分
		int totalCredit = course.getCredit();

		// 透過學生學號找資料庫中相對的所有欄位資訊
		List<StudentCourse> studentCourseList = studentCourseDao.findAllByStudentId(req.getStudentId());
		
		// 遍歷已選的課程
		for (StudentCourse studentCourse : studentCourseList) {
			
			// 每次選課後學分存入已選課程學分容器
			totalCredit += studentCourse.getCredit();

			// 私有防呆方法(防止學生資訊錯誤)
			res = checkStudentCourseInfo(req, res, studentCourse, course, totalCredit);
			// 出現錯誤訊息時進入
			if (StringUtils.hasText(res.getMessage())) {
				return res;
			}
		}

		// 裝入所有新增資訊
		StudentCourse studentCourseEntity = new StudentCourse(req.getId(), req.getStudentId(), req.getStudentName(),
				course.getCourseCode(), course.getCourseName(), course.getCourseDay(), course.getCourseStart(),
				course.getCourseEnd(), course.getCredit());

		// 存入學生課程資料庫
		studentCourseDao.save(studentCourseEntity);

		// 設定回傳資料及訊息
		res.setStudentId(req.getStudentId());
		res.setCourseCode(studentCourseEntity.getCourseCode());
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// 回傳
		return res;
	}

	// 私有防呆方法(防止輸入值為空)
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

	// 私有防呆方法(防止學生資訊錯誤)
	private AddCourseResponse checkStudentCourseInfo(AddCourseRequest req, AddCourseResponse res,
			StudentCourse studentCourse, Course course, Integer totalCredit) {
		
		// 判斷"選好的課程"和"正在選的課程"中的id是否重複
		if (req.getId().equalsIgnoreCase(studentCourse.getId())) {
			res.setMessage(CourseRtnCode.ID_REPEAT.getMessage());
			return res;
		}
		
		// 判斷輸入學生學號是否相同(可以相同)
		if (studentCourse.getStudentId().equals(req.getStudentId())) {
			// 學生學號相同但學生姓名不相同時進入
			if (!studentCourse.getStudentName().equals(req.getStudentName())) {
				res.setMessage(CourseRtnCode.SELECT_COURSE_FAILED.getMessage());
				return res;
			}
		}
		
		// 判斷"選好的課程"和"正在選的課程"中的課程代碼是否重複
		if (req.getCourseCode().equalsIgnoreCase(studentCourse.getCourseCode())) {
			// 不可選同一堂課程
			res.setMessage(CourseRtnCode.COURSECODE_REPEAT.getMessage());
			return res;
		}
		
		// 判斷"選好的課程"和"正在選的課程"中的課程名稱是否重複
		if (course.getCourseName().equalsIgnoreCase(studentCourse.getCourseName())) {
			// 不可選同名稱的課程
			res.setMessage(CourseRtnCode.COURSENAME_REPEAT.getMessage());
			return res;
		}
		
		// 判斷是否超過十學分
		if (totalCredit > 10) {
			res.setMessage(CourseRtnCode.SELECT_COURSE_FAILED.getMessage());
			return res;
		}
		
		// 判斷"選好的課程"和"正在選的課程"中的上課日是否重複再比較上課時間是否重疊
		if (course.getCourseDay().equals(studentCourse.getCourseDay())) {
			boolean time1 = course.getCourseStart().isAfter(studentCourse.getCourseStart())
					&& course.getCourseStart().isBefore(studentCourse.getCourseEnd());

			boolean time2 = course.getCourseEnd().isAfter(studentCourse.getCourseStart())
					&& course.getCourseEnd().isBefore(studentCourse.getCourseEnd());

			boolean time3 = course.getCourseStart().isBefore(studentCourse.getCourseStart())
					&& course.getCourseEnd().isAfter(studentCourse.getCourseEnd());

			boolean time4 = course.getCourseStart().equals(studentCourse.getCourseStart())
					&& course.getCourseEnd().equals(studentCourse.getCourseEnd());

			// 其一為true時進入
			if (time1 || time2 || time3 || time4) {
				res.setMessage(CourseRtnCode.SELECT_COURSE_FAILED.getMessage());
				return res;
			}
		}
		return res;
	}

	// ===================================================================================================
	// 學生退課
	@Override
	public AddCourseResponse dropStudentCourse(String studentId, String courseCode) {
		
		// 新增回應回傳容器
		AddCourseResponse res = new AddCourseResponse();

		// 防呆:輸入請求不得為空
		if (!StringUtils.hasText(studentId)) {
			res.setMessage(CourseRtnCode.STUDENTID_REQUIRED.getMessage());
			return res;
		}
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}

		// 透過學生學號找到多筆學生選課資訊存入list容器(學生不只選一堂課會找到多個相同學生學號所以用list)
		List<StudentCourse> studentlist = studentCourseDao.findByStudentId(studentId);
		
		// 如果資料庫為空時進入
		if (studentlist.isEmpty()) {
			res.setMessage(CourseRtnCode.STUDENTID_IS_EMPTY.getMessage());
			return res;
		}

		// 反之遍歷此學生所選的所有課程資訊
		for (StudentCourse studentInfo : studentlist) {

			// 如果此學生課程代碼符合輸入課程代碼
			if (studentInfo.getCourseCode().equals(courseCode)) {

				// 刪除課程
				studentCourseDao.deleteByCourseCode(courseCode);
				res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
				return res;
			}
			
			// 如果此學生課程代碼不符合輸入課程代碼
			res.setMessage(CourseRtnCode.DELETE_COURSE_FAILED.getMessage());
		}
		return res;
	}

	// ===================================================================================================
	// 透過學生學號找到學生所選課程
	@Override
	public StudentResponse findStudentCourse(String studentId) {
		
		// 新增回應回傳容器
		StudentResponse res = new StudentResponse();
		
		// 防呆:輸入請求不得為空
		if (!StringUtils.hasText(studentId)) {
			res.setMessage(CourseRtnCode.STUDENTID_REQUIRED.getMessage());
			return res;
		}

		// 將利用學生學號找到此學生選的所有課程存入此容器
		List<StudentCourse> studentList = studentCourseDao.findAllByStudentId(studentId);
		
		// 判斷此容器是否為空
		if (studentList.isEmpty()) {
			res.setMessage(CourseRtnCode.STUDENTID_IS_EMPTY.getMessage());
			return res;
		}
		
		// 有課程的話裝進list中
		res.setStudentlist(studentList);
		// 回傳list
		return res;
	}

}
