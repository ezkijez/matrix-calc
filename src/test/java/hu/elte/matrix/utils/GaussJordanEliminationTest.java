package hu.elte.matrix.utils;

import hu.elte.matrix.model.Matrix;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class GaussJordanEliminationTest {


    @Test
    public void calculateInverseTest1() {
        double[][] values = {{-2, 3}, {-3, 4}};
        Matrix matrix1 = new Matrix(values);

        double[][] result = {{4, -3}, {3, -2}};
        Matrix expected = new Matrix(result);
        System.out.println("Helyes eredmény: " + expected);
        System.out.println("Számolt mátrix: " + GaussJordanElimination.calculateInverse(matrix1));
        assertEquals(expected, GaussJordanElimination.calculateInverse(matrix1));
    }

    @Test
    public void calculateInverseTest2() {
        double[][] values = {{1, -1, 3}, {-3, 2, -3}, {-2, 1, -1}};
        Matrix matrix1 = new Matrix(values);

        double[][] result = {{1, 2, -3}, {3, 5, -6}, {1, 1, -1}};
        Matrix expected = new Matrix(result);
        System.out.println("Helyes eredmény: " + expected);
        System.out.println("Számolt mátrix: " + GaussJordanElimination.calculateInverse(matrix1));
        assertEquals(expected, GaussJordanElimination.calculateInverse(matrix1));
    }

    @Test
    public void calculateInverseTest3() {
        double[][] values = {{1, 2, 3}, {4, 5, 6}, {7, 2, 9}};
        Matrix matrix1 = new Matrix(values);

        double[][] result = {{-0.916666666667,0.3333333333, 0.0833333}, {-0.1666666666666, 0.333333333333, -0.16666666666}, {0.75, -0.333333333, 0.0833333333333}};
        Matrix expected = new Matrix(result);
        System.out.println("Helyes eredmény: " + expected);
        System.out.println("Számolt mátrix: " + GaussJordanElimination.calculateInverse(matrix1));
        assertEquals(expected, GaussJordanElimination.calculateInverse(matrix1));
    }

    @Test
    public void calculateInverseTestDeterminantIsNull() {
        double[][] values = {{2, 2}, {2, 2}};
        Matrix matrix1 = new Matrix(values);

        System.out.println("Számolt mátrix: " + GaussJordanElimination.calculateInverse(matrix1));

    }

    @Test
    public void calculateDeterminant()  {
        double[][] values = {{2, 1}, {3, 4}};
        Matrix matrix1 = new Matrix(values);

        double determinant = 5;

        assertEquals(determinant, GaussJordanElimination.calculateDeterminant(matrix1), 0.0000000001);

    }

    @Test
    public void calculateDeterminant3x3()  {
        double[][] values = {{2, 3, 4}, {2, 3, 4}, {2,3, 4}};
        Matrix matrix1 = new Matrix(values);

        double determinant = 0;

        assertEquals(determinant, GaussJordanElimination.calculateDeterminant(matrix1), 0.0000000001);
    }

    @Test
    public void calculateDeterminantIsMinus()  {
        double[][] values = {{3, 8}, {4, 6}};
        Matrix matrix1 = new Matrix(values);

        double determinant = -14;

        assertEquals(determinant, GaussJordanElimination.calculateDeterminant(matrix1), 0.0000000001);

    }

    @Test
    public void calculateDeterminantBigNumber()  {
        double[][] values = {{2,3,2,3,2,3,2,3,2,3}, {2,3,2,3,2,3,2,3,2,3}, {2,3,2,3,2,3,2,3,2,3}, {2,3,2,3,2,3,2,3,2,3},
                {2,3,2,3,2,3,2,3,2,3}, {2,3,2,3,2,3,2,3,2,3}, {2,3,2,3,2,3,2,3,2,3}, {2,3,2,3,2,3,2,3,2,3}, {2,3,2,3,2,3,2,3,2,3},
                {2,3,2,3,2,3,2,3,2,3}};
        Matrix matrix1 = new Matrix(values);

        double determinant = 0;

        assertEquals(determinant, GaussJordanElimination.calculateDeterminant(matrix1), 0.0000000001);
    }






}