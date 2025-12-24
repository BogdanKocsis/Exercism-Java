import java.util.*;

record FlowerFieldBoard(List<String> boardRows) {

    List<String> withNumbers() {
        List<String> result = new LinkedList<>();

        for (int i = 0; i < boardRows.size(); i++) {
            String row = boardRows.get(i);
            StringBuilder rowResult = new StringBuilder();

            for (int j = 0; j < row.length(); j++) {
                char currentChar = row.charAt(j);
                rowResult.append(currentChar == '*' ? currentChar : getAdjacentCount(i, j));
            }

            result.add(rowResult.toString());
        }
        return result;
    }

    private String getAdjacentCount(int startRow, int startColumn) {
        int count = 0;
        for (int i = startRow - 1; i <= startRow + 1; i++) {
            if (i < 0 || i >= boardRows.size()) continue;
            String row = boardRows.get(i);
            for (int j = startColumn - 1; j <= startColumn + 1; j++) {
                if (j < 0 || j >= row.length() || row.charAt(j) == ' ') continue;
                count++;
            }
        }
        return count == 0 ? " " : String.valueOf(count);
    }
}