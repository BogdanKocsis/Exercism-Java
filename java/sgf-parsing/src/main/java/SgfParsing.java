import java.util.*;

public class SgfParsing {

    public SgfNode parse(String input) throws SgfParsingException {
        if (!input.startsWith("(") || !input.endsWith(")")) throw new SgfParsingException("tree missing");
        if (!input.contains(";")) throw new SgfParsingException("tree with no nodes");

        return parseTree(input);
    }

    private SgfNode parseTree(String input) throws SgfParsingException {
        List<String> segments = new ArrayList<>();
        boolean isInValue = false, isInTree = false;
        int start = 1;
        for (int i = 1; i < input.length() - 1; i++) {
            char currentChar = input.charAt(i), nextChar = input.charAt(i + 1);
            if (currentChar == '\\') {
                i++;
                continue;
            }
            switch (currentChar) {
                case '(' -> {
                    if (!isInValue && !isInTree) isInTree = true;
                }
                case ')' -> {
                    if (!isInValue) isInTree = false;
                }
                case '[' -> {
                    if (!isInValue && !isInTree) isInValue = true;
                }
                case ']' -> {
                    if (!isInTree) isInValue = false;
                }
            }

            boolean isEnd = !isInTree && !isInValue && (nextChar == ';' || nextChar == '(' || nextChar == ')');
            if (isEnd) {
                segments.add(input.substring(start, i + 1));
                start = i + 1;
            }
        }

        SgfNode result = new SgfNode();
        SgfNode current = result;
        boolean first = true;
        for (String node : segments) {
            if (node.startsWith(";")) {
                if (first) {
                    first = false;
                } else {
                    SgfNode childNode = new SgfNode();
                    current.appendChild(childNode);
                    current = childNode;
                }
                if (!node.equals(";")) {
                    current.setProperties(getProperties(node));
                }
            } else {
                current.appendChild(parseTree(node));
            }
        }
        return result;
    }

    private Map<String, List<String>> getProperties(String input) throws SgfParsingException {
        if (!input.contains("[") || !input.contains("]")) throw new SgfParsingException("properties without delimiter");

        Map<String, List<String>> result = new HashMap<>();
        String key = null;
        List<String> values = new ArrayList<>();
        boolean isInValue = false;
        int start = 1;
        for (int i = 1; i < input.length(); i++) {
            char currentChar = input.charAt(i), nextChar = i == input.length() - 1 ? '\0' : input.charAt(i + 1);
            if (currentChar == '\\') {
                i++;
                continue;
            }
            switch (currentChar) {
                case '[' -> {
                    if (!isInValue) isInValue = true;
                }
                case ']' -> isInValue = false;
            }

            if (!isInValue && currentChar != ']' && nextChar == '[') {
                key = input.substring(start, i + 1);
                if (key.matches(".*[a-z].*")) throw new SgfParsingException("property must be in uppercase");
                start = i + 1;
            } else if (currentChar == ']') {
                values.add(input.substring(start + 1, i).replace("\\\n", "").replaceAll("[\t\f\r]", " ").replaceAll("\\\\(.)", "$1"));
                start = i + 1;
                if (nextChar != '[') {
                    result.put(key, values);
                    values = new ArrayList<>();
                }
            }
        }
        return result;
    }
}
