package infrastructure.console;

import domain.*;
import domain.exceptoins.*;
import usercase.*;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeacherMenu extends BaseMenu {

    private final TeacherService teacherService;
        private static final Logger LOGGER = Logger.getLogger(TeacherMenu.class.getName());

    public TeacherMenu(TeacherService teacherService, Scanner scanner) {
        super(scanner);
        if (teacherService == null) {
            throw new IllegalArgumentException("TeacherService cannot be null.");
        }
        this.teacherService = teacherService;
    }
 
    // ── Menu loop ─────────────────────────────────────────────────────────────
    public void showMenu() {
        boolean back = false;
        while (!back) {
            printMenu();
            int choice = readInt("Choose an Option:");
 
            switch (choice) {
                case 1: addTeacher();      break;
                case 2: updateTeacher();   break;
                case 3: deleteTeacher();   break;
                case 4: viewAllTeachers(); break;
                case 0: back = true;       break;
                default: System.out.println("  Invalid choice. Please try again.");
            }
            System.out.println();
        }
    }
 
    // ── Private helpers ───────────────────────────────────────────────────────
 
    private void printMenu() {
        System.out.println("\n========== TEACHER MENU ==========");
        System.out.println("  1. Add Teacher");
        System.out.println("  2. Update Teacher");
        System.out.println("  3. Delete Teacher");
        System.out.println("  4. View All Teachers");
        System.out.println("  0. Back to Main Menu");
        System.out.println("===================================");
    }
 
    // ── Add ──────────────────────────────────────────────────────────────────
 
    private void addTeacher() {
        System.out.println("\n[ Add New Teacher ]");
        try {
            int    id      = readPositiveInt("Teacher ID :");
            String name    = readString("Name       :");
            String email   = readString("Email      :");
            String subject = readString("Subject    :");
            double salary  = readDouble("Salary     :");
 
            // salary extra check — readDouble allows negatives
            if (salary < 0) {
                System.out.println("  ✘ Salary cannot be negative.");
                return;
            }
 
            Teacher t = new Teacher(id, name, email, subject, salary);
            teacherService.addTeacher(t);
            System.out.println("  ✔ Teacher added successfully.");
 
        } catch (DuplicateEntityException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (ValidationException e) {
            System.out.println("  ✘ Validation error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // catches Person.validdata() errors — bad email, empty name
            System.out.println("  ✘ Validation error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in addTeacher()", e);
        }
    }
 
    // ── Update ───────────────────────────────────────────────────────────────
 
    private void updateTeacher() {
        System.out.println("\n[ Update Teacher ]");
        int id = readPositiveInt("Teacher ID to update:");
 
        Teacher existing;
        try {
            existing = teacherService.findTeacher(id);
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
            return;
        }
 
        System.out.println("\n  Current data:");
        System.out.println("  " + existing.getInfo());
 
        boolean done = false;
        while (!done) {
            System.out.println("\n  What do you want to edit?");
            System.out.println("  1- Name    : " + existing.getName());
            System.out.println("  2- Email   : " + existing.getEmail());
            System.out.println("  3- Subject : " + existing.getSubject());
            System.out.println("  4- Salary  : " + existing.getSalary());
            System.out.println("  0- Save and Exit");
 
            int choice = readInt("  Enter choice:");
 
            switch (choice) {
                case 1:
                    try {
                        String newName = readString("  Enter new name:");
                        existing.setName(newName);
                        System.out.println("  ✔ Name updated.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        String newEmail = readString("  Enter new email:");
                        existing.setEmail(newEmail);
                        System.out.println("  ✔ Email updated.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("  ✘ Invalid email: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        String newSubject = readString("  Enter new subject:");
                        existing.setSubject(newSubject);
                        System.out.println("  ✔ Subject updated.");
                    } catch (ValidationException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        double newSalary = readDouble("  Enter new salary:");
                        existing.setSalary(newSalary);
                        System.out.println("  ✔ Salary updated.");
                    } catch (ValidationException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    }
                    break;
                case 0:
                    try {
                        teacherService.updateTeacher(existing);
                        System.out.println("  ✔ Teacher saved successfully.");
                    } catch (EntityNotFoundException e) {
                        System.out.println("  ✘ " + e.getMessage());
                    } catch (DatabaseException e) {
                        System.out.println("  ✘ Database error. Changes not saved.");
                        LOGGER.log(Level.SEVERE, "Database error in updateTeacher()", e);
                    }
                    done = true;
                    break;
                default:
                    System.out.println("  Invalid choice.");
            }
        }
    }
 
    // ── Delete ───────────────────────────────────────────────────────────────
 
    private void deleteTeacher() {
        System.out.println("\n[ Delete Teacher ]");
        int id = readPositiveInt("Teacher ID to delete:");
 
        try {
            teacherService.deleteTeacher(id);
            System.out.println("  ✔ Teacher with ID " + id + " deleted successfully.");
        } catch (EntityNotFoundException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (BusinessException e) {
            System.out.println("  ✘ " + e.getMessage());
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in deleteTeacher()", e);
        }
    }
 
    // ── View all ─────────────────────────────────────────────────────────────
 
    private void viewAllTeachers() {
        System.out.println("\n[ All Teachers ]");
        try {
            List<Teacher> teachers = teacherService.listTeacher();
            if (teachers.isEmpty()) {
                System.out.println("  No teachers found.");
            } else {
                System.out.println("  Total: " + teachers.size() + " teacher(s)\n");
                for (Teacher t : teachers) {
                    System.out.println(t.getInfo());
                    System.out.println("  ----------------------------------");
                }
            }
        } catch (DatabaseException e) {
            System.out.println("  ✘ Database error. Please try again later.");
            LOGGER.log(Level.SEVERE, "Database error in viewAllTeachers()", e);
        }
    }
}
