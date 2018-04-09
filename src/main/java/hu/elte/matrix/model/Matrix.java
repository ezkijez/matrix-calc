package hu.elte.matrix.model;

import hu.elte.matrix.exception.DimensionException;
import hu.elte.matrix.exception.InverseException;
import hu.elte.matrix.utils.GaussJordanElimination;

import java.util.Arrays;

public class Matrix {

    private int row;
    private int col;
    protected double[][] matrix;

    /**
     * Constructs a new Matrix.
     */
    public Matrix() {
    }

    /**
     * Constructs a new Matrix with the given number of rows and columns.
     *
     * @param row the number of rows in the matrix
     * @param col the number of columns in the matrix
     */
    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        this.matrix = new double[row][col];
    }

    /**
     * Constructs a new Matrix with the given two-dimensional array.
     * The number of rows and columns of the matrix are determined from the two-dimensional array.
     *
     * @param matrix two-dimensional array with the values of the matrix
     */
    public Matrix(double[][] matrix) {
        this.row = matrix.length;
        this.col = matrix[0].length;
        this.matrix = matrix;
    }

    /**
     * Creates a deep copy of the matrix.
     * The two-dimensional array of the matrix is deep copied as well.
     *
     * @return the deep copied matrix.
     */
    public Matrix copy() {
        Matrix copy = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(this.matrix[i], 0, copy.matrix[i], 0, this.matrix[i].length);
        }

        return copy;
    }

    /**
     * Calculates the sum of two matrices.
     * The two matrices are {@code this} and the parameter {@code other}.
     *
     * @param other the other matrix
     * @return the sum of the two matrices
     * @throws DimensionException if the matrices dimension do not match
     */
    public Matrix add(Matrix other) throws DimensionException {
        if (this.row != other.row || this.col != other.col) {
            throw new DimensionException();
        }

        Matrix result = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] + other.matrix[i][j];
            }
        }

        return result;
    }

    /**
     * Calculates the difference of two matrices.
     * {@code this} is the minuend. {@code other} is the subtrahend.
     *
     * @param other the other matrix, which is the subtrahend
     * @return the difference of the two matrices
     * @throws DimensionException if the matrices dimension do not match
     */
    public Matrix subtract(Matrix other) throws DimensionException {
        if (this.row != other.row || this.col != other.col) {
            throw new DimensionException();
        }

        Matrix result = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }

        return result;
    }

    /**
     * Calculates the product of a matrix and a scalar.
     *
     * @param scalar the scalar multiplier
     * @return the product of the matrix and the scalar
     */
    public Matrix multiply(double scalar) {
        if (scalar == 1) {
            return this.copy();
        }

        Matrix result = new Matrix(this.row, this.col);

        if (scalar == 0) {
            return result;
        }

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] * scalar;
            }
        }

        return result;
    }

    /**
     * Calculates the product of two matrices.
     * The number of columns of the multiplicand and
     * the number of rows of the multiplier must match.
     * {@code this} is the multiplicand and {@code other} is the multiplier.
     *
     * @param other the multiplier matrix
     * @return the product of the two matrices
     * @throws DimensionException if the number of columns of the multiplicand and
     *      the number of rows of the multiplier do not match
     */
    public Matrix multiply(Matrix other) throws DimensionException {
        if (this.col != other.row) {
            throw new DimensionException();
        }
        
        Matrix result = new Matrix(this.row, other.col);

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < other.col; j++) {

                double sum = 0;
                for (int k = 0; k < this.col; k++) {
                    sum += this.matrix[i][k] * other.matrix[k][j];
                }

                result.matrix[i][j] = sum;
            }
        }

        return result;
    }

    /**
     * Calculates the inverse of the matrix.
     * The matrix has to be square.
     *
     * @return the inverse of the matrix
     * @throws DimensionException if the matrix is not square
     * @throws InverseException if the matrix inverse doesn't exist
     */
    public Matrix getInverse() throws DimensionException, InverseException {
        if (this.row != this.col) {
            throw new DimensionException();
        }

        return GaussJordanElimination.calculateInverse(this);
    }

    /**
     * Calculates the determinant of the matrix.
     * The matrix has to be square.
     *
     * @return the determinant of the matrix
     * @throws DimensionException if matrix is not square
     */
    public double getDeterminant() throws DimensionException {
        if (this.row != this.col) {
            throw new DimensionException();
        }

        if (this.row == 2) {
            return this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0];
        } else {
            return GaussJordanElimination.calculateDeterminant(this);
        }
    }

    /**
     * Calculates the rank of the matrix.
     *
     * @return the rank of the matrix
     */
    public int getRank() {
        return GaussJordanElimination.calculateRank(this);
    }

    /**
     * Prints the matrix in a formatted table format.
     * Uses {@code System.out}, serves debugging and development purposes.
     */
    public void printTable() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                System.out.printf("%8.3f ", this.matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    /**
     * Sets the two-dimensional array matrix member of the Matrix.
     * The number of rows and columns of the entity
     * are updated according to the {@code matrix}.
     *
     * @param matrix two-dimensional array with the values of the matrix
     */
    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
        this.row = matrix.length;
        this.col = matrix[0].length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Matrix other = (Matrix) o;

        if (row != other.row || col != other.col) {
            return false;
        }

        return Arrays.deepEquals(matrix, other.matrix);
    }

    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + col;
        result = 31 * result + Arrays.deepHashCode(matrix);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                sb.append(matrix[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
