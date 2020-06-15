package exceptions;

public class MyException extends RuntimeException{
    public MyException(String errorMessage) {
        super(errorMessage);
    }
}