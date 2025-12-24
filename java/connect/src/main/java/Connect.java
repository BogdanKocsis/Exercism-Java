import java.util.*;

class Connect {
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    private final List<char[]> board;
    private final int rows;
    private final int cols;

    private static final int[][] NEIGHBORS = {
            {0, -1}, {0, 1},           // Horizontal
            {-1, 0}, {-1, 1},           // Top diagonals
            {1, 0}, {1, -1}             // Bottom diagonals
    };

    public Connect(String[] rawBoard) {
        this.board = parseBoard(rawBoard);
        this.rows = board.size();
        this.cols = board.getFirst().length;
    }

    public Winner computeWinner() {
        return checkXWinner() ? Winner.PLAYER_X :
                checkOWinner() ? Winner.PLAYER_O :
                        Winner.NONE;
    }

    private boolean checkXWinner() {
        for (int r = 0; r < rows; r++) {
            if (board.get(r)[0] == PLAYER_X &&
                    traversePath(r, 0, PLAYER_X, new boolean[rows][cols], true)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkOWinner() {
        for (int c = 0; c < cols; c++) {
            if (board.getFirst()[c] == PLAYER_O &&
                    traversePath(0, c, PLAYER_O, new boolean[rows][cols], false)) {
                return true;
            }
        }
        return false;
    }

    private boolean traversePath(int startRow, int startCol, char player,
                                 boolean[][] visited, boolean isXPlayer) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];

            if (isWinningPosition(row, col, isXPlayer)) {
                return true;
            }

            for (int[] dir : NEIGHBORS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];

                if (isValidMove(newRow, newCol, player, visited)) {
                    queue.offer(new int[]{newRow, newCol});
                    visited[newRow][newCol] = true;
                }
            }
        }
        return false;
    }

    private boolean isValidMove(int row, int col, char player, boolean[][] visited) {
        return row >= 0 && row < rows &&
                col >= 0 && col < cols &&
                !visited[row][col] &&
                board.get(row)[col] == player;
    }

    private boolean isWinningPosition(int row, int col, boolean isXPlayer) {
        return isXPlayer ? col == cols - 1 : row == rows - 1;
    }

    private static List<char[]> parseBoard(String[] rawBoard) {
        List<char[]> parsedBoard = new ArrayList<>();
        for (String line : rawBoard) {
            String filtered = line.replaceAll("[^OX.]", "");
            if (!filtered.isEmpty()) {
                parsedBoard.add(filtered.toCharArray());
            }
        }
        return parsedBoard;
    }
}