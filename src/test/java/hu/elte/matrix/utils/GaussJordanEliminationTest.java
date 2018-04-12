package hu.elte.matrix.utils;

import static org.junit.Assert.assertEquals;

import hu.elte.matrix.exception.InverseException;
import hu.elte.matrix.model.Matrix;
import org.junit.Test;

public class GaussJordanEliminationTest {

    @Test
    public void calculateInverseTest1() throws InverseException {
        double[][] values = {{-2, 3}, {-3, 4}};
        Matrix matrix = new Matrix(values);

        double[][] result = {{4, -3}, {3, -2}};
        Matrix expected = new Matrix(result);
        assertEquals(expected, GaussJordanElimination.calculateInverse(matrix).setPrecision(3));
    }

    @Test
    public void calculateInverseTest2() throws InverseException {
        double[][] values = {{1, -1, 3}, {-3, 2, -3}, {-2, 1, -1}};
        Matrix matrix = new Matrix(values);

        double[][] result = {{1, 2, -3}, {3, 5, -6}, {1, 1, -1}};
        Matrix expected = new Matrix(result);
        assertEquals(expected, GaussJordanElimination.calculateInverse(matrix).setPrecision(3));
    }

    @Test
    public void calculateInverseTest3() throws InverseException {
        double[][] values = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 2, 9}
        };
        Matrix matrix = new Matrix(values);

        double[][] result = {
            {-0.92, 0.33, 0.08},
            {-0.17, 0.33, -0.17},
            {0.75, -0.33, 0.08}
        };
        Matrix expected = new Matrix(result);
        assertEquals(expected, GaussJordanElimination.calculateInverse(matrix).setPrecision(2));
    }

    @Test(expected = InverseException.class)
    public void calculateInverseTestDeterminantIsNull() throws InverseException {
        double[][] values = {{2, 2}, {2, 2}};
        Matrix matrix = new Matrix(values);

        System.out.println("Számolt mátrix: " + GaussJordanElimination.calculateInverse(matrix));
    }

    @Test
    public void calculateDeterminant()  {
        double[][] values = {{2, 1}, {3, 4}};
        Matrix matrix = new Matrix(values);

        double determinant = 5;

        assertEquals(determinant, GaussJordanElimination.calculateDeterminant(matrix),
            0.0000000001);
    }

    @Test
    public void calculateDeterminant3x3()  {
        double[][] values = {{2, 3, 4}, {2, 3, 4}, {2,3, 4}};
        Matrix matrix = new Matrix(values);

        double determinant = 0;

        assertEquals(determinant, GaussJordanElimination.calculateDeterminant(matrix),
            0.0000000001);
    }

    @Test
    public void calculateDeterminantIsMinus()  {
        double[][] values = {{3, 8}, {4, 6}};
        Matrix matrix = new Matrix(values);

        double determinant = -14;

        assertEquals(determinant, GaussJordanElimination.calculateDeterminant(matrix),
            0.0000000001);
    }

    @Test
    public void calculateDeterminantBigNumber()  {
        double[][] values = {
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3},
            {2, 3, 2, 3, 2, 3, 2, 3, 2, 3}
        };
        Matrix matrix = new Matrix(values);

        double determinant = 0;

        assertEquals(determinant, GaussJordanElimination.calculateDeterminant(matrix),
            0.0000000001);
    }

    @Test
    public void calculateRankTest() {
        double[][] values = {
                {1, 2, 3},
                {0, 5, 4},
                {0, 10, 2}
        };
        Matrix matrix = new Matrix(values);

        double rank = 3;

        assertEquals(rank, GaussJordanElimination.calculateRank(matrix),
                0.0000000001);
    }





}