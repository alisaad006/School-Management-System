package domain;

import domain.exceptoins.ValidationException;

public abstract class Person {

    // ── Regex ────────────────────────────────────────────────
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final int MAX_NAME_LENGTH = 100;
    private static final int MAX_EMAIL_LENGTH = 100;

    private int id;
    private String name;
    private String email;

    public Person() {
    }

    public Person(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        validate();
    }

    protected void validate() {
        validateId();
        validateName();
        validateEmail();
    }

    private void validateId() {
        if (id <= 0) {
            throw new ValidationException("ID", id, "Must be greater than 0.");
        }
    }

    private void validateName() {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Name", name, "Cannot be empty.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new ValidationException("Name", name,
                "Exceeds maximum length of " + MAX_NAME_LENGTH + " characters.");
        }
    }

    private void validateEmail() {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email", email, "Cannot be empty.");
        }
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new ValidationException("Email", email,
                "Exceeds maximum length of " + MAX_EMAIL_LENGTH + " characters.");
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new ValidationException("Email", email,
                "Invalid format. Example: user@gmail.com");
        }
    }

    public abstract String getInfo();

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

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    

}
