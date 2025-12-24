import java.util.List;

class OpticalCharacterReader {
    private final String[] topStrings = { " _ ", "   ", " _ ", " _ ", "   ", " _ ", " _ ", " _ ", " _ ", " _ " };
    private final String[] midStrings = { "| |", "  |", " _|", " _|", "|_|", "|_ ", "|_ ", "  |", "|_|", "|_|" };
    private final String[] botStrings = { "|_|", "  |", "|_ ", " _|", "  |", " _|", "|_|", "  |", "|_|", " _|" };

    String parse(List<String> input) {
        if (input.size() % 4 != 0)
            throw new IllegalArgumentException("Number of input rows must be a positive multiple of 4");
        List<String> oneLine;
        StringBuilder allParsed = new StringBuilder();
        for (int i = 0; i < input.size(); i += 4) {
            oneLine = input.subList(i, i + 4);
            allParsed.append(parseOneLine(oneLine)).append(",");
        }
        return allParsed.substring(0, allParsed.length() - 1);
    }

    private String parseOneLine(List<String> oneLine) {
        if (oneLine.get(0).length() % 3 != 0)
            throw new IllegalArgumentException("Number of input columns must be a positive multiple of 3");
        StringBuilder oneLineParsed = new StringBuilder();
        for (int i = 0; i < oneLine.get(0).length(); i += 3) {
            String top = oneLine.get(0).substring(i, i + 3);
            String mid = oneLine.get(1).substring(i, i + 3);
            String bot = oneLine.get(2).substring(i, i + 3);
            String fourth = oneLine.get(3).substring(i, i + 3);
            oneLineParsed.append(parseOneDigit(top, mid, bot, fourth));
        }
        return oneLineParsed.toString();

    }

    private String parseOneDigit(String top, String mid, String bot, String fourth) {
        if (!fourth.equals("   "))
            throw new IllegalArgumentException();
        for (int i = 0; i < 10; i++) {
            if (!topStrings[i].equals(top))
                continue;
            if (!midStrings[i].equals(mid))
                continue;
            if (!botStrings[i].equals(bot))
                continue;
            return Character.toString(i + '0');
        }
        return "?";
    }

}