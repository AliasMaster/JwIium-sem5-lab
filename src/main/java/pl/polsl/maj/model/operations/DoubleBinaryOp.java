package pl.polsl.maj.model.operations;

/**
 * Functional interface representing a binary operation on two doubles.
 * <p>
 * Used to encapsulate operations like addition, subtraction, multiplication, etc.,
 * that take two double operands and produce a double result. This interface enables
 * lambda-based implementations of mathematical operations.
 * </p>
 */
@FunctionalInterface
public interface DoubleBinaryOp {
    /**
     * Apply the binary operation to two double operands.
     *
     * @param a first operand
     * @param b second operand
     * @return result of applying the operation to a and b
     */
    double apply(double a, double b);
}
