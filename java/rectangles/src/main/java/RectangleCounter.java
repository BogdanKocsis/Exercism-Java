class RectangleCounter {

    int countRectangles(String[] grid) {
        if (grid.length <= 1) return 0;
        int count = 0;
        char[][] rects = new char[grid.length][grid[0].length()];
        for (int i = 0; i < grid.length; i++) {
            rects[i] = grid[i].toCharArray();
        }
        for (int r = 0; r < rects.length; r++) {
            for (int c = 0; c < rects[r].length; c++) {
                if (rects[r][c] == '+') {
                    count += countRectangles(rects, r, c);
                }
            }
        }
        return count;
    }

    private int countRectangles(char[][] rects, int x, int y) {
        int count = 0;
        for (int c = y + 1; c < rects[x].length; c++) {
            if (rects[x][c] == ' ' || rects[x][c] == '|') break;
            if (rects[x][c] == '+') {
                for (int r = x + 1; r < rects.length; r++) {
                    if (rects[r][c] == ' ') break;
                    if (rects[r][c] == '+' && rects[r][y] == '+') {
                        if (isValid(rects, x, r, y, c)) {
                            count++;
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        return count;
    }

    private boolean isValid(char[][] rects, int x, int x1, int y, int y1) {
        for (int r = x + 1; r < x1; r++) {
            if (rects[r][y1] == ' ' || rects[r][y1] == '-' || rects[r][y] == ' ' || rects[r][y] == '-') {
                return false;
            }
        }
        for (int c = y + 1; c < y1; c++) {
            if (rects[x1][c] == ' ' || rects[x1][c] == '|') {
                return false;
            }
        }

        return true;
    }

}