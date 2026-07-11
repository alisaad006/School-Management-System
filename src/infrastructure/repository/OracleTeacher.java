package infrastructure.repository;

import adapter.TeacherRepository;
import domain.Teacher;
import domain.exceptoins.*;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleTeacher implements TeacherRepository {

    private final Connection con;
    private static final Logger LOGGER = Logger.getLogger(OracleTeacher.class.getName());
    private static final int ORA_FK_VIOLATION = 2292;

    public OracleTeacher(Connection con) {
        if (con == null) {
            throw new IllegalArgumentException("Connection cannot be null.");
        }
        this.con = con;
    }

    // A D D
    @Override
    public void save(Teacher teacher) {
        String query = "INSERT INTO teacher (teacher_id, name, email, subject, salary) VALUES (?,?,?,?,?)";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, teacher.getId());
            pre.setString(2, teacher.getName());
            pre.setString(3, teacher.getEmail());
            pre.setString(4, teacher.getSubject());
            pre.setDouble(5, teacher.getSalary());
            pre.executeUpdate();
            LOGGER.info("Teacher inserted: ID=" + teacher.getId());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error inserting teacher ID=" + teacher.getId(), e);
            throw new DatabaseException("Failed to insert teacher with ID " + teacher.getId(), e);
        }
    }

    // U P D A T E
    @Override
    public void update(Teacher teacher) {
        String query = "update teacher set name = ?, email = ?, subject = ?, salary = ? where teacher_id = ?";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setString(1, teacher.getName());
            pre.setString(2, teacher.getEmail());
            pre.setString(3, teacher.getSubject());
            pre.setDouble(4, teacher.getSalary());
            pre.setInt(5, teacher.getId());

            int rows = pre.executeUpdate();
            if (rows == 0) {
                throw new EntityNotFoundException("Teacher", teacher.getId());
            }
            LOGGER.info("Teacher updated: ID=" + teacher.getId());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating teacher ID=" + teacher.getId(), e);
            throw new DatabaseException("Failed to update teacher with ID " + teacher.getId(), e);
        }
    }

    // D E L E T E
    @Override
    public void delete(int id) {
        String query = "DELETE FROM teachers WHERE teacher_id = ?";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, id);
            int rows = pre.executeUpdate();
            if (rows == 0) {
                throw new EntityNotFoundException("Teacher", id);
            }
            LOGGER.info("Teacher deleted: ID=" + id);
        } catch (SQLException e) {
            if (e.getErrorCode() == ORA_FK_VIOLATION) {
                throw new BusinessException(
                        "Cannot delete teacher ID " + id
                        + " because they are assigned to active courses."
                        + " Reassign or remove their courses first.");
            }
            LOGGER.log(Level.SEVERE, "Error deleting teacher ID=" + id, e);
            throw new DatabaseException("Failed to delete teacher with ID " + id, e);
        }
    }

    @Override
    public Teacher findById(int id) {

        String query = "select teacher_id, name, email, subject, salary from teacher where teacher_id = ?";

        try (PreparedStatement pre = con.prepareStatement(query)) {
            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error finding teacher ID=" + id, e);
            throw new DatabaseException("Failed to find teacher with ID " + id, e);
        }
        return null;
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "select * from teacher";

        try (PreparedStatement pre = con.prepareStatement(query);
                ResultSet rs = pre.executeQuery()) {
            while (rs.next()) {
                teachers.add(mapRow(rs));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error fetching all teachers", e);
            throw new DatabaseException("Failed to fetch all teachers", e);
        }
        return teachers;
    }

    private Teacher mapRow(ResultSet rs) throws SQLException {
        return new Teacher(
                rs.getInt("teacher_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("subject"),
                rs.getDouble("salary")
        );
    }

}
