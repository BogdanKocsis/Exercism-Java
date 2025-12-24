import java.awt.Point;
import java.util.*;

class GoCounting {
    private final char[][] grid;
    private final int totalRows;
    private final int totalCols;

    public GoCounting(String boardString) {
        String[] lines = boardString.split("\n");
        this.totalRows = lines.length;
        this.totalCols = lines[0].length();
        this.grid = new char[totalRows][totalCols];

        for (int i = 0; i < totalRows; i++) {
            this.grid[i] = lines[i].toCharArray();
        }
    }

    public Player getTerritoryOwner(int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalArgumentException("Invalid coordinate");
        if (grid[y][x] != ' ') return Player.NONE;
        return exploreTerritory(x, y).owner;
    }

    public Set<Point> getTerritory(int x, int y) {
        if (!isValidCoordinate(x, y)) throw new IllegalArgumentException("Invalid coordinate");
        if (grid[y][x] != ' ') return Collections.emptySet();
        return exploreTerritory(x, y).territory;
    }

    public Map<Player, Set<Point>> getTerritories() {
        Map<Player, Set<Point>> allTerritories = new HashMap<>();
        allTerritories.put(Player.BLACK, new HashSet<>());
        allTerritories.put(Player.WHITE, new HashSet<>());
        allTerritories.put(Player.NONE, new HashSet<>());

        boolean[][] visited = new boolean[totalRows][totalCols];

        for (int row = 0; row < totalRows; row++) {
            for (int col = 0; col < totalCols; col++) {
                if (grid[row][col] == ' ' && !visited[row][col]) {
                    TerritoryResult result = exploreTerritory(col, row);
                    allTerritories.get(result.owner).addAll(result.territory);
                    for (Point p : result.territory) visited[p.y][p.x] = true;
                }
            }
        }

        return allTerritories;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < totalCols && y >= 0 && y < totalRows;
    }

    private TerritoryResult exploreTerritory(int startX, int startY) {
        Set<Point> territorySet = new HashSet<>();
        Set<Character> bordering = new HashSet<>();
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));
        boolean[][] visited = new boolean[totalRows][totalCols];
        visited[startY][startX] = true;

        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            territorySet.add(current);

            for (int[] dir : directions) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];

                if (isValidCoordinate(nx, ny)) {
                    char cell = grid[ny][nx];
                    if (cell == ' ' && !visited[ny][nx]) {
                        queue.add(new Point(nx, ny));
                        visited[ny][nx] = true;
                    } else if (cell == 'B' || cell == 'W') {
                        bordering.add(cell);
                    }
                }
            }
        }

        Player owner;
        if (bordering.size() != 1) owner = Player.NONE;
        else owner = bordering.contains('B') ? Player.BLACK : Player.WHITE;

        return new TerritoryResult(owner, territorySet);
    }

    private record TerritoryResult(Player owner, Set<Point> territory) {
    }
}