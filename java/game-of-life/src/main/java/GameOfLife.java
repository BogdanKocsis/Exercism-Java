class GameOfLife {
    public int[][] tick(int[][] matrix) {
        int rows = matrix.length;
        if (rows == 0) {
            return new int[][]{};
        }
        int cols = matrix[0].length;

        int[][] nextGen = new int[rows][cols];
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                int sum = 0;
                for (int k = 0; k < dx.length; k++) {
                    int newX = i + dx[k];
                    int newY = j + dy[k];
                    if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                        sum += matrix[newX][newY];
                    }
                }
                nextGen[i][j] = (sum == 3 || (matrix[i][j] == 1 && sum == 2)) ? 1 : 0;
            }
        }
        return nextGen;
    }
}
