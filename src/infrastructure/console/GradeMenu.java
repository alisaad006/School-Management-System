package infrastructure.console;

import domain.*;
import domain.exceptoins.*;
import usercase.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GradeMenu extends BaseMenu {

    private final GradeService gradeService;
    private static final Logger LOGGER = Logger.getLogger(GradeMenu.class.getName());

    public GradeMenu(GradeService gradeService, Scanner scanner) {
        super(scanner);
        if (gradeService == null) {
            throw new IllegalArgumentException("GradeService cannot be null.");
        }
        this.gradeService = gradeService;
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            printMenu();
            int choice = readInt("Choose an Option:");
 
            switch (choice) {
                case 1: addGrade();                  break;
                case 2: updateGrade();               break;
                case 3: deleteGrade();               break;
                case 4: viewGradesByStudent();       break;
                case 5: viewGradesByCourse();        break;
                case 6: calculateStudentAverage();   break;
                case 0: back = true;                 break;
                default: System.out.println("  Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("\n========== GRADE MENU ==========");
        System.out.println("  1. Add Grade");
        System.out.println("  2. Update Grade");
        System.out.println("  3. Delete Grade");
        System.out.println("  4. View Grades by Student");
        System.out.println("  5. View Grades by Course");
        System.out.println("  6. Calculate Student Average");
        System.out.println("  0. Back to Main Menu");
        System.out.println("=================================");
    }

    private void addGrade() {
        System.out.println("\n[ Add Grade ]");
        try {
            int gradeId = readPositiveInt("Grade ID   :");
            int studentId = readPositiveInt("Student ID :");
            int courseId = readPositiveInt("Course ID  :");
            double score = readScore("Score (0-100) :");

            // Grade constructor auto-calculates letter from score
            Grade g = new Grade(gradeId, studentId, courseId, score);
            gradeService.addGrade(g);
            System.out.println("  ✔ Grade added successfully. Letter: " + g.getLetter());

        } catch (DuplicateEntityException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (ValidationException e) {
            System.out.println("  ✘ Validation error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in addGrade()", e);
        }
    }

    private void updateGrade() {
        System.out.println("\n[ Update Grade ]");
        int gradeId = readPositiveInt("Grade ID to update:");

        Grade existing;
        try {
            existing = gradeService.findGrade(gradeId);
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
            return;
        }

        System.out.println("\n  Current data:");
        System.out.println("  " + existing.getInfo());

        boolean done = false;
        while (!done) {
            System.out.println("\n  What do you want to edit?");
            System.out.println("  1- Score  : " + existing.getScore()
                    + "  (Letter will auto-update)");
            System.out.println("  0- Save and Exit");

            int choice = readInt("  Enter choice:");

            switch (choice) {
                case 1:
                    try {
                        double newScore = readScore("  Enter new score (0-100):");
                        existing.setScore(newScore);
                        System.out.println("  ✔ Score updated. New letter: " + existing.getLetter());
                    } catch (ValidationException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    }
                    break;
                case 0:
                    try {
                        gradeService.updateGrade(existing);
                        System.out.println("  ✔ Grade saved successfully.");
                    } catch (EntityNotFoundException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    } catch (DatabaseException e) {
                        System.out.println("  ✘ Database error. Changes not saved.");
                        LOGGER.log(Level.SEVERE, "Database error in updateGrade()", e);
                    }
                    done = true;
                    break;
                default:
                    System.out.println("  Invalid choice.");
            }
        }
    }

    private void deleteGrade() {
        System.out.println("\n[ Delete Grade ]");
        int gradeId = readPositiveInt("Grade ID to delete:");

        try {
            gradeService.deleteGrade(gradeId);
            System.out.println("  ✔ Grade with ID " + gradeId + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in deleteGrade()", e);
        }
    }

    private void viewGradesByStudent() {
        System.out.println("\n[ Grades by Student ]");
        int studentId = readPositiveInt("Student ID:");

        try {
            List<Grade> grades = gradeService.getStudentGrade(studentId);
            System.out.println("  Total: " + grades.size() + " grade(s)\n");
            for (Grade g : grades) {
                System.out.println(g.getInfo());
                System.out.println("  ----------------------------------");
            }
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in viewGradesByStudent()", e);
        }
    }

    private void viewGradesByCourse() {
        System.out.println("\n[ Grades by Course ]");
        int courseId = readPositiveInt("Course ID:");

        try {
            List<Grade> grades = gradeService.getCourseGrade(courseId);
            System.out.println("  Total: " + grades.size() + " grade(s)\n");
            for (Grade g : grades) {
                System.out.println(g.getInfo());
                System.out.println("  ----------------------------------");
            }
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in viewGradesByCourse()", e);
        }
    }

    private void calculateStudentAverage() {
        System.out.println("\n[ Calculate Student Average ]");
        int studentId = readPositiveInt("Student ID:");

        try {
            double avg = gradeService.calcAverage(studentId);
            System.out.printf("  ✔ Average grade for student %d: %.2f (%s)%n",
                    studentId, avg, letterFromAverage(avg));
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in calculateStudentAverage()", e);
        }
    }

    private String letterFromAverage(double avg) {
        if (avg >= 90) return "A";
        if (avg >= 80) return "B";
        if (avg >= 70) return "C";
        if (avg >= 60) return "D";
        return "F";
    }

}
