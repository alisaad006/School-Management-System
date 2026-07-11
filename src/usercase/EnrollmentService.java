package usercase;

import adapter.*;
import domain.*;
import domain.exceptoins.BusinessException;
import domain.exceptoins.EntityNotFoundException;
import java.util.List;
import java.util.logging.Logger;

public class EnrollmentService {

    private final StudentRepository studentRepo;
    private final CourseRepository courseRepo;
    private final EnrollmentRepository enrollmentRepo;
    private static final Logger LOGGER = Logger.getLogger(EnrollmentService.class.getName());

    public EnrollmentService(StudentRepository studentRepo, CourseRepository courseRepo, EnrollmentRepository enrollmentRepo) {
        if (studentRepo == null) {
            throw new IllegalArgumentException("StudentRepository cannot be null.");
        }
        if (courseRepo == null) {
            throw new IllegalArgumentException("CourseRepository cannot be null.");
        }
        if (enrollmentRepo == null) {
            throw new IllegalArgumentException("EnrollmentRepository cannot be null.");
        }

        this.studentRepo = studentRepo;
        this.courseRepo = courseRepo;
        this.enrollmentRepo = enrollmentRepo;
    }

    public void enrollStudent(int studentId, int courseId) {
        Student student = studentRepo.findById(studentId);
        if (student == null) {
            throw new EntityNotFoundException("Student", studentId);
        }

        Course course = courseRepo.findById(courseId);
        if (course == null) {
            throw new EntityNotFoundException("Course", courseId);
        }

        if (enrollmentRepo.isEnrolled(studentId, courseId)) {
            throw new BusinessException("Student '" + student.getName() + "' is already enrolled in '" + course.getTitle() + "'.");
        }

        if (course.isFull()) {
            throw new BusinessException("Course '" + course.getTitle() + "' is full." + "Max Student: " + course.getMaxStudents());
        }

        enrollmentRepo.enroll(studentId, courseId);
        course.incrementEnrolled();
        courseRepo.update(course);
        LOGGER.info("Student ID = " + studentId + " enrolled in Course ID = " + courseId);
    }

    public void unenrollStudent(int studentId, int courseId) {
        Student student = studentRepo.findById(studentId);
        if (student == null) {
            throw new EntityNotFoundException("Student", studentId);
        }

        Course course = courseRepo.findById(courseId);
        if (course == null) {
            throw new EntityNotFoundException("Course", courseId);
        }

        if (!enrollmentRepo.isEnrolled(studentId, courseId)) {
            throw new BusinessException("Student '" + student.getName() + "' is not enrolled in '" + course.getTitle() + "'.");
        }

        enrollmentRepo.unenroll(studentId, courseId);

        if (course.getEnrolledCount() > 0) {
            course.setEnrolledCount(course.getEnrolledCount() - 1);
            courseRepo.update(course);
        }

        LOGGER.info("Student ID = " + studentId + " unenrolled from Course ID = " + courseId);
    }

    public List<Enrollment> getStudentEnrollments(int studentId) {
        if (studentRepo.findById(studentId) == null) {
            throw new EntityNotFoundException("Student", studentId);
        }
        return enrollmentRepo.findEnrollmentsByStudent(studentId);
    }

    public List<Enrollment> getCourseEnrollments(int courseId) {
        if (courseRepo.findById(courseId) == null) {
            throw new EntityNotFoundException("Course", courseId);
        }
        return enrollmentRepo.findEnrollmentsByCourse(courseId);
    }

    public void removeAllEnrollmentsForStudent(int studentId) {
        enrollmentRepo.deleteByStudent(studentId);
        LOGGER.info("All enrollments removed for student ID=" + studentId);
    }

    public void removeAllEnrollmentsForCourse(int courseId) {
        enrollmentRepo.deleteByCourse(courseId);
        LOGGER.info("All enrollments removed for course ID=" + courseId);
    }

}
