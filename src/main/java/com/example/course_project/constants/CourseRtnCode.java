package com.example.course_project.constants;

public enum CourseRtnCode {

	//�[���� ���A�N�X
	SUCCESSFUL("200", "���\!!"),
	
	SELECT_COURSE_FAILED("400", "Select course failed!!"),
	
	DELETE_COURSE_FAILED("400", "Delete course failed!!"),
	
	COURSECODE_REQUIRED("400", "�п�J�ҵ{�N�X!!"),
	
	COURSECODE_REPEAT("400", "�ҵ{�N�X����!!"),
	
	COURSECODE_IS_EMPTY("400", "This coursecode is empty!!"),
	
	COURSENAME_REQUIRED("400", "�п�J�ҵ{�W��!!"),
	
	COURSENAME_REPEAT("400", "Coursename is repeat!!"),
	
	COURSENAME_IS_EMPTY("400", "This coursename is empty!!"),
	
	COURSEDAY_REQUIRED("400", "�п�J�ҵ{��!!"),
	
	COURSEDAY_FAILED("400", "�п�J���T�ҵ{��!!"),
	
	COURSESTART_REQUIRED("400", "�п�J�ҵ{�}�l�ɶ�!!"),
	
	COURSEEND_REQUIRED("400", "�п�J�ҵ{�����ɶ�!!"),
	
	CREDIT_REQUIRED("400", "�п�J�ҵ{�Ǥ�!!"),
	
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
