package usercase;

import adapter.CourseRepository;
import adapter.TeacherRepository;
import domain.Course;
import domain.Student;
import java.util.List;
import java.util.logging.Logger;
import domain.Course;
import domain.exceptoins.*;

public class CourseService {

    private CourseRepository courseRepo;
    private final TeacherRepository teacherRepo;
    private static final Logger LOGGER = Logger.getLogger(CourseService.class.getName());

    public CourseService(CourseRepository repo, TeacherRepository teacherRepo) {
        if (repo == null) {
            throw new IllegalArgumentException("Course Repository cannot be null.");
        }
        if (teacherRepo == null) {
            throw new IllegalArgumentException("Teacher Repository cannot be null.");
        }
        this.courseRepo = repo;
        this.teacherRepo = teacherRepo;
    }

    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (courseRepo.findById(course.getCourseId()) != null) {
            throw new DuplicateEntityException("Course", course.getCourseId());
        }

        if (teacherRepo.findById(course.getTeacherId()) == null) {
            throw new EntityNotFoundException("Teacher", course.getTeacherId());
        }

        course.validate();
        courseRepo.save(course);
        LOGGER.info("Course added: ID= " + course.getCourseId() + ", Title= " + course.getTitle());
    }

    public void updateCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }

        if (courseRepo.findById(course.getCourseId()) == null) {
            throw new EntityNotFoundException("Course", course.getCourseId());
        }

        if (teacherRepo.findById(course.getTeacherId()) == null) {
            throw new EntityNotFoundException("Teacher", course.getTeacherId());
        }

        course.validate();
        courseRepo.update(course);
        LOGGER.info("Course updated: ID= " + course.getCourseId());
    }

    public void deleteCourse(int id) {
        if (courseRepo.findById(id) == null) {
            throw new EntityNotFoundException("Course", id);
        }
        courseRepo.delete(id);
        LOGGER.info("Course deleted: ID= " + id);
    }

    public List<Course> listCourse() {
        return courseRepo.findAll();
    }

    public Course findCourseById(int id) {
        Course c = courseRepo.findById(id);
        if (c == null) {
            throw new EntityNotFoundException("Course", id);
        }
        return c;
    }

    public void assignTeacher(int courseId, int teacherId) {
        Course c = findCourseById(courseId);

        if (teacherRepo.findById(teacherId) == null) {
            throw new EntityNotFoundException("Teacher", teacherId);
        }

        c.setTeacherId(teacherId);
        courseRepo.update(c);
        LOGGER.info("Teacher " + teacherId + " assigned to course " + courseId);
    }

}
