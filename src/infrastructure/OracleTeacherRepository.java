package infrastructure;

import adapter.TeacherRepository;
import domain.Teacher;
import java.util.*;
import java.sql.*;

public class OracleTeacherRepository implements TeacherRepository {

    private final Connection con;

    public OracleTeacherRepository(Connection con) {
        this.con = con;
    }

    // A D D
    @Override
    public void save(Teacher teacher) {
        String query = "INSERT INTO teacher (teacher_id, name, email, subject, salary) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement pre = con.prepareStatement(query);
            pre.setInt(1, teacher.getId());
            pre.setString(2, teacher.getName());
            pre.setString(3, teacher.getEmail());
            pre.setString(4, teacher.getSubject());
            pre.setDouble(5, teacher.getSalary());

            pre.executeUpdate();
            System.out.println("Teacher Inserted Successfully.");

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    // U P D A T E
    @Override
    public void update(Teacher teacher) {
        String query = "update teacher set name = ?, email = ?, subject = ?, salary = ? where teacher_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(query);
            pre.setString(1, teacher.getName());
            pre.setString(2, teacher.getEmail());
            pre.setString(3, teacher.getSubject());
            pre.setDouble(4, teacher.getSalary());
            pre.setInt(5, teacher.getId());

            int row = pre.executeUpdate();

            if (row > 0) {
                System.out.println("Teacher data updated successfully");
            } else {
                System.out.println("No teacher found with ID " + teacher.getId() + " to update.");
            }
        } catch (SQLException e) {
            System.out.println("Database Error during updataing: " + e.getMessage());
        }
    }

    // D E L E T E
    @Override
    public void delete(int id) {
        String query = "Delete form teacher where id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(query);

            pre.setInt(1, id);
            int row = pre.executeUpdate();
            if (row > 0) {
                System.out.println("Teacher with ID " + id + " Delete successfuly.");
            } else {
                System.out.println("No Teacher found with ID " + id + " to Delete.");
            }

        } catch (SQLException e) {
            System.out.println("Database Error during Delete: " + e.getMessage());
        }
    }

    @Override
    public Teacher findById(int id) {

        String query = "select teacher_id, name, email, subject, salary from teacher where teacher_id = ?";

        try {
            PreparedStatement pre = con.prepareStatement(query);

            pre.setInt(1, id);
            try (ResultSet rs = pre.executeQuery()) {

                if (rs.next()) {
                    return new Teacher(
                            rs.getInt("teacher_id"),
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("subject"),
                            rs.getDouble("salary"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Database Error during search: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Teacher> findAll() {
        List<Teacher> teachers = new ArrayList<>();
        String query = "select * from teacher";

        try {

            PreparedStatement pre = con.prepareStatement(query);
            ResultSet rs = pre.executeQuery();

            while (rs.next()) {
                Teacher teacher = new Teacher(
                        rs.getInt("teacher_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("subject"),
                        rs.getDouble("salary"));
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            System.out.println("Database Error: " + e.getMessage());
        }
        return teachers;
    }

}
