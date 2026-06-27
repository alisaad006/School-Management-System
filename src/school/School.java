package school;

import adapter.*;
import java.util.Scanner;
import domain.*;
import infrastructure.*;
import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import usercase.*;

public class School {

    public static void main(String[] args) {

        // ── 1. Load Oracle JDBC Driver ───────────────────────────────────────
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(School.class.getName()).log(Level.SEVERE, "Oracle Driver not found.", ex);
            return; // stop if driver is missing
        }

        // ── 2. Connection settings ───────────────────────────────────────
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "pl";
        String password = "123";

        // ── 3. Connect and write everything together ───────────────────────────────────────
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Successfully connected to Oracle Database.");
            
            // Repositoryes - Oracle Implementations
            StudentRepository sr = new OracleStudentRepository(con);
            CourseRepository cr = new OracleCourseRepository(con);
            TeacherRepository tr = new OracleTeacherRepository(con);
            GradeRepository gr = new OracleGradeRepository(con);

            // Services - recive repositoryes throgh constructor
            
            StudentService studentservice = new StudentService(sr);
            CourseService courseservice = new CourseService(cr);
            TeacherService teacherservice = new TeacherService(tr);
            GradeService gradeservice = new GradeService(gr);
            

            // Start the console menu
            ConsoleMenu menu = new ConsoleMenu(studentservice, courseservice, teacherservice, gradeservice);
            menu.start();
        } catch (SQLException ex) {
            Logger.getLogger(School.class.getName()).log(Level.SEVERE, "Database connection failed.", ex);
        }

    }

}
