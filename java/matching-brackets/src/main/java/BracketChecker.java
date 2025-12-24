import java.util.*;

class BracketChecker {
    private final String expression;
    private final Set<Character> openingBrackets = Set.of('(', '{', '[');
    private final Set<Character> closingBrackets = Set.of(')', '}', ']');
    private final Map<Character, Character> map = Map.ofEntries(
            Map.entry(')', '('),
            Map.entry('}', '{'),
            Map.entry(']', '[')
    );

    BracketChecker(String expression) {
        this.expression = expression;
    }

    private boolean isValid(Deque<Character> d, char c) {
        return !d.isEmpty() && d.pollLast() == c;
    }

    boolean areBracketsMatchedAndNestedCorrectly() {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : expression.toCharArray()) {
            if (openingBrackets.contains(c)) {
                stack.offerLast(c);
            } else if (closingBrackets.contains(c)) {
                if (!isValid(stack, map.get(c))) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

}