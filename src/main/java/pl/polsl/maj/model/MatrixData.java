package pl.polsl.maj.model;

import java.util.List;

/**
 * Immutable record representing matrix data in row-major order.
 *
 * @param rows number of rows
 * @param cols number of columns
 * @param data flat list of values in row-major order (size must be rows*cols)
 * 
 * @author piotr.maj
 * @version 1.0.0
 */
public record MatrixData(int rows, int cols, List<Double> data) {
}
