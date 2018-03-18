package hu.elte.matrix.model;

public class IdentityMatrix extends Matrix {

    public IdentityMatrix(int row, int col) {
        super(row, col);

        for (int i = 0; i < row; i++) {
            this.matrix[i][i] = 1;
        }
    }
}
