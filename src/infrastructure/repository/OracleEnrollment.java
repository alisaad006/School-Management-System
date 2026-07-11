package infrastructure.repository;

import adapter.EnrollmentRepository;
import domain.Enrollment;
import domain.exceptoins.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleEnrollment implements EnrollmentRepository {

    private final Connection con;
    private static final Logger LOGGER = Logger.getLogger(OracleEnrollment.class.getName());
    private static final int ORA_DUPLICATE_KEY = 1; // ORA-00001 unique constraint violated

    public OracleEnrollment(Connection con) {
        this.con = con;
    }

    @Override
    public void enroll(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments (student_id, course_id, enroll_data) VALUES (?, ?, SYSDATE)";
        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, studentId);
            pre.setInt(2, courseId);
            pre.executeUpdate();
            LOGGER.info("Enrolled: Student ID=" + studentId + " in Course ID=" + courseId);
        } catch (SQLException e) {
            if (e.getErrorCode() == ORA_DUPLICATE_KEY) {
                throw new BusinessException(
                        "Student ID " + studentId + " is already enrolled in Course ID " + courseId);
            }
            LOGGER.log(Level.SEVERE, "Error enrolling student", e);
            throw new DatabaseException("Failed to enroll student ID " + studentId
                    + " in course ID " + courseId, e);
        }
    }

    @Override
    public void unenroll(int studentId, int courseId) {
        String sql = "Delete from enrollments where student_id = ? and course_id = ?";
        
        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, studentId);
            pre.setInt(2, courseId);
            int rows = pre.executeUpdate();
            if (rows == 0) {
                throw new BusinessException(
                    "Student ID " + studentId
                    + " is not enrolled in Course ID " + courseId);
            }
            LOGGER.info("Unenrolled: Student ID=" + studentId + " from Course ID=" + courseId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error unenrolling student", e);
            throw new DatabaseException("Failed to unenroll student ID " + studentId
                + " from course ID " + courseId, e);
        }
    }

    @Override
    public boolean isEnrolled(int studentId, int courseId) {
        String sql = "SELECT COUNT(*) FROM enrollments WHERE STUDENT_ID = ? AND COURSE_ID = ?";

        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, studentId);
            pre.setInt(2, courseId);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error checking enrollment", e);
            throw new DatabaseException("Failed to check enrollment status", e);
        }
        return false;
    }

    @Override
    public List<Enrollment> findEnrollmentsByStudent(int studentId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT STUDENT_ID, COURSE_ID, ENROLL_DATE FROM enrollments WHERE STUDENT_ID = ?";

        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, studentId);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding enrollments for student ID=" + studentId, e);
            throw new DatabaseException("Failed to find enrollments for student ID " + studentId, e);
        }
        return list;
    }

    @Override
    public List<Enrollment> findEnrollmentsByCourse(int courseId) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT student_id, COURSE_ID, ENROLL_DATE FROM enrollments WHERE COURSE_ID = ?";

        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, courseId);
            try (ResultSet rs = pre.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding enrollments for course ID=" + courseId, e);
            throw new DatabaseException("Failed to find enrollments for course ID " + courseId, e);
        }
        return list;
    }

    @Override
    public void deleteByStudent(int studentId) {
        String sql = "Delete from enrollments where student_id = ?";
        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, studentId);
            int rows = pre.executeUpdate();
            LOGGER.info("Deleted " + rows + " enrollment(s) for student ID=" + studentId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting enrollments for student ID=" + studentId, e);
            throw new DatabaseException("Failed to delete enrollments for student ID " + studentId, e);
        }
    }

    @Override
    public void deleteByCourse(int courseId) {
        String sql = "Delete from enrollments where course_id = ?";
        try (PreparedStatement pre = con.prepareStatement(sql)) {
            pre.setInt(1, courseId);
            int rows = pre.executeUpdate();
            LOGGER.info("Deleted " + rows + " enrollment(s) for course ID=" + courseId);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting enrollments for course ID=" + courseId, e);
            throw new DatabaseException("Failed to delete enrollments for course ID " + courseId, e);
        }
    }

    private Enrollment mapRow(ResultSet rs) throws SQLException {
        LocalDate date = rs.getDate("enroll_date") != null
                ? rs.getDate("enroll_date").toLocalDate()
                : LocalDate.now();
        return new Enrollment(
                rs.getInt("student_id"),
                rs.getInt("course_id"),
                date
        );
    }
}
