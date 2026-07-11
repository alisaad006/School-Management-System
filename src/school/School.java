package school;

import infrastructure.repository.*;
import adapter.*;
import java.util.Scanner;
import domain.*;
import infrastructure.console.ConsoleMenu;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import usercase.*;

public class School {

    private static final Logger LOGGER = Logger.getLogger(School.class.getName());

    public static void main(String[] args) {

        LogConfig.setup();

        // ── 1. Load Oracle JDBC Driver ───────────────────────────────────────
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Oracle JDBC Driver not found.", e);
            System.out.println("Oracle driver not found. Check your classpath.");
            return;
        }

        // ── 2. Connection settings ───────────────────────────────────────
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USER");
        String password = System.getenv("DB_PASS");

        // Fallback for local development only
        // REMOVE before deploying to any shared environment
        if (url == null || username == null || password == null) {
            LOGGER.warning("Environment variables not set. Using local dev config.");
            url = "jdbc:oracle:thin:@localhost:1521:XE";
            username = "pl";
            password = "123";
        }

//        String url = System.getenv("DB_URL");
//        String username = System.getenv("DB_USER");
//        String password = System.getenv("DB_PASS");
//
//        if (url == null || username == null || password == null) {
//            LOGGER.severe("Missing database environment variables.");
//            LOGGER.severe("Please set: DB_URL, DB_USER, DB_PASS");
//            return;
//        }
        // ── 3. Connect and write everything together ───────────────────────────────────────
        try (Connection con = DriverManager.getConnection(url, username, password)) {
 
            System.out.println("Connected to Oracle Database successfully.");
            LOGGER.info("Database connected.");
 
            // Repositories
            StudentRepository    sr = new OracleStudent(con);
            CourseRepository     cr = new OracleCourse(con);
            TeacherRepository    tr = new OracleTeacher(con);
            GradeRepository      gr = new OracleGrade(con);
            EnrollmentRepository er = new OracleEnrollment(con);
 
            // Services
            StudentService    studentService    = new StudentService(sr);
            CourseService     courseService     = new CourseService(cr, tr);
            TeacherService    teacherService    = new TeacherService(tr);
            GradeService      gradeService      = new GradeService(gr);
            EnrollmentService enrollmentService = new EnrollmentService(sr, cr, er);
 
            // Start the application
            ConsoleMenu menu = new ConsoleMenu(
                studentService, courseService,
                teacherService, gradeService,
                enrollmentService
            );
            menu.start();
 
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection failed.", e);
            System.out.println("Failed to connect to database. Check school.log for details.");
        }

    }

}
