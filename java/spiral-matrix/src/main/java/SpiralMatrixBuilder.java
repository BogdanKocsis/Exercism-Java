class SpiralMatrixBuilder {
    int[][] buildMatrixOfSize(int size) {
        int[][] matrix = new int[size][size];
        int value = 1;
        int startRow = 0;
        int endRow = size - 1;
        int startCol = 0;
        int endCol = size - 1;

        while (startRow <= endRow && startCol <= endCol) {
            for (int i = startCol; i <= endCol; i++) {
                matrix[startRow][i] = value++;
            }
            startRow++;
            for (int i = startRow; i <= endRow; i++) {
                matrix[i][endCol] = value++;
            }
            endCol--;
            if (startRow <= endRow) {
                for (int i = endCol; i >= startCol; i--) {
                    matrix[endRow][i] = value++;
                }
                endRow--;
            }
            if (startCol <= endCol) {
                for (int i = endRow; i >= startRow; i--) {
                    matrix[i][startCol] = value++;
                }
                startCol++;
            }
        }
        return matrix;
    }
}
