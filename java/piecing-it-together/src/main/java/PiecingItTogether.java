public class PiecingItTogether {
    private static final int MAX = 1000;

    private static JigsawInfo getJigsawInfo(int rows, int cols) {
        int pieces = rows * cols;
        int border = rows == 1 || cols == 1 ? pieces : 2 * (rows + cols) - 4;
        int inside = rows == 1 || cols == 1 ? 0 : (rows - 2) * (cols - 2);
        double aspectRatio = (double) cols / rows;
        String format = rows > cols ? "portrait" : rows == cols ? "square" : "landscape";
        return new JigsawInfo.Builder().pieces(pieces).border(border).inside(inside)
                .rows(rows).columns(cols).aspectRatio(aspectRatio).format(format).build();
    }

    private static boolean check(JigsawInfo current, JigsawInfo input) {
        int pieces = input.getPieces().orElse(current.getPieces().getAsInt());
        int border = input.getBorder().orElse(current.getBorder().getAsInt());
        int inside = input.getInside().orElse(current.getInside().getAsInt());
        int rows = input.getRows().orElse(current.getRows().getAsInt());
        int columns = input.getColumns().orElse(current.getColumns().getAsInt());
        double aspectRatio = input.getAspectRatio().orElse(current.getAspectRatio().getAsDouble());
        String format = input.getFormat().orElse(current.getFormat().get());
        JigsawInfo info = new JigsawInfo.Builder().pieces(pieces).border(border).inside(inside)
                .rows(rows).columns(columns).aspectRatio(aspectRatio).format(format).build();
        return current.equals(info);
    }

    public static JigsawInfo getCompleteInformation(JigsawInfo input) {
        JigsawInfo result = null;
        for (int row = 1; row <= MAX; row++)
            for (int col = 1; col <= MAX; col++) {
                JigsawInfo current = getJigsawInfo(row, col);
                if (!check(current, input))
                    continue;
                if (result == null)
                    result = current;
                else
                    throw new IllegalArgumentException("Insufficient data");
            }
        if (result == null)
            throw new IllegalArgumentException("Contradictory data");
        else
            return result;
    }
}