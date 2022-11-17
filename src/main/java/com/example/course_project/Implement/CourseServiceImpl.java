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

	// 新增課程
	@Override
	public CourseResponse createCourse(CourseRequest req) {
		// 新增回應回傳容器
		CourseResponse res = new CourseResponse();

		// 抽出防呆方法
		res = checkCourseInput(req, res);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// 暫存以主鍵找到的欄位資訊
		List<Course> courseOp = courseDao.findByCourseCode(req.getCourseCode());
		// 遍歷資料庫中找到的欄位資訊並新增容器"courselist"放入
		for (Course courselist : courseOp) {
			// 防呆:課程代碼不可相同 如果輸入代碼等於資料庫中代碼時進入 //在新增容器"courselist"中的欄位資訊裡取課程代碼
			if (req.getCourseCode().equalsIgnoreCase(courselist.getCourseCode())) {
				res.setMessage(CourseRtnCode.COURSECODE_REPEAT.getMessage());
				return res;
			}
		}

		// 新增entity回傳容器(Course)
		Course course = new Course(req);
		// 存入資料庫
		courseDao.save(course);
		
		// 設定回傳欄位資訊
		res.setCourseCode(course.getCourseCode());
		res.setCourseName(course.getCourseName());
		res.setCourseDay(course.getCourseDay());
		res.setCourseStart(course.getCourseStart());
		res.setCourseEnd(course.getCourseEnd());
		res.setCredit(course.getCredit());
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());

		// 回傳回應容器
		return res;
	}

	private CourseResponse checkCourseInput(CourseRequest req, CourseResponse res) {
		// 防呆:輸入請求不得為空
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
		// 限制使用者輸入星期
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
	// 修改課程
	@Override
	public CourseResponse reviseCourse(CourseRequest req) {
		// 新增回應回傳容器
		CourseResponse res = new CourseResponse();
		// 抽出防呆方法
		res = checkCourseInput(req, res);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// 新增容器裝入透過主鍵找到的欄位資訊
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		// 判斷容器中資料存在時進入
		if (courseOp.isPresent()) {
			// 取得新增容器中欄位資訊
			Course course = courseOp.get();
			// 將容器中原有欄位資訊覆蓋為輸入資訊
			course.setCourseCode(req.getCourseCode());
			course.setCourseName(req.getCourseName());
			course.setCourseDay(req.getCourseDay());
			course.setCourseStart(req.getCourseStart());
			course.setCourseEnd(req.getCourseEnd());
			course.setCredit(req.getCredit());
			// 儲存覆蓋資訊
			courseDao.save(course);
			// 設定更改資訊
			res.setCourseCode(req.getCourseCode());
			res.setCourseName(req.getCourseName());
			res.setCourseDay(req.getCourseDay());
			res.setCourseStart(req.getCourseStart());
			res.setCourseEnd(req.getCourseEnd());
			res.setCredit(req.getCredit());
			res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
			// 回傳更改資訊
			return res;
		}
		// 不存在時的錯誤訊息
		res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 刪除課程
	@Override
	public CourseResponse deleteCourseById(String courseCode) {
		// 新增回應回傳容器
		CourseResponse res = new CourseResponse();
		// 防呆:輸入請求不得為空
		if (!StringUtils.hasText(courseCode)) {
			res.setMessage(CourseRtnCode.COURSECODE_REQUIRED.getMessage());
			return res;
		}
		// 透過課程代碼找到課程放入暫存容器
		Optional<Course> courseOp = courseDao.findById(courseCode);
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		// 如果此容器中有資料 //StringUtils.hasText --> 如果值為null或空字串回傳false
		if (courseOp.isPresent() || !StringUtils.hasText(courseCode)) {
			// 刪除
			courseDao.deleteById(courseCode);
		}
		// 設定刪除成功訊息
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 透過課程代碼找課程
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
		// 如果課程欄位資訊不存在
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		// 新增拿來裝查詢到課程的容器
		Course course = courseOp.get();
		// 設定請求的課程資訊
		res.setCourseCode(course.getCourseCode());
		res.setCourseName(course.getCourseName());
		res.setCourseDay(course.getCourseDay());
		res.setCourseStart(course.getCourseStart());
		res.setCourseEnd(course.getCourseEnd());
		res.setCredit(course.getCredit());
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 透過課程名稱找課程
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
		List<Course> coursenamelist = courseDao.findAllByCourseName(courseName);
		// 課程資訊不存在的話
		if (coursenamelist.isEmpty()) {
			res.setMessage(CourseRtnCode.COURSENAME_IS_EMPTY.getMessage());
			return res;
		}

		// 設定所有符合課程名稱的課程資訊
		res.setCourseList(coursenamelist);
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 學生選課
	@Override
	public AddCourseResponse addCourse(AddCourseRequest req) {
		AddCourseResponse res = new AddCourseResponse();

		res = checkCourseInput(req, res);
		if (StringUtils.hasText(res.getMessage())) {
			return res;
		}

		// 輸入主鍵找到其實體資訊
		Optional<Course> courseOp = courseDao.findById(req.getCourseCode());
		// 如果課程代碼不存在
		if (!courseOp.isPresent()) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}
		// 拿來裝已經選好的課程
		Course course = courseOp.get();
		// 新增容器裝入已選課程學分
		int totalCredit = course.getCredit();
		// 判斷有無輸入課程代碼
		if (!StringUtils.hasText(req.getCourseCode())) {
			res.setMessage(CourseRtnCode.COURSECODE_IS_EMPTY.getMessage());
			return res;
		}

		// 透過學生學號找資料庫中相對的所有欄位資料
		List<StudentCourse> studentCourseList = studentCourseDao.findAllByStudentId(req.getStudentId());
		// 拿來裝正在選的課程 遍歷正在選的課程
		for (StudentCourse studentCourse : studentCourseList) {
			// 每次選課後學分存入已選課程學分容器
			totalCredit += studentCourse.getCredit();

			res = checkStudentCourseInfo(req, res, studentCourse, course, totalCredit);
			if (StringUtils.hasText(res.getMessage())) {
				return res;
			}
		}

		StudentCourse studentCourseEntity = new StudentCourse(req.getId(), req.getStudentId(), req.getStudentName(),
				course.getCourseCode(), course.getCourseName(), course.getCourseDay(), course.getCourseStart(),
				course.getCourseEnd(), course.getCredit());

		// 存入資料庫
		studentCourseDao.save(studentCourseEntity);
		// 設定回傳資料及訊息
		res.setStudentId(req.getStudentId());
		res.setCourseCode(studentCourseEntity.getCourseCode());
		res.setMessage(CourseRtnCode.SUCCESSFUL.getMessage());
		// 回傳大括號內所有動作過的容器
		return res;

	}

	private AddCourseResponse checkCourseInput(AddCourseRequest req, AddCourseResponse res) {
		// 防呆:輸入請求不得為空
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
		// 判斷"選好的課程"和"正在選的課程"中的id是否重複
		if (req.getId().equalsIgnoreCase(studentCourse.getId())) {
			res.setMessage(CourseRtnCode.ID_REPEAT.getMessage());
			return res;
		}
		// 判斷輸入學生學號是否相同(可以相同)
		if (studentCourse.getStudentId().equals(req.getStudentId())) {
			// 判斷輸入學生姓名是否相同
			if (!studentCourse.getStudentName().equals(req.getStudentName())) {
				// 學生學號相同但學生姓名不相同時進入
				res.setMessage(CourseRtnCode.SELECT_COURSE_ERROR.getMessage());
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
			res.setMessage(CourseRtnCode.SELECT_COURSE_ERROR.getMessage());
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
			if (time1 || time2 || time3 || time4) {
				res.setMessage(CourseRtnCode.SELECT_COURSE_ERROR.getMessage());
				return res;
			}
		}
		return res;
	}

	// ===================================================================================================
	// 學生退課
	@Override
	public AddCourseResponse dropCourse(String studentId, String courseCode) {
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
		// 如果資料庫沒有輸入學生學號時進入
		if (studentlist.isEmpty()) {
			res.setMessage(CourseRtnCode.STUDENTID_IS_EMPTY.getMessage());
			return res;
		}

		// 如果有的話遍歷多筆相同學生學號找到的課程資訊裝入studentInfo
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
	/// 透過學生學號找學生所選課程
	@Override
	public StudentResponse findStudentLesson(String studentId) {
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
