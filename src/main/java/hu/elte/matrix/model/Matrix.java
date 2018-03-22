package hu.elte.matrix.model;

import hu.elte.matrix.exception.DimensionException;
import hu.elte.matrix.exception.InverseException;
import hu.elte.matrix.utils.GaussJordanElimination;

import java.util.Arrays;

public class Matrix {

    protected int row;
    protected int col;
    protected double[][] matrix;

    public Matrix() {
    }

    public Matrix(int row, int col) {
        this.row = row;
        this.col = col;
        this.matrix = new double[row][col];
    }

    public Matrix(double[][] matrix) {
        this.row = matrix.length;
        this.col = matrix[0].length;
        this.matrix = matrix;
    }

    public Matrix copy() {
        Matrix copy = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            System.arraycopy(this.matrix[i], 0, copy.matrix[i], 0, this.matrix[i].length);
        }

        return copy;
    }

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

    public Matrix subtract(Matrix other) throws DimensionException {
        if (this.row != other.row || this.col != other.col) {
            throw new DimensionException("man, u suck");
        }

        Matrix result = new Matrix(this.row, this.col);

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] - other.matrix[i][j];
            }
        }

        return result;
    }

    public Matrix multiply(double constant) {
        if (constant == 1) {
            return this.copy();
        }

        Matrix result = new Matrix(this.row, this.col);

        if (constant == 0) {
            return result;
        }

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                result.matrix[i][j] = this.matrix[i][j] * constant;
            }
        }

        return result;
    }

    public Matrix getInverse() throws DimensionException, InverseException {
        if (this.row != this.col) throw new DimensionException();

        return GaussJordanElimination.calculateInverse(this);
    }

    public double getDeterminant() throws DimensionException {
        if (this.row != this.col) throw new DimensionException();

        if (this.row == 2) {
            return this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0];
        } else {
            return GaussJordanElimination.calculateDeterminant(this);
        }
    }

    public int getRank() {
        return GaussJordanElimination.calculateRank(this);
    }

    // Formatted table output
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

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix other = (Matrix) o;

        if (row != other.row) return false;
        if (col != other.col) return false;
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
