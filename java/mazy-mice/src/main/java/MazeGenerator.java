import java.util.*;

public class MazeGenerator {
    private static final char WALL_VERTICAL = '│';
    private static final char WALL_HORIZONTAL = '─';
    private static final char WALL_CORNER_TOP_LEFT = '┌';
    private static final char WALL_CORNER_TOP_RIGHT = '┐';
    private static final char WALL_CORNER_BOTTOM_LEFT = '└';
    private static final char WALL_CORNER_BOTTOM_RIGHT = '┘';
    private static final char WALL_T_RIGHT = '├';
    private static final char WALL_T_LEFT = '┤';
    private static final char WALL_T_UP = '┴';
    private static final char WALL_T_DOWN = '┬';
    private static final char WALL_CROSS = '┼';
    private static final char EMPTY = ' ';
    private static final char ARROW = '⇨';

    private static final int[][] DIRECTIONS = {
            {-1, 0}, // North
            {0, 1},  // East
            {1, 0},  // South
            {0, -1}  // West
    };


    public char[][] generatePerfectMaze(int rows, int columns) {
        return generatePerfectMaze(rows, columns, new Random().nextInt(1000000));
    }


    public char[][] generatePerfectMaze(int rows, int columns, int seed) {
        // Validate input parameters
        if (rows < 5 || rows > 100 || columns < 5 || columns > 100) {
            throw new IllegalArgumentException("Maze dimensions must be between 5 and 100 cells.");
        }

        Random random = new Random(seed);

        boolean[][] visited = new boolean[rows][columns];

        boolean[][][] walls = new boolean[rows][columns][4];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                for (int d = 0; d < 4; d++) {
                    walls[r][c][d] = true;
                }
            }
        }

        int entranceRow = random.nextInt(rows);
        int exitRow = random.nextInt(rows);

        walls[entranceRow][0][3] = false;

        walls[exitRow][columns - 1][1] = false;

        generateMaze(entranceRow, 0, visited, walls, random);

        return renderMaze(rows, columns, walls, entranceRow, exitRow);
    }

    private void generateMaze(int row, int col, boolean[][] visited, boolean[][][] walls, Random random) {
        visited[row][col] = true;

        List<Integer> directions = new ArrayList<>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(directions, random);

        for (int dirIndex : directions) {
            int newRow = row + DIRECTIONS[dirIndex][0];
            int newCol = col + DIRECTIONS[dirIndex][1];

            if (newRow >= 0 && newRow < visited.length &&
                    newCol >= 0 && newCol < visited[0].length &&
                    !visited[newRow][newCol]) {

                walls[row][col][dirIndex] = false;

                int oppositeDir = (dirIndex + 2) % 4;
                walls[newRow][newCol][oppositeDir] = false;

                generateMaze(newRow, newCol, visited, walls, random);
            }
        }
    }

    private char[][] renderMaze(int rows, int columns, boolean[][][] walls, int entranceRow, int exitRow) {
        int charRows = 2 * rows + 1;
        int charCols = 2 * columns + 1;
        char[][] maze = new char[charRows][charCols];

        for (int r = 0; r < charRows; r++) {
            for (int c = 0; c < charCols; c++) {
                maze[r][c] = EMPTY;
            }
        }

        for (int r = 0; r < charRows; r += 2) {
            for (int c = 0; c < charCols; c += 2) {
                if (r == 0 && c == 0) {
                    maze[r][c] = WALL_CORNER_TOP_LEFT;
                } else if (r == 0 && c == charCols - 1) {
                    maze[r][c] = WALL_CORNER_TOP_RIGHT;
                } else if (r == charRows - 1 && c == 0) {
                    maze[r][c] = WALL_CORNER_BOTTOM_LEFT;
                } else if (r == charRows - 1 && c == charCols - 1) {
                    maze[r][c] = WALL_CORNER_BOTTOM_RIGHT;
                } else if (r == 0 || r == charRows - 1) {
                    maze[r][c] = WALL_T_DOWN;
                } else if (c == 0 || c == charCols - 1) {
                    maze[r][c] = WALL_T_RIGHT;
                } else {
                    maze[r][c] = WALL_CROSS;
                }
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                // North wall
                if (walls[r][c][0]) {
                    for (int i = 1; i < 2; i++) {
                        maze[2 * r][2 * c + i] = WALL_HORIZONTAL;
                    }
                }
                if (walls[r][c][2]) {
                    for (int i = 1; i < 2; i++) {
                        maze[2 * r + 2][2 * c + i] = WALL_HORIZONTAL;
                    }
                }
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                // West wall
                if (walls[r][c][3]) {
                    for (int i = 1; i < 2; i++) {
                        maze[2 * r + i][2 * c] = WALL_VERTICAL;
                    }
                }
                if (walls[r][c][1]) {
                    for (int i = 1; i < 2; i++) {
                        maze[2 * r + i][2 * c + 2] = WALL_VERTICAL;
                    }
                }
            }
        }

        for (int r = 0; r < charRows; r += 2) {
            for (int c = 0; c < charCols; c += 2) {
                if (r > 0 && r < charRows - 1 && c > 0 && c < charCols - 1) {
                    boolean north = maze[r - 1][c] == WALL_VERTICAL;
                    boolean south = (r < charRows - 1 && maze[r + 1][c] == WALL_VERTICAL);
                    boolean east = (c < charCols - 1 && maze[r][c + 1] == WALL_HORIZONTAL);
                    boolean west = maze[r][c - 1] == WALL_HORIZONTAL;

                    if (north && south && east && west) {
                        maze[r][c] = WALL_CROSS;
                    } else if (north && south && east) {
                        maze[r][c] = WALL_T_RIGHT;
                    } else if (north && south && west) {
                        maze[r][c] = WALL_T_LEFT;
                    } else if (north && east && west) {
                        maze[r][c] = WALL_T_UP;
                    } else if (south && east && west) {
                        maze[r][c] = WALL_T_DOWN;
                    } else if (north && south) {
                        maze[r][c] = WALL_VERTICAL;
                    } else if (east && west) {
                        maze[r][c] = WALL_HORIZONTAL;
                    } else if (north && east) {
                        maze[r][c] = WALL_CORNER_BOTTOM_LEFT;
                    } else if (north && west) {
                        maze[r][c] = WALL_CORNER_BOTTOM_RIGHT;
                    } else if (south && east) {
                        maze[r][c] = WALL_CORNER_TOP_LEFT;
                    } else if (south && west) {
                        maze[r][c] = WALL_CORNER_TOP_RIGHT;
                    }
                }
            }
        }

        maze[2 * entranceRow + 1][0] = ARROW;
        maze[2 * exitRow + 1][charCols - 1] = ARROW;

        return maze;
    }
}