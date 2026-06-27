package domain;

import java.util.*;

public class Student extends Person {

    private String studentCode;
    private List<Course> enrolledCourses = new ArrayList<>();

    public Student(int id, String name, String studentCode, String email) {
        super(id, name, email);
        this.studentCode = studentCode;

    }

    public void enroll(Course c) {

        if (enrolledCourses.contains(c)) {
            System.out.println("Warning: Already enrolled in " + c.getTitle());
            return;
        }

        for (Course enrolled : enrolledCourses) {
            if (enrolled.getCourseId() == c.getCourseId()) {
                System.out.println("Warning: Course ID " + c.getCourseId() + " is already enrolled");
                return;
            }
        }

        enrolledCourses.add(c);
        System.out.println("Successfully enrolled in: " + c.getTitle());
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", Student Code: " + studentCode + ", Enrolled Courses: " + enrolledCourses.size();
    }

    public String getStudentCode() {
        return studentCode;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }
    
}
