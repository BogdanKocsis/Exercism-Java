import java.util.*;
import java.util.stream.Collectors;

class School {
    private final Map<String, Integer> students = new TreeMap<>();

    boolean add(String student, int grade) {
        int auxLength = students.size();
        students.putIfAbsent(student, grade);
        return students.size() > auxLength;
    }

    List<String> roster() {
        return students.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());
    }

    List<String> grade(int grade) {
        return students.entrySet().stream()
                .filter(entry -> entry.getValue() == grade)
                .map(Map.Entry::getKey)
                .toList();
    }

}
