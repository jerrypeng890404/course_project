package com.example.course_project.constants;

public enum CourseRtnCode {

	SUCCESSFUL("200", "Successful"),
	
	SELECT_COURSE_FAILED("400", "Select course failed!!"),
	
	DELETE_COURSE_FAILED("400", "Delete course failed!!"),
	
	COURSECODE_REQUIRED("400", "Coursecode is required!!"),
	
	COURSECODE_REPEAT("400", "Coursecode is repeat!!"),
	
	COURSECODE_IS_EMPTY("400", "This coursecode is empty!!"),
	
	COURSENAME_REQUIRED("400", "Coursename is required!!"),
	
	COURSENAME_REPEAT("400", "Coursename is repeat!!"),
	
	COURSENAME_IS_EMPTY("400", "This coursename is empty!!"),
	
	COURSEDAY_REQUIRED("400", "Courseday is required!!"),
	
	COURSEDAY_FAILED("400", "Courseday is failed!!"),
	
	COURSESTARTTIME_REQUIRED("400", "Coursestarttime is required!!"),
	
	COURSEENDTIME_REQUIRED("400", "Courseendtime is required!!"),
	
	CREDIT_REQUIRED("400", "Credit is required!!"),
	
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
