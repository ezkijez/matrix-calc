package hu.elte.matrix.model;

import org.junit.Assert;
import org.junit.Test;

public class IdentityMatrixTest {

    @Test
    public void constructIdentityTest() {
        double[][] expectedValues = {
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
        };
        Matrix expected = new Matrix(expectedValues);

        Matrix actual = new IdentityMatrix(3);

        Assert.assertArrayEquals(expected.getMatrix(), actual.getMatrix());
    }
}