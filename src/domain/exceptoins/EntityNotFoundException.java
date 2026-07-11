package domain.exceptoins;

public class EntityNotFoundException extends RuntimeException{
    
    public EntityNotFoundException(String message) {
        super(message);
    }
 
    public EntityNotFoundException(String entityName, int id) {
        super(entityName + " with ID " + id + " was not found.");
    }
    
}
