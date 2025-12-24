import java.util.ArrayList;
import java.util.List;

class WordProblemSolver {
    public int solve(final String wordProblem) {
        if (wordProblem == null || !wordProblem.startsWith("What is ") || !wordProblem.endsWith("?")) {
            throw new IllegalArgumentException("I'm sorry, I don't understand the question!");
        }

        String body = wordProblem.substring(8, wordProblem.length() - 1).trim();

        List<String> tokens = new ArrayList<>(List.of(body.split("\\s+")));

        if (tokens.isEmpty()) throw new IllegalArgumentException("I'm sorry, I don't understand the question!");

        Integer result = parseNumber(tokens.getFirst());
        if (result == null) throw new IllegalArgumentException("I'm sorry, I don't understand the question!");

        int index = 1;
        while (index < tokens.size()) {
            if (index + 1 >= tokens.size()) {
                throw new IllegalArgumentException("I'm sorry, I don't understand the question!");
            }

            String operator = tokens.get(index);
            String next = tokens.get(index + 1);

            if ((operator.equals("multiplied") || operator.equals("divided")) && next.equals("by")) {
                if (index + 2 >= tokens.size())
                    throw new IllegalArgumentException("I'm sorry, I don't understand the question!");
                operator = operator + " by";
                next = tokens.get(index + 2);
                index += 1;
            }

            Integer number = parseNumber(next);
            if (number == null) throw new IllegalArgumentException("I'm sorry, I don't understand the question!");

            result = applyOperation(result, operator, number);
            index += 2;
        }

        return result;
    }

    private Integer parseNumber(String token) {
        try {
            return Integer.valueOf(token);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private int applyOperation(int a, String op, int b) {
        return switch (op) {
            case "plus" -> a + b;
            case "minus" -> a - b;
            case "multiplied by" -> a * b;
            case "divided by" -> a / b;
            default -> throw new IllegalArgumentException("Unsupported operation: " + op);
        };
    }
}
