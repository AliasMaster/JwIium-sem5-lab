package pl.polsl.maj.view;

/**
 * Enumeration mapping user-visible operations to the menu option codes
 * understood by the controller.
 * @author piotr.maj
 * @version 1.0.0
 */
public enum OperationCode {
    /** Determinant computation operation. */
    DETERMINANT("1"),
    /** Scalar multiplication operation. */
    SCALAR("2"),
    /** Matrix multiplication operation. */
    MULTIPLY("3"),
    /** Matrix addition operation. */
    ADD("4"),
    /** Matrix subtraction operation. */
    SUBSTRACT("5"),
    /** Matrix transpose operation. */
    TRANSPOSE("6"),
    /** Matrix inversion operation. */
    INVERSE("7"),
    /** Matrix trace computation operation. */
    TRACE("8"),
    /** Exit application operation. */
    EXIT("0");

    private final String code;

    /**
     * Create a new operation code with the given string code.
     *
     * @param code the string representation of this operation
     */
    OperationCode(String code) {
        this.code = code;
    }

    /**
     * Get the string code for this operation.
     *
     * @return the operation code as a string
     */
    public String code() { return code; }
}
