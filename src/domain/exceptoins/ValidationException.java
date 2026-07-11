package domain.exceptoins;

public class ValidationException extends RuntimeException{
    
    public ValidationException(String message) {
        super(message);
    }
 
    public ValidationException(String fieldName, Object value, String reason) {
        super("Validation failed for [" + fieldName + "] with value [" + value + "]: " + reason);
    }
    
}
