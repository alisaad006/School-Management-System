package infrastructure;

import adapter.*;
import domain.*;
import infrastructure.*;
import usercase.*;
import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {

    private final StudentService studentService;
    private final CourseService courseService;
    private final TeacherService teacherService;
    private final GradeService gradeService;
    private final Scanner scanner;

    public ConsoleMenu(StudentService studentService, CourseService courseService, TeacherService teacherService, GradeService gradeService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.teacherService = teacherService;
        this.gradeService = gradeService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("====================================================");
        System.out.println("       Welcome to School Managment System   ");
        System.out.println("====================================================");

        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = readInt("Enter your choice: ");
            System.out.println();

            switch (choice) {
                case 1:  studentMenu();  break;
                case 2:  courseMenu(); break;
                case 3:  teacherMenu(); break;
                case 4:  gradesMenu(); break;
                case 5:  System.out.println("Goodbye!"); running = false; break;
                default: System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    // ─── Menu ────────────────────────────────────────────────────────────────
    private void showMainMenu() {
        System.out.println("_________________________________________");
        System.out.println("  Main Menu");
        System.out.println("   1.  Students");
        System.out.println("   2.  Course");
        System.out.println("   3.  Teacher");
        System.out.println("   4.  Grades");
        System.out.println("   5. Exit");
        System.out.println("_________________________________________");
    }

    // ─── Student Sub-menu ────────────────────────────────────────────────────────────────
    private void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n____STUDENT MENU____");
            System.out.println("  1. Add Student");
            System.out.println("  2. Update Student");
            System.out.println("  3. Delete Student");
            System.out.println("  4. View All Students");
            System.out.println("  0. Back to Main Menu");
            int choice = readInt("Choose an Optoin: ");
            System.out.println();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: updateStudent(); break;
                case 3: deleteStudent(); break;
                case 4: viewAllStudents(); break;
                case 0: back = true; break;
                default:System.out.println("Invalid choice.");
            }
        }
    }

    // A D D
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

    // U P D A T E
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

    // D E L E T E
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

    // V I E W    A L L    S T U D E N T S
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

    // ─── Course (Sub-menu & Handlers) ────────────────────────────────────────────────────────────────
    private void courseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n____COURSE MENU____");
            System.out.println("  1. Add Course");
            System.out.println("  2. Update Course");
            System.out.println("  3. Delete Course");
            System.out.println("  4. View All COurse");
            System.out.println("  5. Enroll Student in Course");
            System.out.println("  6. Assign Teacher to Course");
            System.out.println("  7. Back to Main Menu");
            int choice = readInt("Choose an Optoin: ");
            System.out.println();

            switch (choice) {
                case 1: addCourse();break;
                case 2: updateCourse();break;
                case 3: deleteCourse();break;
                case 4:viewAllCourse(); break;
                case 5: enrollStudent(); break;
                case 6:assignTeacher(); break;
                case 0:back = true;break;
                default:System.out.println("Invalid choice.");
            }
        }
    }

    // A D D
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

    // U P D A T E
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

    // D E L E T E
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

    // V I E W    A L L    C O U R S E
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

    // ─── Teacher (Sub-menu & Handlers) ────────────────────────────────────────────────────────────────
    private void teacherMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- TEACHER MENU ---");
            System.out.println("  1. Add Teacher");
            System.out.println("  2. Update Teacher");
            System.out.println("  3. Delete Teacher");
            System.out.println("  4. View All Teachers");
            System.out.println("  0. Back to Main Menu");
            int choice = readInt("Choose an option: ");
            System.out.println();

            switch (choice) {
                case 1: addTeacher();break;
                case 2: updateTeacher();break;
                case 3: deleteTeacher();break;
                case 4: viewAllTeachers(); break;
                case 0: back = true;
                default:System.out.println("Invalid choice.");
            }
        }
    }

    // A D D
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

    // U P D A T E
    private void updateTeacher() {
        System.out.println("[ Update Teacher ]");
        int id = readInt("Teacher ID to Update");

        Teacher existing = teacherService.findTeacher(id);
        if (existing == null) {
            return;
        }

        System.out.println("Current: " + existing.getInfo());
        String name = readString("New Name: ");
        String email = readString("New Email: ");
        String subject = readString("New Subject: ");
        double salary = readDouble("New Salary: ");

        try {
            Teacher update = new Teacher(id, name, email, subject, salary);
            teacherService.updateTeacher(update);
            System.out.println("Teacher updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // D E L E T E
    private void deleteTeacher() {
        System.out.println("[ Delete Teacher ]");
        int id = readInt("Teacher ID to Delete: ");

        try {
            teacherService.deleteTeacher(id);
            System.out.println("Teacher deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // V I E W    A L L    T E A C H E R
    private void viewAllTeachers() {
        System.out.println("[ All Teacher ]");
        List<Teacher> teacher = teacherService.listTeacher();
        if (teacher.isEmpty()) {
            System.out.println("No course found.");
        } else {
            for (Teacher t : teacher) {
                System.out.println(" " + t.getInfo());
            }
        }
    }

// ─── Grades (Sub-menu & Handlers) ────────────────────────────────────────────────────────────────
    private void gradesMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- GRADES MENU ---");
            System.out.println("  1. Add Grade");
            System.out.println("  2. Update Grade");
            System.out.println("  3. Delete Grade");
            System.out.println("  4. View Grades by Student");
            System.out.println("  5. View Grades by Course");
            System.out.println("  6. Calculate Student Average");
            System.out.println("  0. Back to Main Menu");
            int choice = readInt("Choose an option: ");
            System.out.println();

            switch (choice) {
                case 1: addGrade();break;
                case 2: updateGrade();break;
                case 3: deleteGrade(); break;
                case 4: viewGradesByStudent();break;
                case 5: viewGradesByCourse(); break;
                case 6: calculateStudentAverage(); break;
                case 0: back = true;break;
                default:System.out.println("Invalid choice.");
            }
        }
    }

    // A D D
    private void addGrade() {
        System.out.println("[ Add Grade ]");
        int gradeId = readInt("Grade ID: ");
        int studentId = readInt("Student ID: ");
        int courseId = readInt("Course ID: ");
        double score = readDouble("Score: ");
        String latter = readString("Letter: ");

        try {
            Grade g = new Grade(gradeId, studentId, courseId, score, latter);
            gradeService.addGrade(g);
            System.out.println("Grade added successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // U P D A T E
    private void updateGrade() {
        System.out.println("[ Update Grade ]");
        int gradeId = readInt("Grade ID to Update: ");

        Grade existing = gradeService.findGrade(gradeId);
        if (existing == null) {
            System.out.println("Grade not found.");
            return;
        }
        System.out.println("Current: " + existing.getInfo());
        double newScore = readDouble("New Score: ");
        String newlatter = readString("New Latter: ");

        try {
            Grade updated = new Grade(gradeId, existing.getStudentId(), existing.getCourseId(), newScore, newlatter);
            gradeService.updateGrade(updated);
            System.out.println("Grade updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // D E L E T E
    private void deleteGrade() {
        System.out.println("[ Delete Grade ]");
        int gradeId = readInt("Grade ID to delete: ");

        try {
            gradeService.deleteGrade(gradeId);
            System.out.println("Grade deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // VIEW GRADES BY STUDETS
    private void viewGradesByStudent() {
        System.out.println("[ Grades by Student ]");
        int studentId = readInt("Student ID: ");
        List<Grade> grades = gradeService.getStudentGrade(studentId);
        if (grades.isEmpty()) {
            System.out.println("No grades found for this student.");
        } else {
            for (Grade g : grades) {
                System.out.println("  " + g.getInfo());
            }
        }
    }

    // VIEW GRADES BY COURSE
    private void viewGradesByCourse() {
        System.out.println("[ Grades by Course ]");
        int courseId = readInt("Course ID: ");
        List<Grade> grades = gradeService.getCourseGrade(courseId);
        if (grades.isEmpty()) {
            System.out.println("No grades found for this course.");
        } else {
            for (Grade g : grades) {
                System.out.println("  " + g.getInfo());
            }
        }
    }

    // CALCULATE STUDENT AVERAGE MARKS
    private void calculateStudentAverage() {
        System.out.println("[ Calculate Student Average ]");
        int studentId = readInt("Student ID: ");
        try {
            double avg = gradeService.calcAverage(studentId);
            System.out.printf("Average grade for student %d: %.2f%n", studentId, avg);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

// ─── Operations (Enroll & Assign) ────────────────────────────────────────────────────────────────
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
