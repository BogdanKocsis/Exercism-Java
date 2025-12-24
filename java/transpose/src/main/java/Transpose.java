public class Transpose {
    public String transpose(String toTranspose) {
        if (toTranspose.isEmpty())
            return "";
        String[] rows = toTranspose.split("\n");
        StringBuilder result = new StringBuilder();
        int longestLength = 0;
        for (String row : rows) {
            if (row.length() > longestLength)
                longestLength = row.length();
        }
        int spaceCount = 0;
        for (int i = 0; i < longestLength; i++) {
            for (String row : rows) {
                if (i >= row.length()) {
                    result.append(" ");
                    spaceCount++;
                    continue;
                }
                spaceCount = 0;
                result.append(row.charAt(i));
            }
            result = new StringBuilder(result.substring(0, result.length() - spaceCount));
            result.append("\n");
        }
        return result.substring(0, result.length() - 1);
    }
}