package hu.elte.matrix.model;

import hu.elte.matrix.exception.DimensionException;
import hu.elte.matrix.exception.InverseException;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

    @Test
    public void equalsFalseTest() {
        double[][] values1 = {{1, 2, 3}};
        double[][] values2 = {{1, 2, 4}};

        Matrix matrix1 = new Matrix(values1);
        Matrix matrix2 = new Matrix(values2);

        Assert.assertFalse(matrix1.equals(matrix2));
    }

    @Test
    public void equalsTrueTest() {
        double[][] values1 = {{1, 2, 3}};
        double[][] values2 = {{1, 2, 3}};

        Matrix matrix1 = new Matrix(values1);
        Matrix matrix2 = new Matrix(values2);

        Assert.assertTrue(matrix1.equals(matrix2));
    }

    @Test
    public void copyTestByChanging() {
        double[][] values = {{1, 1, 1}, {1, 1, 1}};

        Matrix original = new Matrix(values);
        Matrix copy = original.copy();

        copy.getMatrix()[0][0] = 0;

        Assert.assertTrue(original.getMatrix()[0][0] == 1);
    }

    @Test
    public void copyTestByComparison() {
        double[][] values = {{1, 1, 1}};

        Matrix original = new Matrix(values);
        Matrix copy = original.copy();

        Assert.assertTrue(original != copy);
        Assert.assertTrue(original.equals(copy));
        Assert.assertTrue(original.getMatrix() != copy.getMatrix());
        Assert.assertTrue(Arrays.deepEquals(original.getMatrix(), copy.getMatrix()));
    }

    @Test
    public void addTest() throws DimensionException {
        double[][] values = {{1, 1}};
        Matrix matrix1 = new Matrix(values);
        Matrix matrix2 = matrix1.copy();

        double[][] result = {{2, 2}};
        Matrix expected = new Matrix(result);

        Assert.assertEquals(expected, matrix1.add(matrix2));
    }

    @Test(expected = DimensionException.class)
    public void addTestDimensionException() throws DimensionException {
        Matrix matrix1 = new Matrix(2, 2);
        Matrix matrix2 = new Matrix(2, 3);

        matrix1.add(matrix2);
    }

    @Test
    public void multiplyByOne() {
        double[][] values = {{1, 1, 1}, {1, 1, 1}};

        Matrix original = new Matrix(values);
        Matrix result = original.multiply(1);

        Assert.assertTrue(original != result);
        Assert.assertTrue(original.equals(result));
    }

    @Test
    public void subtractTest() throws DimensionException {
        double[][] values = {{1, 1}};
        Matrix matrix1 = new Matrix(values);
        Matrix matrix2 = matrix1.copy();

        double[][] result = {{0, 0}};
        Matrix expected = new Matrix(result);

        Assert.assertEquals(expected, matrix1.subtract(matrix2));
    }

    @Test(expected = DimensionException.class)
    public void subtractDimensionExceptionTest() throws DimensionException {
        Matrix matrix1 = new Matrix(3, 3);
        Matrix matrix2 = new Matrix(2, 3);

        matrix1.subtract(matrix2);
    }

    @Test
    public void multiplyTest() {
        double[][] values = {{2, 2}};
        Matrix matrix1 = new Matrix(values);

        double[][] result = {{4, 4}};
        Matrix expected = new Matrix(result);

        double constant = 2;

        Assert.assertEquals(expected, matrix1.multiply(constant));
    }

    @Test
    public void multiplyTestConstantIsNull() {
        double[][] values = {{2, 2}};
        Matrix matrix1 = new Matrix(values);

        double constatnt = 0;

        double[][] result = {{0, 0}};
        Matrix expected = new Matrix(result);

        Assert.assertEquals(expected, matrix1.multiply(constatnt));
    }

    @Test
    public void multiplyTestConstantIsMinus() {
        double[][] values = {{2, 2}};
        Matrix matrix1 = new Matrix(values);

        double constatnt = -2;

        double[][] result = {{-4, -4}};
        Matrix expected = new Matrix(result);

        Assert.assertEquals(expected, matrix1.multiply(constatnt));
    }

    @Test
    public void multiplyMatrixTest() throws DimensionException {
        double[][] values = {
                {2, 2},
                {2, 2}
        };
        Matrix matrix1 = new Matrix(values);

        double[][] values2 = {
                {2, 2},
                {2, 2}
        };
        Matrix matrix2 = new Matrix(values);

        double[][] result = {
                {8, 8},
                {8, 8}
        };
        Matrix expected = new Matrix(result);

        Assert.assertEquals(expected, matrix1.multiply(matrix2));
    }

    @Test
    public void multiplyMatrixTest2() throws DimensionException {
        double[][] values = {
                {1, 0, 2},
                {-1, 3, 1}
        };
        Matrix matrix1 = new Matrix(values);

        double[][] values2 = {
                {3, 1},
                {2, 1},
                {1, 0}
        };
        Matrix matrix2 = new Matrix(values2);

        double[][] result = {
                {5, 1},
                {4, 2}
        };
        Matrix expected = new Matrix(result);

        Assert.assertEquals(expected, matrix1.multiply(matrix2));
    }

    @Test
    public void multiplyMatrix2x2Test() throws DimensionException {
        double[][] values = {{2, 2}};
        Matrix matrix1 = new Matrix(values);

        double[][] values2 = {
                {2},
                {2}
        };
        Matrix matrix2 = new Matrix(values2);

        double[][] result = {{8}};
        Matrix expected = new Matrix(result);

        Assert.assertEquals(expected, matrix1.multiply(matrix2));
    }

    @Test(expected = DimensionException.class)
    public void multiplyMatrixExceptionTest() throws DimensionException {
        double[][] values = {
                {2, 2},
                {3, 4}
        };

        Matrix matrix1 = new Matrix(values);

        double[][] values2 = {
                {2, 2, 2},
                {5, 6, 4},
                {4, 5, 6}
        };
        Matrix matrix2 = new Matrix(values2);

        matrix1.multiply(matrix2);
    }

    @Test
    public void getDeterminantTest() throws DimensionException {
        double[][] values = {
                {3, 2, -4},
                {-2, -5, 1},
                {-8, 2, 1}
        };
        Matrix matrix1 = new Matrix(values);

        double determinant = 143;

        Assert.assertEquals(determinant, matrix1.getDeterminant(), 0);
    }

    @Test(expected = DimensionException.class)
    public void getDeterminantExceptionTest() throws DimensionException {
        double[][] values = {
                {3, 2, -4},
                {-2, -5, 1},
        };
        Matrix matrix1 = new Matrix(values);

        matrix1.getDeterminant();
    }

    @Test
    public void getDeterminant2RowTest() throws DimensionException {
        double[][] values = {
                {3, 2},
                {2, 3},
        };
        Matrix matrix1 = new Matrix(values);

        double determinant = 5;

        Assert.assertEquals(determinant, matrix1.getDeterminant(), 0);
    }

    @Test
    public void getRankTest() throws DimensionException {
        double[][] values = {
                {1, 2, 3},
                {0, 5, 4},
                {0, 10, 2}
        };
        Matrix matrix1 = new Matrix(values);

        int rank = 3;

        Assert.assertEquals(rank, matrix1.getRank());
    }

    @Test
    public void getInverseTest() throws DimensionException, InverseException {
        double[][] values = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 2, 9}
        };
        Matrix matrix1 = new Matrix(values);

        double[][] result = {
                {-0.9166666666666667, 0.3333333333333334, 0.08333333333333331},
                {-0.16666666666666677, 0.33333333333333337, -0.16666666666666666},
                {0.75, -0.33333333333333337, 0.08333333333333333}
        };
        Matrix expected = new Matrix(result);

        Assert.assertEquals(expected, matrix1.getInverse());
    }

    @Test(expected = DimensionException.class)
    public void getInverseExceptionTest() throws DimensionException, InverseException {
        double[][] values = {
                {1, 2, 3},
                {4, 5, 6},
        };
        Matrix matrix1 = new Matrix(values);

        matrix1.getInverse();
    }

    @Test
    public void constructorTest() {
        Matrix matrix = new Matrix(2, 3);
        Matrix matrix1 = new Matrix();
    }




}
