package infrastructure.console;

import domain.*;
import domain.exceptoins.BusinessException;
import domain.exceptoins.DatabaseException;
import domain.exceptoins.DuplicateEntityException;
import domain.exceptoins.EntityNotFoundException;
import infrastructure.repository.OracleStudent;
import usercase.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentMenu extends BaseMenu {

    private final StudentService studentService;
    private static final Logger LOGGER = Logger.getLogger(StudentMenu.class.getName());

    public StudentMenu(StudentService studentService, Scanner scanner) {
        super(scanner);
        if (studentService == null) {
            throw new IllegalArgumentException("StudentService cannot be null.");
        }
        this.studentService = studentService;
    }

    public void showMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n____STUDENT MENU____");
            System.out.println("  1. Add Student");
            System.out.println("  2. Update Student");
            System.out.println("  3. Delete Student");
            System.out.println("  4. View All Students");
            System.out.println("  0. Back to Main Menu");
            int choice = readInt("Choose an Option: ");
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
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n========== STUDENT MENU ==========");
        System.out.println("  1. Add Student");
        System.out.println("  2. Update Student");
        System.out.println("  3. Delete Student");
        System.out.println("  4. View All Students");
        System.out.println("  0. Back to Main Menu");
        System.out.println("==================================");
    }

    private void addStudent() {
        System.out.println("\n[ Add New Student ]");
        try {
            int id = readPositiveInt("Student ID    :");
            String name = readString("Name          :");
            String studentCode = readString("Student Code  :");
            String email = readString("Email         :");

            Student s = new Student(id, name, studentCode, email);
            studentService.addStudent(s);
            System.out.println("  ✔ Student added successfully.");

        } catch (DuplicateEntityException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("  ✘ Validation error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in addStudent()", e);
        }
    }

    private void updateStudent() {
        System.out.println("\n[ Update Student ]");
        int id = readPositiveInt("Student ID to update:");

        Student existing;
        try {
            existing = studentService.findStudent(id);
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
            return;
        }

        System.out.println("\n  Current data:");
        System.out.println("  " + existing.getInfo());

        boolean done = false;
        while (!done) {
            System.out.println("\n  What do you want to edit?");
            System.out.println("  1- Name         : " + existing.getName());
            System.out.println("  2- Student Code : " + existing.getStudentCode());
            System.out.println("  3- Email        : " + existing.getEmail());
            System.out.println("  0- Save and Exit");

            int choice = readInt("  Enter choice:");

            switch (choice) {
                case 1:
                    String newName = readString("  Enter new name:");
                    existing.setName(newName);
                    System.out.println("  ✔ Name updated.");
                    break;
                case 2:
                    String newCode = readString("  Enter new student code:");
                    existing.setStudentCode(newCode);
                    System.out.println("  ✔ Student code updated.");
                    break;
                case 3:
                    String newEmail = readString("  Enter new email:");
                    try {
                        existing.setEmail(newEmail);
                        System.out.println("  ✔ Email updated.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("  ✘ Invalid email format: " + e.getMessage());
                    }
                    break;
                case 0:
                    try {
                        studentService.updateStudent(existing);
                        System.out.println("  ✔ Student saved successfully.");
                    } catch (EntityNotFoundException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    } catch (DatabaseException e) {
                        System.out.println("  ✘ Database error. Changes not saved.");
                        LOGGER.log(Level.SEVERE, "Database error in updateStudent()", e);
                    }
                    done = true;
                    break;
                default:
                    System.out.println("  Invalid choice.");
            }
        }
    }

    private void deleteStudent() {
        System.out.println("\n[ Delete Student ]");
        int id = readPositiveInt("Student ID to delete:");
 
        try {
            studentService.deleteStudent(id);
            System.out.println("  ✔ Student with ID " + id + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (BusinessException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in deleteStudent()", e);
        }
    }

    private void viewAllStudents() {
        System.out.println("\n[ All Students ]");
        try {
            List<Student> students = studentService.listStudent();
            if (students.isEmpty()) {
                System.out.println("  No students found.");
            } else {
                System.out.println("  Total: " + students.size() + " student(s)\n");
                for (Student s : students) {
                    System.out.println(s.getInfo());
                    System.out.println("  ----------------------------------");
                }
            }
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in viewAllStudents()", e);
        }
    }
}
