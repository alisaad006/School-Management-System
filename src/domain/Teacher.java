package domain;

public class Teacher extends Person {

    private String subject;
    private double salary;

    public Teacher(int id, String name, String email, String subject, double salary) {
        super(id, name, email);
        this.subject = subject;
        this.salary = salary;
    }

    @Override
    public String getInfo() {
        return "Teacher ID: " + getId()
                + "\nName: " + getName()
                + "\nEmail: " + getEmail()
                + "\nSubject: " + subject;
    }

    public String getSubject() {
        return subject;
    }

    public double getSalary() {
        return salary;
    }

}
