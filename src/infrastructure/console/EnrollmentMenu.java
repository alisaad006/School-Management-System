package infrastructure.console;
 
import domain.Enrollment;
import domain.exceptoins.*;
import usercase.EnrollmentService;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnrollmentMenu extends BaseMenu {
 
    private final EnrollmentService enrollmentService;
    private static final Logger LOGGER = Logger.getLogger(EnrollmentMenu.class.getName());
 
    // ── Constructor ──────────────────────────────────────────────────────────
    public EnrollmentMenu(EnrollmentService enrollmentService, Scanner scanner) {
        super(scanner);
        if (enrollmentService == null) {
            throw new IllegalArgumentException("EnrollmentService cannot be null.");
        }
        this.enrollmentService = enrollmentService;
    }
 
    // ── Menu loop ─────────────────────────────────────────────────────────────
    public void showMenu() {
        boolean back = false;
        while (!back) {
            printMenu();
            int choice = readInt("Choose an Option:");
 
            switch (choice) {
                case 1: enrollStudent();            break;
                case 2: unenrollStudent();          break;
                case 3: viewByStudent();            break;
                case 4: viewByCourse();             break;
                case 0: back = true;                break;
                default: System.out.println("  Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }
 
    // ── Private helpers ───────────────────────────────────────────────────────
 
    private void printMenu() {
        System.out.println("\n========== ENROLLMENT MENU ==========");
        System.out.println("  1. Enroll Student in Course");
        System.out.println("  2. Unenroll Student from Course");
        System.out.println("  3. View Enrollments by Student");
        System.out.println("  4. View Enrollments by Course");
        System.out.println("  0. Back to Main Menu");
        System.out.println("=====================================");
    }
 
    // ── Enroll ───────────────────────────────────────────────────────────────
 
    private void enrollStudent() {
        System.out.println("\n[ Enroll Student in Course ]");
        int studentId = readPositiveInt("Student ID :");
        int courseId  = readPositiveInt("Course ID  :");
 
        try {
            enrollmentService.enrollStudent(studentId, courseId);
            System.out.println("  ✔ Student enrolled successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (BusinessException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in enrollStudent()", e);
        }
    }
 
    // ── Unenroll───────────────────────────────────────────────────────────────
 
    private void unenrollStudent() {
        System.out.println("\n[ Unenroll Student from Course ]");
        int studentId = readPositiveInt("Student ID :");
        int courseId  = readPositiveInt("Course ID  :");
 
        try {
            enrollmentService.unenrollStudent(studentId, courseId);
            System.out.println("  ✔ Student unenrolled successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (BusinessException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in unenrollStudent()", e);
        }
    }
 
    // ── View by student ───────────────────────────────────────────────────────
 
    private void viewByStudent() {
        System.out.println("\n[ Enrollments by Student ]");
        int studentId = readPositiveInt("Student ID:");
 
        try {
            List<Enrollment> enrollments = enrollmentService.getStudentEnrollments(studentId);
            if (enrollments.isEmpty()) {
                System.out.println("  No enrollments found for this student.");
            } else {
                System.out.println("  Total: " + enrollments.size() + " enrollment(s)\n");
                for (Enrollment e : enrollments) {
                    System.out.println(e.toString());
                    System.out.println("  ----------------------------------");
                }
            }
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in viewByStudent()", e);
        }
    }
 
    // ── View by course ────────────────────────────────────────────────────────
 
    private void viewByCourse() {
        System.out.println("\n[ Enrollments by Course ]");
        int courseId = readPositiveInt("Course ID:");
 
        try {
            List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseId);
            if (enrollments.isEmpty()) {
                System.out.println("  No enrollments found for this course.");
            } else {
                System.out.println("  Total: " + enrollments.size() + " student(s) enrolled\n");
                for (Enrollment e : enrollments) {
                    System.out.println(e.toString());
                    System.out.println("  ----------------------------------");
                }
            }
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in viewByCourse()", e);
        }
    }
}