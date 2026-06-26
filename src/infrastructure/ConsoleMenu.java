package infrastructure;

import adapter.*;
import domain.*;
import infrastructure.*;
import usercase.*;
import java.sql.*;
import java.util.List;
import java.util.Scanner;
import sun.security.jgss.GSSToken;

public class ConsoleMenu {

    private final StudentService studentService;
    private final CourseService courseService;
    private final Scanner scanner;

    public ConsoleMenu(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("====================================================");
        System.out.println("       Welcome to School Managment System   ");
        System.out.println("====================================================");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = readInt("Enter your choice: ");
            System.out.println();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    updateStudent();
                    break;

                case 3:
                    deleteStudent();
                    break;

                case 4:
                    viewAllStudents();
                    break;

                case 5:
                    addCourse();
                    break;

                case 6:
                    updateCourse();
                    break;

                case 7:
                    deleteCourse();
                    break;

                case 8:
                    viewAllCourse();
                    break;

                case 9:
                    enrollStudent();
                    break;

                case 10:
                    assignTeacher();
                    break;

                case 11:
                    addTeacher();
                    break;

                case 12:
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }
    }

    // ─── Menu ────────────────────────────────────────────────────────────────
    private void showMenu() {
        System.out.println("_________________________________________");
        System.out.println("  STUDENTS");
        System.out.println("   1.  Add Student");
        System.out.println("   2.  Update Student");
        System.out.println("   3.  Delete Student");
        System.out.println("   4.  View All Students");
        System.out.println("  COURSES");
        System.out.println("   5.  Add Course");
        System.out.println("   6.  Update Course");
        System.out.println("   7.  Delete Course");
        System.out.println("   8.  View All Courses");
        System.out.println("  OPERATIONS");
        System.out.println("   9.  Enroll Student in Course");
        System.out.println("   10. Assign Teacher to Course");
        System.out.println("   11. Add Teacher");
        System.out.println("   12. Exit");
        System.out.println("_________________________________________");
    }

    // ─── Student Handlers ────────────────────────────────────────────────────────────────
    private void addStudent() {
        System.out.println("[ Add New Student ]");
        int id = readInt("Student ID: ");
        String name = readString("Name: ");
        String email = readString("Email: ");
        String studentcode = readString("Student Code: ");

        try {
            Student s = new Student(id, name, email, studentcode);
            studentService.addStudent(s);
            System.out.println("Student added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateStudent() {
        System.out.println("[ Update Student ]");
        int id = readInt("Student ID to Update: ");

        Student existing = studentService.findStudent(id);
        if (existing == null) {
            return;
        }
        System.out.println("Current: " + existing.getInfo());
        String name = readString("New Name: ");
        String email = readString("New Email: ");
        String studentcode = readString("New Student Code: ");

        try {
            Student update = new Student(id, name, studentcode, email);
            studentService.updateStudent(update);
            System.out.println("Student updated successfuly.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteStudent() {
        System.out.println("[ Delete Student ]");
        int id = readInt("Student ID to delete: ");

        try {
            studentService.deleteStudent(id);
            System.out.println("Student deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllStudents() {
        System.out.println("[ All Students ]");
        List<Student> students = studentService.listStudent();
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println("  " + s.getInfo());
            }
        }
    }

    // ─── Course Handlers ────────────────────────────────────────────────────────────────
    private void addCourse() {
        System.out.println("[ Add New Course ]");
        int courseId = readInt("Course ID : ");
        String title = readString("Title : ");
        int teacherId = readInt("Teacher ID : ");
        int maxStudents = readInt("Max Students : ");

        try {
            Course c = new Course(courseId, title, teacherId, maxStudents);
            courseService.addcourse(c);
            System.out.println("Course added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateCourse() {
        System.out.println("[ Update Course ]");
        int id = readInt("Course ID to Update");

        Course existing = courseService.findCourseById(id);
        if (existing == null) {
            return;
        }

        System.out.println("Current: " + existing.getInfo());
        String title = readString("New Title: ");
        int teacherId = readInt("New Teacher ID: ");
        int maxStudent = readInt("New Max Student: ");

        try {
            Course update = new Course(teacherId, title, teacherId, maxStudent);
            courseService.updateCourse(update);
            System.out.println("Course updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteCourse() {
        System.out.println("[ Delete Course ]");
        int id = readInt("Course ID to Delete: ");

        try {
            courseService.deleteCourse(id);
            System.out.println("Course deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void viewAllCourse() {
        System.out.println("[ All Course ]");
        List<Course> course = courseService.listCourse();
        if (course.isEmpty()) {
            System.out.println("No course found.");
        } else {
            for (Course c : course) {
                System.out.println(" " + c.getInfo());
            }
        }
    }

    // ─── Operation Handlers ────────────────────────────────────────────────────────────────
    private void enrollStudent() {
        System.out.println("[ Enroll Student in Course ]");
        int studentId = readInt("Student ID: ");
        int courseId = readInt("Course ID: ");

        try {
            courseService.enrollStudentInCourse(courseId, studentService, studentId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void assignTeacher() {
        System.out.println("[ Assign Teacher to Course ]");
        int courseId = readInt("Course ID: ");
        int teacherId = readInt("Teacher ID: ");

        try {
            courseService.assignTeacher(courseId, teacherId);
            System.out.println("Teacher assigned successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void addTeacher() {
        System.out.println("[ Add New Teacher ]");
        int id = readInt("  Teacher ID : ");
        String name = readString("  Name       : ");
        String email = readString("  Email      : ");
        String subject = readString("  Subject    : ");
        double salary = readDouble("  Salary     : ");

        try {
            Teacher t = new Teacher(id, name, email, subject, salary);
            System.out.println("   Teacher created: " + t.getInfo());
            System.out.println("  (Teacher persistence will be active in Phase 2)");
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
        }
    }

// ─── Input helpers ────────────────────────────────────────────────────────────────
    private String readString(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please eter a valid number.");
            }
        }
    }
}
