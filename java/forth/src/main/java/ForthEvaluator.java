import java.util.*;
import java.util.stream.*;
import java.util.function.*;

class ForthEvaluator {

    List<Integer> evaluateProgram(List<String> input) {
        List<String> newInput = new LinkedList<>(input);
        Map<String, List<String>> definitions = new HashMap<>();
        for (String current : input) {
            if (current.matches(": \\S+ .+? ;")) {
                newInput.remove(current);
                current = current.toLowerCase();
                String name = current.replaceFirst(": (\\S+) .+? ;", "$1");
                if (name.matches("-?\\d+")) throw new IllegalArgumentException("Cannot redefine numbers");
                List<String> definition = Arrays.stream(current.replaceFirst(": \\S+ (.+?) ;", "$1").split(" "))
                        .flatMap(s -> definitions.getOrDefault(s, List.of(s)).stream())
                        .toList();
                definitions.put(name, definition);
            }
        }

        List<Integer> result = new LinkedList<>();
        List<String> operations = newInput.stream()
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .collect(Collectors.toCollection(LinkedList::new));
        while (!operations.isEmpty()) {
            String current = operations.removeFirst().toLowerCase();
            try {
                result.add(Integer.parseInt(current));
            } catch (NumberFormatException e) {
                if (definitions.containsKey(current)) {
                    operations.addAll(0, definitions.get(current));
                    continue;
                }
                switch (current) {
                    case "+" -> arithmetic(result, Integer::sum, "Addition");
                    case "-" -> arithmetic(result, (b, a) -> a - b, "Subtraction");
                    case "*" -> arithmetic(result, (b, a) -> a * b, "Multiplication");
                    case "/" -> arithmetic(result, (b, a) -> {
                        if (b == 0) throw new IllegalArgumentException("Division by 0 is not allowed");
                        return a / b;
                    }, "Division");

                    case "dup" -> {
                        checkSize(result, 1, "Duplicating");
                        result.add(result.getLast());
                    }
                    case "drop" -> {
                        checkSize(result, 1, "Dropping");
                        result.removeLast();
                    }
                    case "swap" -> {
                        checkSize(result, 2, "Swapping");
                        Integer temp = result.removeLast();
                        result.add(result.size() - 1, temp);
                    }
                    case "over" -> {
                        checkSize(result, 2, "Overing");
                        result.add(result.get(result.size() - 2));
                    }
                    default ->
                            throw new IllegalArgumentException("No definition available for operator " + "\"" + current + "\"");
                }
            }
        }
        return result;
    }

    private void arithmetic(List<Integer> result, BiFunction<Integer, Integer, Integer> function, String name) {
        checkSize(result, 2, name);
        result.add(function.apply(result.removeLast(), result.removeLast()));
    }

    private void checkSize(List<Integer> result, int minimumSize, String action) {
        if (result.size() < minimumSize)
            throw new IllegalArgumentException(action + " requires that the stack contain at least " + minimumSize + " value" + (minimumSize == 1 ? "" : "s"));
    }
}