package infrastructure.repository;

import adapter.CourseRepository;
import domain.Course;
import domain.exceptoins.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleCourse implements CourseRepository {

    private final Connection con;
    private static final Logger LOGGER = Logger.getLogger(OracleCourse.class.getName());

    private static final int ORA_FK_VIOLATION = 2292;

    public OracleCourse(Connection con) {
        if (con == null) {
            throw new IllegalArgumentException("Connetion cannot be null.");
        }
        this.con = con;
    }

    // A D D
    @Override
    public void save(Course course) {
        String sql = "INSERT INTO course (course_id, title, teacher_id, max_students) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pre = con.prepareStatement(sql);) {
            pre.setInt(1, course.getCourseId());
            pre.setString(2, course.getTitle());
            pre.setInt(3, course.getTeacherId());
            pre.setInt(4, course.getMaxStudents());

            pre.executeUpdate();
            LOGGER.info("Course inserted: ID= " + course.getCourseId());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting course ID = " + course.getCourseId(), e);
            throw new DatabaseException("Failed to insert course with ID " + course.getCourseId(), e);
        }
    }

    // U P D A T E
    @Override
    public void update(Course course) {
        String sql = "update course set title = ?, teacher_id = ?, max_students = ? where course_id = ?";

        try (PreparedStatement pre = con.prepareStatement(sql);) {
            pre.setString(1, course.getTitle());
            pre.setInt(2, course.getTeacherId());
            pre.setInt(3, course.getMaxStudents());
            pre.setInt(4, course.getCourseId());

            int row = pre.executeUpdate();
            if (row == 0) {
                throw new EntityNotFoundException("Course", course.getCourseId());
            }
            LOGGER.info("Course updated: ID=" + course.getCourseId());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating course ID=" + course.getCourseId(), e);
            throw new DatabaseException("Failed to update course with ID " + course.getCourseId(), e);
        }
    }

    // D E L E T E
    @Override
    public void delete(int id) {
        String sql = "delete from course where course_id = ?";

        try (PreparedStatement pre = con.prepareStatement(sql);) {
            pre.setInt(1, id);
            int row = pre.executeUpdate();
            if (row == 0) {
                throw new EntityNotFoundException("Course", id);
            }
            LOGGER.info("Course deleted ID = " + id);
        } catch (SQLException e) {
            if (e.getErrorCode() == ORA_FK_VIOLATION) {
                throw new BusinessException(
                        "Cannot delete course ID " + id
                        + " because it has active enrollments or grades."
                        + " Remove associated records first.");
            }
            LOGGER.log(Level.SEVERE, "Error deleting course ID=" + id, e);
            throw new DatabaseException("Failed to delete course with ID " + id, e);
        }
    }

    // FIND BY ID
    @Override
    public Course findById(int id) {
        String sql = "select course_id, title, teacher_id, max_students from course where course_id = ?";

        try (PreparedStatement pre = con.prepareStatement(sql);) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding course ID= " + id, e);
            throw new DatabaseException("Failed to find course with ID " + id, e);
        }
        return null;
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "select course_id, title, teacher_id, max_students from course";

        try (PreparedStatement pre = con.prepareStatement(sql);) {
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                courses.add(mapRow(rs));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all courses", e);
            throw new DatabaseException("Failed to fetch all courses", e);
        }
        return courses;
    }

    private Course mapRow(ResultSet rs) throws SQLException {
        Course c = new Course(
                rs.getInt("course_id"),
                rs.getString("title"),
                rs.getInt("teacher_id"),
                rs.getInt("max_students")
        );
        return c;
    }

}
