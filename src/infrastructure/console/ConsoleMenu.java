package infrastructure.console;

import usercase.*;
import java.util.Scanner;

public class ConsoleMenu {

    private final StudentMenu studentMenu;
    private final CourseMenu courseMenu;
    private final TeacherMenu teacherMenu;
    private final GradeMenu gradeMenu;
    private final EnrollmentMenu enrollmentMenu;
    private final Scanner scanner;

    // ── Constructor ──────────────────────────────────────────────────────────
    public ConsoleMenu(StudentService studentService,
            CourseService courseService,
            TeacherService teacherService,
            GradeService gradeService,
            EnrollmentService enrollmentService) {

        this.scanner = new Scanner(System.in);
        this.studentMenu = new StudentMenu(studentService, scanner);
        this.teacherMenu = new TeacherMenu(teacherService, scanner);
        this.courseMenu = new CourseMenu(courseService, enrollmentService, scanner);
        this.enrollmentMenu = new EnrollmentMenu(enrollmentService, scanner);
        this.gradeMenu = new GradeMenu(gradeService, scanner);
    }

    // ── Main loop ─────────────────────────────────────────────────────────────
    public void start() {
        System.out.println("====================================================");
        System.out.println("      Welcome to School Management System           ");
        System.out.println("====================================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice:");

            switch (choice) {
                case 1: studentMenu.showMenu();    break;
                case 2: courseMenu.showMenu();     break;
                case 3: teacherMenu.showMenu();    break;
                case 4: gradeMenu.showMenu();      break;
                case 5: enrollmentMenu.showMenu(); break;
                case 0:
                    System.out.println("Goodbye!");
                    scanner.close();
                    running = false;
                    break;
                default:
                    System.out.println("  Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    // ── Private helpers ───────────────────────────────────────────────────────
    private void printMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("  1. Students");
        System.out.println("  2. Courses");
        System.out.println("  3. Teachers");
        System.out.println("  4. Grades");
        System.out.println("  5. Enrollment");
        System.out.println("  0. Exit");
        System.out.println("================================");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt + " ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  Please enter a valid number.");
            }
        }
    }
}
