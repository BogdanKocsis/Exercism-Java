import java.util.List;

class DiamondPrinter {

    List<String> printToList(char a) {
        char firstChar = 'A';
        int size = a - firstChar;
        int diamondSize = size * 2 + 1;
        String[] diamond = new String[diamondSize];
        for (int i = 0; i <= size; i++) {
            char currentChar = (char) (firstChar + i);
            String line = buildLine(currentChar, i, size);
            diamond[i] = line;
            diamond[diamondSize - 1 - i] = line;
        }
        return List.of(diamond);
    }

    private String buildLine(char currentChar, int i, int size) {
        int outerSpaces = size - i;
        StringBuilder line = new StringBuilder();
        line.append(" ".repeat(outerSpaces));
        line.append(currentChar);
        if (i > 0) {
            int innerSpaces = i * 2 - 1;
            line.append(" ".repeat(innerSpaces));
            line.append(currentChar);
        }
        line.append(" ".repeat(outerSpaces));
        return line.toString();
    }

}
