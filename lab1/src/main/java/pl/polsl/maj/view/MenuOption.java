package pl.polsl.maj.view;

/**
 * Enumeration of supported menu options for the application.
 * 
 * @author piotr.maj
 * @version 1.0.0
 */
public enum MenuOption {
    /** Calculate determinant of the current matrix. */
    Determinant,

    /** Multiply the current matrix by a scalar value. */
    MultiplyByScalar,

    /** Add another matrix to the current matrix. */
    AddMatrix,

    /** Subtract another matrix from the current matrix. */
    SubractMatrix,

    /** Multiply the current matrix by another matrix. */
    Multiply,

    /** Transpose the current matrix. */
    Transpose,

    /** Invert the current matrix (if invertible). */
    Inverse,

    /** Compute the trace (sum of diagonal) of the current matrix. */
    Trace,

    /** Exit the program. */
    End,

    /** Invalid or unrecognized option. */
    Invalid,
}
