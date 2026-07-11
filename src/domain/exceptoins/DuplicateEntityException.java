package domain.exceptoins;

public class DuplicateEntityException extends RuntimeException {
    
    
    public DuplicateEntityException(String entityName, int id) {
        super(entityName + " with ID " + id + " already exists.");
    }
}
