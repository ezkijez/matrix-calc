package hu.elte.matrix.utils;

import hu.elte.matrix.exception.InverseException;
import hu.elte.matrix.model.IdentityMatrix;
import hu.elte.matrix.model.Matrix;

public class GaussJordanElimination {

    // Calculations presuppose NxN matrix
    public static Matrix calculateInverse(Matrix matrix) throws InverseException {
        IdentityMatrix identity = new IdentityMatrix(matrix.getRow());

        double[][] augmentedMatrix = buildAugmentedMatrix(matrix, identity);

        if (transformToRowEchelonForm(augmentedMatrix) == 0) {
            throw new InverseException();
        }

        transformToDiagonalForm(augmentedMatrix);

        return new Matrix(extractInverse(augmentedMatrix));
    }

    // Calculations presuppose NxN matrix
    public static double calculateDeterminant(Matrix matrix) {
        return transformToRowEchelonForm(matrix.copy().getMatrix());
    }

    // Matrix rank equals the number of linearly independent rows
    // TODO?: use singular value decomposition instead of Gauss elimination for better reliability
    public static int calculateRank(Matrix matrix) {
        double[][] temp = matrix.copy().getMatrix();

        transformToRowEchelonForm(temp);

        return measureDegreeOfLinearIndependence(temp);
    }

    private static double[][] buildAugmentedMatrix(Matrix a, Matrix b) {
        double[][] augmented = new double[a.getRow()][a.getCol() + b.getCol()];
        int m = a.getCol();

        for (int i = 0; i < augmented.length; i++) {
            for (int j = 0; j < augmented[0].length; j++) {
                if (j < m) {
                    augmented[i][j] = a.getMatrix()[i][j];
                } else {
                    augmented[i][j] = b.getMatrix()[i][j - m];
                }
            }
        }

        return augmented;
    }

    // Reduces input matrix to row echelon form and returns its determinant
    private static double transformToRowEchelonForm(double[][] a) {
        int numOfSwaps = 0;

        int m = a.length;
        int n = a[0].length;

        int h = 0;
        int k = 0;
        while (h < m && k < n) {
            // Using partial pivot for better stability
            int pivot = findPartialPivot(a, h, k);

            // If current pivot is unusable, step to next column
            if (a[pivot][k] == 0) {
                k++;
            } else {
                numOfSwaps += swapRows(a, h, pivot);

                for (int i = h + 1; i < m; i++) {
                    double f = a[i][k] / a[h][k];

                    // Set elements to zero under pivot
                    a[i][k] = 0;

                    // Calculate remaining elements in the row
                    for (int j = k + 1; j < n; j++) {
                        a[i][j] -= f * a[k][j];
                    }
                }

                h++;
                k++;
            }
        }

        // If determinant is zero,
        // inverse doesn't exist
        double determinant = 1;
        for (int i = 0; i < m; i++) {
            determinant *= a[i][i];
        }

        double sign = Math.pow(-1, numOfSwaps);

        return sign * determinant;
    }

    // TODO: combine diagonal division with reduction
    // TODO: improve performance
    private static void transformToDiagonalForm(double[][] a) {
        int m = a.length;
        int n = a[0].length;

        // Divide rows by the corresponding diagonal elements
        for (int i = 0; i < m; i++) {
            double d = a[i][i];
            for (int j = i; j < n; j++) {
                a[i][j] /= d;
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) {
                double f = a[i][j];

                a[i][j] = 0;

                for (int k = j + 1; k < n; k++) {
                    a[i][k] -= f * a[j][k];
                }
            }
        }
    }

    // Calculates the degree of linear independence from a row echelon form matrix
    // elements are rounded to 2 decimal places to resolve floating point error
    private static int measureDegreeOfLinearIndependence(double[][] a) {
        int m = a.length;
        int n = a[0].length;

        int degree = m;

        for (int i = 0; i < m; i++) {
            for (int j = i; j < n; j++) {
                double rounded = Math.round(a[i][j] * 100.0) / 100.0;

                if (rounded != 0) {
                    break;
                }

                degree--;
            }
        }

        return degree;
    }

    // Finds the maximum absolute value in the given column,
    // starting from the given row,
    // and returns its index number
    private static int findPartialPivot(double[][] a, int startingFromRow, int inCol) {
        int pivot = startingFromRow;
        double max = Math.abs(a[startingFromRow][inCol]);

        for (int i = startingFromRow; i < a.length; i++) {
            double absElement = Math.abs(a[i][inCol]);
            if (absElement > max) {
                max = absElement;
                pivot = i;
            }
        }

        return pivot;
    }

    // Swapping rows doesn't affect inverse calculation,
    // but it changes the sign of determinant
    private static int swapRows(double[][] a, int row1, int row2) {
        if (row1 != row2) {
            double[] temp = a[row1];
            a[row1] = a[row2];
            a[row2] = temp;

            return 1;
        }

        return 0;
    }

    // Extracts solution matrix from augmented form
    private static double[][] extractInverse(double[][] a) {
        int m = a.length;
        int n = a[0].length;

        double[][] solution = new double[m][m];

        for (int i = 0; i < m; i++) {
            for (int j = m; j < n; j++) {
                solution[i][j - m] = a[i][j];
            }
        }

        return solution;
    }
}
