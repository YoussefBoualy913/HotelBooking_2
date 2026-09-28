package exception;

public class UserNotBalanceException extends RuntimeException {
    public UserNotBalanceException(String message) {
        super(message);
    }
}
