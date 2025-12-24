import java.util.Arrays;
import java.util.List;

record Anagram(String word) {

    public List<String> match(List<String> candidates) {
        String sortedWord = sortString(word.toLowerCase());
        return candidates.stream()
                .filter(candidate -> {
                    String lowerCandidate = candidate.toLowerCase();
                    return !lowerCandidate.equals(word.toLowerCase()) &&
                            sortString(lowerCandidate).equals(sortedWord);
                })
                .toList();
    }

    private String sortString(String lowerCase) {
        char[] chars = lowerCase.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

}