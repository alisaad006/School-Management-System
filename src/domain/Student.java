package domain;

import java.util.*;

public class Student extends Person {

    private String studentCode;

    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(int id, String name, String studentCode, String email) {
        super(id, name, email);
        if (studentCode == null || studentCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Student code cannot be empty.");
        }
        this.studentCode = studentCode.trim();
    }

    public void enroll(Course c) {
        if (c == null) {
            throw new IllegalArgumentException("Cannot enroll in a null course.");
        }
        if (enrolledCourses.contains(c)) {
            System.out.println("Warning: Already enrolled in " + c.getTitle());
            return;
        }
        enrolledCourses.add(c);
        System.out.println("Successfully enrolled in: " + c.getTitle());
    }

    public void setStudentCode(String studentCode) {
        if (studentCode == null || studentCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Student code cannot be empty.");
        }
        this.studentCode = studentCode.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        Student s = (Student) o;
        return getId() == s.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public void setEnrolledCourses(List<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public String getStudentCode() {
        return studentCode;
    }

    @Override
    public String getInfo() {
        return "Student ["
                + "\n  ID           = " + getId()
                + "\n  Name         = " + getName()
                + "\n  Email        = " + getEmail()
                + "\n  Student Code = " + studentCode
                + "\n  Enrolled     = " + enrolledCourses.size() + " course(s)"
                + "\n]";
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

}
