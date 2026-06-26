package domain;

public abstract class Person {

    private int id;
    private String name;
    private String email;

    public Person() {
    }

    public Person(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    protected void validdata() {

        // check ID
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be greater than 0");
        }

        // check name
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        // check email
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email format");
        }

    }

    public String getInfo() {
        return "ID: " + id
                + "\nName:" + name
                + "\nAge: " + email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

}
