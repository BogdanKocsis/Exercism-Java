
class Matrix {
    private final int[][] matrix;

    Matrix(String matrixAsString) {
        String[] rows = matrixAsString.split("\n");

        int numberOfRows = rows.length;
        int numberOfColumns = rows[0].split(" ").length;

        matrix = new int[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++) {
            String[] numbers = rows[i].split(" ");
            for (int j = 0; j < numberOfColumns; j++){
                matrix[i][j] = Integer.parseInt(numbers[j]);
            }
        }
    }

    int[] getRow(int rowNumber) {
        return matrix[rowNumber - 1].clone();
    }


    int[] getColumn(int columnNumber) {
        int[] column = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            column[i] = matrix[i][columnNumber - 1];
        }
        return column;
    }
}
