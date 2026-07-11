package infrastructure.console;

import domain.*;
import domain.exceptoins.*;
import usercase.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseMenu extends BaseMenu {

    private final CourseService courseService;
    private final EnrollmentService enrollmentService;
    private static final Logger LOGGER = Logger.getLogger(CourseMenu.class.getName());

    public CourseMenu(CourseService courseService, EnrollmentService enrollmentService, Scanner scanner) {
        super(scanner);
        if (courseService == null) {
            throw new IllegalArgumentException("CourseService cannot be null.");
        }
        if (enrollmentService == null) {
            throw new IllegalArgumentException("EnrollmentService cannot be null.");
        }
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            printMenu();
            int choice = readInt("Choose an Option:");
 
            switch (choice) {
                case 1: addCourse();       break;
                case 2: updateCourse();    break;
                case 3: deleteCourse();    break;
                case 4: viewAllCourses();  break;
                case 5: enrollStudent();   break;
                case 6: assignTeacher();   break;
                case 0: back = true;       break;
                default: System.out.println("  Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("\n========== COURSE MENU ==========");
        System.out.println("  1. Add Course");
        System.out.println("  2. Update Course");
        System.out.println("  3. Delete Course");
        System.out.println("  4. View All Courses");
        System.out.println("  5. Enroll Student in Course");
        System.out.println("  6. Assign Teacher to Course");
        System.out.println("  0. Back to Main Menu");
        System.out.println("==================================");
    }

    // ── Add ──────────────────────────────────────────────────────────────────
    private void addCourse() {
        System.out.println("\n[ Add New Course ]");
        try {
            int courseId = readPositiveInt("Course ID    :");
            String title = readString("Title        :");
            int teacherId = readPositiveInt("Teacher ID   :");
            int maxStudents = readPositiveInt("Max Students :");

            Course c = new Course(courseId, title, teacherId, maxStudents);
            courseService.addCourse(c);
            System.out.println("  ✔ Course added successfully.");

        } catch (DuplicateEntityException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (ValidationException e) {
            System.out.println("  ✘ Validation error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in addCourse()", e);
        }
    }

    // ── Update ───────────────────────────────────────────────────────────────
    private void updateCourse() {
        System.out.println("\n[ Update Course ]");
        int id = readPositiveInt("Course ID to update:");

        Course existing;
        try {
            existing = courseService.findCourseById(id);
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
            return;
        }

        System.out.println("\n  Current data:");
        System.out.println("  " + existing.getInfo());

        boolean done = false;
        while (!done) {
            System.out.println("\n  What do you want to edit?");
            System.out.println("  1- Title        : " + existing.getTitle());
            System.out.println("  2- Teacher ID   : " + existing.getTeacherId());
            System.out.println("  3- Max Students : " + existing.getMaxStudents());
            System.out.println("  0- Save and Exit");

            int choice = readInt("  Enter choice:");

            switch (choice) {
                case 1:
                    try {
                        String newTitle = readString("  Enter new title:");
                        existing.setTitle(newTitle);
                        System.out.println("  ✔ Title updated.");
                    } catch (ValidationException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        int newTeacherId = readPositiveInt("  Enter new Teacher ID:");
                        existing.setTeacherId(newTeacherId);
                        System.out.println("  ✔ Teacher ID updated.");
                    } catch (ValidationException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        int newMax = readPositiveInt("  Enter new Max Students:");
                        existing.setMaxStudents(newMax);
                        System.out.println("  ✔ Max Students updated.");
                    } catch (ValidationException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    }
                    break;
                case 0:
                    try {
                        courseService.updateCourse(existing);
                        System.out.println("  ✔ Course saved successfully.");
                    } catch (EntityNotFoundException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    } catch (DatabaseException e) {
                        System.out.println("  ✘ Database error. Changes not saved.");
                        LOGGER.log(Level.SEVERE, "Database error in updateCourse()", e);
                    }
                    done = true;
                    break;
                default:
                    System.out.println("  Invalid choice.");
            }
        }
    }

    // ── Delete ───────────────────────────────────────────────────────────────
    private void deleteCourse() {
        System.out.println("\n[ Delete Course ]");
        int id = readPositiveInt("Course ID to delete:");

        try {
            courseService.deleteCourse(id);
            System.out.println("  ✔ Course with ID " + id + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (BusinessException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in deleteCourse()", e);
        }
    }

    // ── View all ─────────────────────────────────────────────────────────────
    private void viewAllCourses() {
        System.out.println("\n[ All Courses ]");
        try {
            List<Course> courses = courseService.listCourse();
            if (courses.isEmpty()) {
                System.out.println("  No courses found.");
            } else {
                System.out.println("  Total: " + courses.size() + " course(s)\n");
                for (Course c : courses) {
                    System.out.println(c.getInfo());
                    System.out.println("  ----------------------------------");
                }
            }
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in viewAllCourses()", e);
        }
    }

    // ── Assign teacher ────────────────────────────────────────────────────────
    private void assignTeacher() {
        System.out.println("\n[ Assign Teacher to Course ]");
        int courseId = readPositiveInt("Course ID  :");
        int teacherId = readPositiveInt("Teacher ID :");

        try {
            courseService.assignTeacher(courseId, teacherId);
            System.out.println("  ✔ Teacher assigned successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in assignTeacher()", e);
        }
    }

    // ── Enroll student ────────────────────────────────────────────────────────
    private void enrollStudent() {
        System.out.println("\n[ Enroll Student in Course ]");
        int studentId = readPositiveInt("Student ID :");
        int courseId = readPositiveInt("Course ID  :");

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

}
