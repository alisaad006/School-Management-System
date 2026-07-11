package domain;

import domain.exceptoins.ValidationException;
import java.util.Objects;

public class Teacher extends Person {

    private String subject;
    private double salary;

    public Teacher(int id, String name, String email, String subject, double salary) {
        super(id, name, email);
        this.subject = subject;
        this.salary = salary;
        validationTeacherFields();
    }

    private void validationTeacherFields() {
        validationSubject();
        validationSalary();
    }

    private void validationSubject() {
        if (subject == null || subject.trim().isEmpty()) {
            throw new ValidationException("Subject", subject, "Cannot be empty.");
        }
        if (subject.trim().length() > 100) {
            throw new ValidationException("Subject", subject, "Exceeds maximum length of 100 characters.");
        }
        this.subject = subject.trim();
    }

    private void validationSalary() {
        if (salary < 0) {
            throw new ValidationException("Salary", salary, "Cannot be negative.");
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Teacher)) {
            return false;
        }
        Teacher t = (Teacher) o;
        return getId() == t.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public String getSubject() {return subject;}

    public double getSalary() {return salary;}

    public void setSubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) {
            throw new ValidationException("Subject", subject, "Cannot be empty.");
        }
        if (subject.trim().length() > 100) {
            throw new ValidationException("Subject", subject, "Exceeds maximum of 100 characters.");
        }
        this.subject = subject.trim();
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new ValidationException("Salary", salary, "Cannot be negative.");
        }
        this.salary = salary;
    }

    @Override
    public String getInfo() {
        return "Teacher ["
                + "\n  ID      = " + getId()
                + "\n  Name    = " + getName()
                + "\n  Email   = " + getEmail()
                + "\n  Subject = " + subject
                + "\n  Salary  = " + salary
                + "\n]";
    }

    @Override
    public String toString() {
        return getInfo();
    }

}
