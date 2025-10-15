package pl.polsl.maj.exceptions;

/**
 * Exception type used for matrix-related errors such as invalid indices,
 * dimension mismatches or non-invertible matrices.
 * 
 * @author piotr.maj
 * @version 1.0.0
 */
public class MatrixException extends Exception {
    /** Optional numeric code associated with the exception. */
    private int exceptionNumber;

    /**
     * Create MatrixException with error message.
     *
     * @param message descriptive error message
     */
    public MatrixException(String message) {
        super(message);
    }

    /**
     * Create MatrixException with message and numeric code.
     *
     * @param message descriptive message
     * @param exceptionNumber optional numeric code
     */
    public MatrixException(String message, int exceptionNumber) {
        super(message);
        this.exceptionNumber = exceptionNumber;
    }

    /**
     * Get optional exception number.
     *
     * @return exception number or 0 when not set
     */
    public int getExceptionNumber() {
        return exceptionNumber;
    }
        
}