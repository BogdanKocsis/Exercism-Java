import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
class WordSearcher {
    // Direction vectors for all 8 directions
    private static final int[][] DIRECTIONS = {
            {0, 1},   // right
            {1, 0},   // down
            {0, -1},  // left
            {-1, 0},  // up
            {1, 1},   // down-right
            {1, -1},  // down-left
            {-1, 1},  // up-right
            {-1, -1}  // up-left
    };
    Map<String, Optional<WordLocation>> search(final Set<String> words, final char[][] grid) {
        Map<String, Optional<WordLocation>> result = new HashMap<>();

        for (String word : words) {
            result.put(word, Optional.empty());
        }

        if (grid.length == 0 || grid[0].length == 0) {
            return result;
        }

        int rows = grid.length;
        int cols = grid[0].length;

        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }

            outerLoop:
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (grid[row][col] == word.charAt(0)) {
                        for (int[] direction : DIRECTIONS) {
                            int dx = direction[0];
                            int dy = direction[1];

                            if (wordFitsInDirection(word, grid, row, col, dx, dy, rows, cols)) {
                                int endRow = row + dx * (word.length() - 1);
                                int endCol = col + dy * (word.length() - 1);

                                result.put(word, Optional.of(new WordLocation(
                                        new Pair(col + 1, row + 1),
                                        new Pair(endCol + 1, endRow + 1)
                                )));

                                break outerLoop;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    /**
     * Checks if the word fits from the starting position in the given direction
     */
    private boolean wordFitsInDirection(String word, char[][] grid, int startRow, int startCol,
                                        int dx, int dy, int rows, int cols) {
        int length = word.length();

        if (startRow + dx * (length - 1) < 0 || startRow + dx * (length - 1) >= rows ||
                startCol + dy * (length - 1) < 0 || startCol + dy * (length - 1) >= cols) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            int currentRow = startRow + dx * i;
            int currentCol = startCol + dy * i;

            if (grid[currentRow][currentCol] != word.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}