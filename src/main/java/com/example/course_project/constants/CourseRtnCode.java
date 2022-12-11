package com.example.course_project.constants;

public enum CourseRtnCode {

	//加註解 狀態代碼
	SUCCESSFUL("200", "成功!!"),
	
	SELECT_COURSE_FAILED("400", "Select course failed!!"),
	
	DELETE_COURSE_FAILED("400", "Delete course failed!!"),
	
	COURSECODE_REQUIRED("400", "請輸入課程代碼!!"),
	
	COURSECODE_REPEAT("400", "課程代碼重複!!"),
	
	COURSECODE_IS_EMPTY("400", "This coursecode is empty!!"),
	
	COURSENAME_REQUIRED("400", "請輸入課程名稱!!"),
	
	COURSENAME_REPEAT("400", "Coursename is repeat!!"),
	
	COURSENAME_IS_EMPTY("400", "This coursename is empty!!"),
	
	COURSEDAY_REQUIRED("400", "請輸入課程日!!"),
	
	COURSEDAY_FAILED("400", "請輸入正確課程日!!"),
	
	COURSESTART_REQUIRED("400", "請輸入課程開始時間!!"),
	
	COURSEEND_REQUIRED("400", "請輸入課程結束時間!!"),
	
	CREDIT_REQUIRED("400", "請輸入課程學分!!"),
	
	ID_REQUIRED("400", "Id is required!!"),
	
	ID_REPEAT("400", "Id is repeat!!"),
	
	STUDENTID_REQUIRED("400", "Studentid is required!!"),
	
	STUDENTID_IS_EMPTY("400", "This Studentid is empty!!"),
	
	STUDENTNAME_REQUIRED("400", "Studentname is required!!");
	
	private String code;
	
	private String message;
	
	private CourseRtnCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
