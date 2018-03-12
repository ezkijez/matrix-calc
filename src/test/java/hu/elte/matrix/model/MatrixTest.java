package hu.elte.matrix.model;

import hu.elte.matrix.exception.DimensionException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

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

    @Test
    public void addTestDimensionException() {
        Matrix matrix1 = new Matrix(2, 2);
        Matrix matrix2 = new Matrix(2, 3);

        try {
            matrix1.add(matrix2);
            Assert.fail("DimensionException should've been thrown.");
        } catch (DimensionException e) {}
    }

    @Test
    public void multiplyByOne() {
        double[][] values = {{1, 1, 1}, {1, 1, 1}};

        Matrix original = new Matrix(values);
        Matrix result = original.multiply(1);

        Assert.assertTrue(original != result);
        Assert.assertTrue(original.equals(result));
    }
}
