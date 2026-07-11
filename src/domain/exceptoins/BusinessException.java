package domain.exceptoins;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
