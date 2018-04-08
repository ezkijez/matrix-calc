package hu.elte.matrix.model;

public class IdentityMatrix extends Matrix {

    /**
     * Constructs a new identity matrix of size {@code n}.
     * The constructed matrix has n rows and n columns.
     *
     * @param n the size of the identity matrix
     */
    public IdentityMatrix(int n) {
        super(n, n);

        for (int i = 0; i < n; i++) {
            this.matrix[i][i] = 1;
        }
    }
}
