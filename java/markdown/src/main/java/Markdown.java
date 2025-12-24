class Markdown {

    String parse(String markdown) {
        String[] lines = markdown.split("\n");
        StringBuilder result = new StringBuilder();
        boolean insideList = false;

        for (String line : lines) {
            String parsedLine = parseLine(line);
            boolean isListItem = parsedLine.startsWith("<li>");

            if (isListItem && !insideList) {
                insideList = true;
                result.append("<ul>");
            } else if (!isListItem && insideList) {
                insideList = false;
                result.append("</ul>");
            }
            result.append(parsedLine);
        }

        if (insideList) {
            result.append("</ul>");
        }

        return result.toString();
    }

    private String parseLine(String line) {
        if (line.startsWith("#")) {
            return parseHeader(line);
        } else if (line.startsWith("*")) {
            return parseListItem(line);
        } else {
            return parseParagraph(line);
        }
    }

    private String parseHeader(String markdown) {
        int level = 0;

        while (level < markdown.length() && markdown.charAt(level) == '#') {
            level++;
        }

        if (level > 6) {
            return parseParagraph(markdown);
        }

        return String.format("<h%d>%s</h%d>", level, markdown.substring(level + 1), level);
    }

    private String parseListItem(String markdown) {
        return "<li>" + parseSomeSymbols(markdown.substring(2)) + "</li>";
    }

    private String parseParagraph(String markdown) {
        return "<p>" + parseSomeSymbols(markdown) + "</p>";
    }

    private String parseSomeSymbols(String markdown) {
        String workingOn = markdown.replaceAll("__(.+?)__", "<strong>$1</strong>");
        return workingOn.replaceAll("_(.+?)_", "<em>$1</em>");
    }
}