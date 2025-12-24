import java.util.List;
import java.util.Map;

record NucleotideCounter(String sequence) {
    NucleotideCounter {
        List<Character> characters = sequence.chars()
                .mapToObj(c -> (char) c)
                .toList();

        characters.stream()
                .filter(c -> c != 'A' && c != 'C' && c != 'G' && c != 'T')
                .findAny()
                .ifPresent(c -> {
                    throw new IllegalArgumentException("invalid nucleotide: " + c);
                });
    }

    Map<Character, Integer> nucleotideCounts() {
        var characters = sequence.chars()
                .mapToObj(c -> (char) c)
                .toList();

        return Map.of(
                'A', (int) characters.stream().filter(c -> c == 'A').count(),
                'C', (int) characters.stream().filter(c -> c == 'C').count(),
                'G', (int) characters.stream().filter(c -> c == 'G').count(),
                'T', (int) characters.stream().filter(c -> c == 'T').count()
        );
    }

}