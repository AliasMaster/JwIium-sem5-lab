package pl.polsl.maj.exceptions;

public class MatrixException extends Exception {
    private int exceptionNumber;

    public MatrixException(String message) {
        super(message);
    }

    public MatrixException(String message, int exceptionNumber) {
        super(message);
        this.exceptionNumber = exceptionNumber;
    }

    public int getExceptionNumber() {
        return exceptionNumber;
    }
        
}