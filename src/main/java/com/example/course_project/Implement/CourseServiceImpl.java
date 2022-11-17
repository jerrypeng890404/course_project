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
	// 新增課程
	@Override
	public CourseResponse createCourse(String coursecode, String coursename, String courseday, LocalTime coursestart,
			LocalTime courseend, Integer credit) {
		// 新增回應回傳容器
		CourseResponse res = new CourseResponse();
		// 新增entity回傳容器
		Course course = new Course();
		// 防呆:課程資訊不得為空
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("請輸入課程代碼!!");
			return res;
		}
		if (!StringUtils.hasText(coursename)) {
			res.setMessage("請輸入課程名稱!!");
			return res;
		}
		if (!StringUtils.hasText(courseday)) {
			res.setMessage("請輸入課程日!!");
			return res;
		}
		// 限制使用者輸入星期
		List<String> checkDay = new ArrayList<>();
		checkDay.add("MON");
		checkDay.add("TUE");
		checkDay.add("WED");
		checkDay.add("THU");
		checkDay.add("FRI");
		// lambda表達式
		if (!checkDay.stream().anyMatch(day -> day.equalsIgnoreCase(courseday))) {
			res.setMessage("請輸入正確課程日!!");
			return res;
		}
		if (coursestart == null) {
			res.setMessage("請輸入上課時間!!");
			return res;
		}
		if (courseend == null) {
			res.setMessage("請輸入下課時間!!");
			return res;
		}
		if (credit == null) {
			res.setMessage("請輸入學分!!");
			return res;
		}

		// 暫存以主鍵找到的欄位資訊
		List<Course> courseOp = courseDao.findByCourseCode(coursecode);
		// 遍歷資料庫中找到的欄位資訊並新增容器"courselist"放入
		for (Course courselist : courseOp) {
			// 防呆:課程代碼不可相同 如果輸入代碼等於資料庫中代碼時進入 //在新增容器"courselist"中的欄位資訊裡取課程代碼
			if (coursecode.equalsIgnoreCase(courselist.getCourseCode())) {
				res.setMessage("課程代碼重複!!");
				return res;
			}
		}

		// 新增容器設定並儲存所有欄位資訊
		course.setCourseCode(coursecode);
		course.setCourseName(coursename);
		course.setCourseDay(courseday);
		course.setCourseStart(coursestart);
		course.setCourseEnd(courseend);
		course.setCredit(credit);
		// 存入資料庫
		courseDao.save(course);
		// 設定欄位資訊
		res.setCoursecode(course.getCourseCode());
		res.setCoursename(course.getCourseName());
		res.setCourseday(course.getCourseDay());
		res.setCoursestart(course.getCourseStart());
		res.setCourseend(course.getCourseEnd());
		res.setCredit(course.getCredit());
		res.setMessage("課程新增成功!!");

		// 回傳回應容器
		return res;
	}

	// ===================================================================================================
	// 修改課程
	@Override
	public CourseResponse reviseCourse(String coursecode, String coursename, String courseday, LocalTime coursestart,
			LocalTime courseend, Integer credit) {
		// 新增回應回傳容器
		CourseResponse res = new CourseResponse();
		// 防呆:課程資訊不得為空
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("請輸入課程代碼!!");
			return res;
		}
		if (!StringUtils.hasText(coursename)) {
			res.setMessage("請輸入課程名稱!!");
			return res;
		}
		if (!StringUtils.hasText(courseday)) {
			res.setMessage("請輸入課程日!!");
			return res;
		}
		// 限制使用者輸入星期
		List<String> checkDay = new ArrayList<>();
		checkDay.add("MON");
		checkDay.add("TUE");
		checkDay.add("WED");
		checkDay.add("THU");
		checkDay.add("FRI");
		// lambda表達式
		if (!checkDay.stream().anyMatch(day -> day.equalsIgnoreCase(courseday))) {
			res.setMessage("請輸入正確上課日!!");
			return res;
		}
		if (coursestart == null) {
			res.setMessage("請輸入上課時間!!");
			return res;
		}
		if (courseend == null) {
			res.setMessage("請輸入下課時間!!");
			return res;
		}
		if (credit == null) {
			res.setMessage("請輸入學分!!");
			return res;
		}

		// 新增容器裝入透過主鍵找到的所有欄位資訊
		Optional<Course> courseOp = courseDao.findById(coursecode);
		// 判斷容器中資料存不存在
		// 存在時進入
		if (courseOp.isPresent()) {
			// 取得新增容器中欄位資訊
			Course course = courseOp.get();
			// 將容器中原有欄位資訊覆蓋為輸入資訊
			course.setCourseCode(coursecode);
			course.setCourseName(coursename);
			course.setCourseDay(courseday);
			course.setCourseStart(coursestart);
			course.setCourseEnd(courseend);
			course.setCredit(credit);
			// 儲存覆蓋資訊
			courseDao.save(course);
			// 設定更改資訊
			res.setCoursecode(coursecode);
			res.setCoursename(coursename);
			res.setCourseday(courseday);
			res.setCoursestart(coursestart);
			res.setCourseend(courseend);
			res.setCredit(credit);
			res.setMessage("課程更改成功!!");
			// 回傳更改資訊
			return res;
		}
		// 不存在
		res.setMessage("無此課程代碼!!");
		// 回傳容器值
		return res;
	}

	// ===================================================================================================
	// 刪除課程
	@Override
	public CourseResponse deleteCourseById(String coursecode) {
		// 新增回應回傳容器
		CourseResponse res = new CourseResponse();
		// 判斷輸入值是否為空
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("請輸入欲刪除課程代碼!!");
			return res;
		}
		// 透過課程代碼找到課程放入暫存容器
		Optional<Course> courseOp = courseDao.findById(coursecode);
		if (!courseOp.isPresent()) {
			res.setMessage("欲刪除課程不存在!!");
			return res;
		}
		// 如果此容器中有資料 //StringUtils.hasText --> 如果值為null或空字串回傳false
		if (courseOp.isPresent() || !StringUtils.hasText(coursecode)) {
			// 刪除
			courseDao.deleteById(coursecode);
		}
		res.setMessage("課程" + coursecode + "刪除成功!!");
		// 回傳容器值
		return res;
	}

	// ===================================================================================================
	// 輸入課程代碼找對應課程
	@Override
	public AddCourseResponse findCourseByCode(String coursecode) {
		// 新增回應回傳容器
		AddCourseResponse res = new AddCourseResponse();
		// 判斷輸入值為空時
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("請輸入欲查詢課程代碼!!");
			return res;
		}
		// 透過課程代碼(PK)找相對課程資訊存入
		Optional<Course> courseOp = courseDao.findById(coursecode);
		// 如果課程欄位資訊不存在
		if (!courseOp.isPresent()) {
			res.setMessage("無此課程代碼!!");
			return res;
		}
		// 新增拿來裝查詢到課程的容器
		Course course = courseOp.get();
		// 設定請求的課程資訊
		res.setCoursecode(course.getCourseCode());
		res.setCoursename(course.getCourseName());
		res.setCourseday(course.getCourseDay());
		res.setCoursestart(course.getCourseStart());
		res.setCourseend(course.getCourseEnd());
		res.setCredit(course.getCredit());
		// 回傳
		return res;
	}

	// ===================================================================================================
	// 輸入課程名稱找對應課程
	@Override
	public AddCourseResponse findCourseByName(String coursename) {
		// 新增回應回傳資訊
		AddCourseResponse res = new AddCourseResponse();
		// 如果沒輸入課程名稱時進入
		if (!StringUtils.hasText(coursename)) {
			res.setMessage("請輸入欲查詢課程名稱!!");
			return res;
		}
		// 透過課程名稱找到所有相同課程名稱
		List<Course> coursenamelist = courseDao.findAllByCourseName(coursename);
		// 課程資訊不存在的話
		if (coursenamelist.isEmpty()) {
			res.setMessage("無此課程名稱!!");
			return res;
		}

		// 設定所有符合課程名稱的課程資訊
		res.setCourseList(coursenamelist);
		// 回傳
		return res;
	}

	// ===================================================================================================
//	// 新增學生
//	@Override
//	public StudentCourse addStudent(String id, String studentId, String studentName) {
//		// 生成容器
//		StudentCourse studentCourse = new StudentCourse();
//		// 透過id找到學生放入暫存容器
//		Optional<StudentCourse> studentOp = studentCourseDao.findById(id);
//		// 如果有此學生資料 //StringUtils.hasText --> 如果值為null或空字串回傳false
//		if (studentOp.isPresent() || !StringUtils.hasText(id)) {
//			// ??????回傳空容器??????
//			return new StudentCourse();
//		} else {
//			// 新增學生資料
//			studentCourse.setId(id);
//			studentCourse.setStudentId(studentId);
//			studentCourse.setStudentName(studentName);
//			// 儲存學生資料
//			studentCourseDao.save(studentCourse);
//		}
//		// 回傳容器值
//		return studentCourse;
//	}

	// ===================================================================================================
	// 學生加選
	@Override
	public AddCourseResponse addCourse(String id, String studentid, String studentname, String coursecode) {
		StudentCourse studentCourseEntity = new StudentCourse();
		AddCourseResponse res = new AddCourseResponse();
		// 防止輸入空值
		if (!StringUtils.hasText(id)) {
			res.setMessage("請輸入表編號!!");
			return res;
		}
		if (!StringUtils.hasText(studentid)) {
			res.setMessage("請輸入學生學號!!");
			return res;
		}
		if (!StringUtils.hasText(studentname)) {
			res.setMessage("請輸入學生姓名!!");
			return res;
		}
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("請輸入欲加選課程代碼!!");
			return res;
		}

		// 輸入主鍵找到其實體資訊
		Optional<Course> courseOp = courseDao.findById(coursecode);
		// 如果課程代碼不存在
		if (!courseOp.isPresent()) {
			res.setMessage("無此課程!!");
		}
		// 如果課程存在
		// 拿來裝已經選好的課程
		Course course = courseOp.get();
		// 新增容器裝入已選課程學分
		int totalCredit = course.getCredit();
		// 判斷有無輸入課程代碼
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("無此課程代碼!!");
			return res;
		}

		// 透過學生學號找資料庫中相對的所有欄位資料
		List<StudentCourse> studentCourseList = studentCourseDao.findAllByStudentId(studentid);
		// 拿來裝正在選的課程 遍歷正在選的課程
		for (StudentCourse studentCourse : studentCourseList) {
			// 每次選課後學分存入已選課程學分容器
			totalCredit += studentCourse.getCredit();
			// 判斷"選好的課程"和"正在選的課程"中的id是否重複
			if (id.equalsIgnoreCase(studentCourse.getId())) {
				res.setMessage("id重複!!");
				return res;
			}
			// 判斷輸入學生學號是否相同(可以相同)
			if (studentCourse.getStudentId().equals(studentid)) {
				// 判斷輸入學生姓名是否相同
				if (!studentCourse.getStudentName().equals(studentname)) {
					// 學生學號相同但學生姓名不相同時進入
					res.setMessage("相同學號不可有不同姓名!!");
					return res;
				}
			}
			// 判斷"選好的課程"和"正在選的課程"中的課程代碼是否重複
			if (coursecode.equalsIgnoreCase(studentCourse.getCourseCode())) {
				// 不可選同一堂課程
				res.setMessage("課程代碼重複!!");
				return res;
			}
			// 判斷"選好的課程"和"正在選的課程"中的課程名稱是否重複
			if (course.getCourseName().equalsIgnoreCase(studentCourse.getCourseName())) {
				// 不可選同名稱的課程
				res.setMessage("課程名稱重複!!");
				return res;
			}
			// 判斷是否超過十學分
			if (totalCredit > 10) {
				res.setMessage("選課上限為10學分!!");
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
					res.setMessage("與現有課程時間重疊!!");
					return res;
				}
			}
		}
		// 設定輸入值
		studentCourseEntity.setId(id);
		studentCourseEntity.setStudentId(studentid);
		studentCourseEntity.setStudentName(studentname);
		studentCourseEntity.setCourseCode(course.getCourseCode());
		studentCourseEntity.setCourseName(course.getCourseName());
		studentCourseEntity.setCourseDay(course.getCourseDay());
		studentCourseEntity.setCourseStart(course.getCourseStart());
		studentCourseEntity.setCourseEnd(course.getCourseEnd());
		studentCourseEntity.setCredit(course.getCredit());
		// 存入資料庫
		studentCourseDao.save(studentCourseEntity);
		// 設定回傳資料及訊息
		res.setStudentid(studentid);
		res.setCoursecode(studentCourseEntity.getCourseCode());
		res.setMessage("課程新增完成!!");
		// 回傳大括號內所有動作過的容器
		return res;

	}

	// ===================================================================================================
	// 學生退選
	@Override
	public AddCourseResponse dropCourse(String studentid, String coursecode) {
		// 新增回應回傳容器
		AddCourseResponse res = new AddCourseResponse();
		// 判斷輸入值是否為空
		if (!StringUtils.hasText(studentid)) {
			res.setMessage("請輸入欲刪除學生學號!!");
			return res;
		}
		if (!StringUtils.hasText(coursecode)) {
			res.setMessage("請輸入欲刪除課程代碼!!");
			return res;
		}
		// 透過學生學號找到多筆學生選課資訊存入list容器(學生不只選一堂課會找到多個相同學生學號所以用list)
		List<StudentCourse> studentlist = studentCourseDao.findByStudentId(studentid);
		// 如果資料庫沒有輸入學生學號時進入
		if (studentlist.isEmpty()) {
			res.setMessage("欲刪除學生不存在!!");
			return res;
		}

		// 如果有的話遍歷多筆相同學生學號找到的課程資訊裝入studentInfo
		for (StudentCourse studentInfo : studentlist) {
			// 如果此學生課程代碼符合輸入課程代碼
			if (studentInfo.getCourseCode().equals(coursecode)) {
				// 刪除課程
				studentCourseDao.deleteByCourseCode(coursecode);
				res.setMessage("課程刪除成功!!");
				return res;
			}
			// 如果此學生課程代碼不符合輸入課程代碼
			res.setMessage("該學生沒有選修此課!!");
		}
		return res;
	}

	// ===================================================================================================
	// 查詢學生已選取課程
	@Override
	public StudentResponse findStudentLesson(String studentId) {
		// 新增回應回傳容器
		StudentResponse res = new StudentResponse();
		// 判斷輸入內容是否為空
		if (!StringUtils.hasText(studentId)) {
			res.setMessage("請輸入欲查詢學生學號!!");
			return res;
		}
		// 將利用學生學號找到此學生選的所有課程存入此容器
		List<StudentCourse> studentList = studentCourseDao.findAllByStudentId(studentId);
		// 判斷此容器是否為空
		if (studentList.isEmpty()) {
			res.setMessage("查無此學生!!");
			return res;
		}
		// 有課程的話裝進list中
		res.setStudentlist(studentList);
		// 回傳list
		return res;
	}

}
