--------------------------------------------------------
--  File created - Saturday-July-11-2026   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence CLIENTS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."CLIENTS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence GRADE_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."GRADE_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence MEDIATORS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."MEDIATORS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence STUDENT_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."STUDENT_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence STUDENT_SEQ1
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."STUDENT_SEQ1"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SUBER_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."SUBER_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence TRAVEL_REQUESTS_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."TRAVEL_REQUESTS_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence TRAVEL_REQUESTS_SEQ1
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."TRAVEL_REQUESTS_SEQ1"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence TRAVEL_REQUESTS_SEQ2
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."TRAVEL_REQUESTS_SEQ2"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence TRAVEL_REQUESTS_SEQ3
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."TRAVEL_REQUESTS_SEQ3"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence TRAVEL_REQUESTS_SEQ4
--------------------------------------------------------

   CREATE SEQUENCE  "PL"."TRAVEL_REQUESTS_SEQ4"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table COURSE
--------------------------------------------------------

  CREATE TABLE "PL"."COURSE" 
   (	"COURSE_ID" NUMBER, 
	"TITLE" VARCHAR2(100), 
	"TEACHER_ID" NUMBER, 
	"MAX_STUDENTS" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Table ENROLLMENTS
--------------------------------------------------------

  CREATE TABLE "PL"."ENROLLMENTS" 
   (	"STUDENT_ID" NUMBER, 
	"COURSE_ID" NUMBER, 
	"ENROLL_DATA" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table GRADES
--------------------------------------------------------

  CREATE TABLE "PL"."GRADES" 
   (	"GRADE_ID" NUMBER, 
	"STUDENT_ID" NUMBER, 
	"COURSE_ID" NUMBER, 
	"SCORE" NUMBER(5,2), 
	"LETTER" VARCHAR2(2)
   ) ;
--------------------------------------------------------
--  DDL for Table STUDENT
--------------------------------------------------------

  CREATE TABLE "PL"."STUDENT" 
   (	"STUDENT_ID" NUMBER, 
	"STUDENT_CODE" VARCHAR2(20), 
	"STUDENT_NAME" VARCHAR2(100), 
	"STUDENT_EMAIL" VARCHAR2(100)
   ) ;
--------------------------------------------------------
--  DDL for Table TEACHER
--------------------------------------------------------

  CREATE TABLE "PL"."TEACHER" 
   (	"TEACHER_ID" NUMBER, 
	"NAME" VARCHAR2(100), 
	"EMAIL" VARCHAR2(100), 
	"SUBJECT" VARCHAR2(100), 
	"SALARY" NUMBER(10,2)
   ) ;
--------------------------------------------------------
--  DDL for Index SYS_C009784
--------------------------------------------------------

  CREATE UNIQUE INDEX "PL"."SYS_C009784" ON "PL"."COURSE" ("COURSE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C009786
--------------------------------------------------------

  CREATE UNIQUE INDEX "PL"."SYS_C009786" ON "PL"."ENROLLMENTS" ("STUDENT_ID", "COURSE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C009951
--------------------------------------------------------

  CREATE UNIQUE INDEX "PL"."SYS_C009951" ON "PL"."GRADES" ("GRADE_ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C009782
--------------------------------------------------------

  CREATE UNIQUE INDEX "PL"."SYS_C009782" ON "PL"."STUDENT" ("STUDENT_ID") 
  ;
--------------------------------------------------------
--  DDL for Index SYS_C009783
--------------------------------------------------------

  CREATE UNIQUE INDEX "PL"."SYS_C009783" ON "PL"."TEACHER" ("TEACHER_ID") 
  ;
--------------------------------------------------------
--  Constraints for Table COURSE
--------------------------------------------------------

  ALTER TABLE "PL"."COURSE" ADD PRIMARY KEY ("COURSE_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table ENROLLMENTS
--------------------------------------------------------

  ALTER TABLE "PL"."ENROLLMENTS" ADD PRIMARY KEY ("STUDENT_ID", "COURSE_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table GRADES
--------------------------------------------------------

  ALTER TABLE "PL"."GRADES" ADD PRIMARY KEY ("GRADE_ID") ENABLE;
  ALTER TABLE "PL"."GRADES" MODIFY ("LETTER" NOT NULL ENABLE);
  ALTER TABLE "PL"."GRADES" MODIFY ("SCORE" NOT NULL ENABLE);
  ALTER TABLE "PL"."GRADES" MODIFY ("COURSE_ID" NOT NULL ENABLE);
  ALTER TABLE "PL"."GRADES" MODIFY ("STUDENT_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STUDENT
--------------------------------------------------------

  ALTER TABLE "PL"."STUDENT" ADD PRIMARY KEY ("STUDENT_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table TEACHER
--------------------------------------------------------

  ALTER TABLE "PL"."TEACHER" ADD PRIMARY KEY ("TEACHER_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table COURSE
--------------------------------------------------------

  ALTER TABLE "PL"."COURSE" ADD CONSTRAINT "FK_TEACHER_ID" FOREIGN KEY ("TEACHER_ID")
	  REFERENCES "PL"."TEACHER" ("TEACHER_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ENROLLMENTS
--------------------------------------------------------

  ALTER TABLE "PL"."ENROLLMENTS" ADD CONSTRAINT "FK_COURSEID" FOREIGN KEY ("COURSE_ID")
	  REFERENCES "PL"."COURSE" ("COURSE_ID") ENABLE;
  ALTER TABLE "PL"."ENROLLMENTS" ADD CONSTRAINT "FK_STUDENTID" FOREIGN KEY ("STUDENT_ID")
	  REFERENCES "PL"."STUDENT" ("STUDENT_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table GRADES
--------------------------------------------------------

  ALTER TABLE "PL"."GRADES" ADD CONSTRAINT "FK_GRADE_COURSE" FOREIGN KEY ("COURSE_ID")
	  REFERENCES "PL"."COURSE" ("COURSE_ID") ENABLE;
  ALTER TABLE "PL"."GRADES" ADD CONSTRAINT "FK_GRADE_STUDENT" FOREIGN KEY ("STUDENT_ID")
	  REFERENCES "PL"."STUDENT" ("STUDENT_ID") ENABLE;
