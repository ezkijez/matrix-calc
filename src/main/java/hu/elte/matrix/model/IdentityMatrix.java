package hu.elte.matrix.model;

public class IdentityMatrix extends Matrix {

    public IdentityMatrix(int row, int col) {
        super(row, col);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i == j) {
                    this.matrix[i][j] = 1;
                } else {
                    this.matrix[i][j] = 0;
                }
            }
        }
    }
}
