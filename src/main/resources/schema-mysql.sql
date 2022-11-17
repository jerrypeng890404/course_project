CREATE TABLE IF NOT EXISTS `student_course` (
  `id` varchar(20) NOT NULL,
  `student_id` varchar(20) DEFAULT NULL,
  `student_name` varchar(20) DEFAULT NULL,
  `course_code` varchar(20) DEFAULT NULL,
  `course_name` varchar(20) DEFAULT NULL,
  `course_day` varchar(20) DEFAULT NULL,
  `course_start` time DEFAULT NULL,
  `course_end` time DEFAULT NULL,
  `credit` int DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS`course` (
  `course_code` varchar(20) NOT NULL,
  `course_name` varchar(20) DEFAULT NULL,
  `course_day` varchar(20) DEFAULT NULL,
  `course_start` time DEFAULT NULL,
  `course_end` time DEFAULT NULL,
  `credit` int DEFAULT NULL,
  PRIMARY KEY (`course_code`)
);