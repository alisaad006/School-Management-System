package usercase;

import adapter.CourseRepository;
import domain.Course;
import domain.Student;
import java.util.List;

public class CourseService {

    private CourseRepository repo;

    public CourseService(CourseRepository repo) {
        this.repo = repo;
    }

    public void addcourse(Course course) {
        if (course == null) {
            System.out.println("Error: Cannot add a null Course");
            return;
        }
        if (repo.findByID(course.getCourseId()) != null) {
            System.out.println("Error: Course with ID " + course.getCourseId() + " already exists.");
            return;
        }
        repo.save(course);
    }

    public void updateCourse(Course course) {
        if (course == null) {
            System.out.println("Error: Cannot update a null Course.");
            return;
        }
        repo.update(course);
    }

    public void deleteCourse(int id) {
        repo.delete(id);
    }

    public List<Course> listCourse() {
        return repo.findAll();
    }

    public Course findCourseById(int id) {
        Course c = repo.findByID(id);
        if (c == null) {
            System.out.println("Course with ID " + id + " not found.");
        }
        return c;
    }

    public void assignTeacher(int courseId, int teacherId) {
        Course c = repo.findByID(courseId);

        if (c == null) {
            System.out.println("Cannot assign teacher. Course ID " + courseId + " dose not exist.");
            return;
        }

        c.setTeacherId(teacherId);
        System.out.println("Assigning Teacher ID " + teacherId + " to Course: " + c.getTitle());
        repo.save(c);
    }
    
    public void enrollStudentInCourse(int courseId, StudentService studentservice, int studentId) {
        Course course = findCourseById(courseId);
        Student student = studentservice.findStudent(studentId);
        
        if (course == null || student == null) return;
        
        if (course.isFull()) {
            System.out.println("Error: Course '" + course.getTitle() + "' is full.");
            return;
        }
        
        student.enroll(course);
        course.incrementEnrolled();
        repo.update(course);
        System.out.println("Enrolled " + student.getName() + " in " + course.getTitle() + " successfully.");
    }

}
