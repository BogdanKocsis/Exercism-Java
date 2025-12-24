import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

record ParallelLetterFrequency(String[] texts) {

    Map<Character, Integer> countLetters() {
        return Arrays.stream(texts)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .flatMapToInt(String::chars)
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .map(Character::toLowerCase)
                .collect(Collectors.groupingBy(
                        c -> c,
                        Collectors.summingInt(c -> 1)
                ));
    }

}
