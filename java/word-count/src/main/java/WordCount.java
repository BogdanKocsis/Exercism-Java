import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WordCount {
    public Map<String, Integer> phrase(String input) {
        if (input == null || input.isEmpty()) {
            return new HashMap<>();
        }

        input = input.replace("\\n", "\n").replace("\\t", "\t");

        Pattern wordPattern = Pattern.compile("(?:\\p{L}+(?:['’]\\p{L}+)*)|(?:\\p{N}+)");
        Matcher matcher = wordPattern.matcher(input.toLowerCase());

        Map<String, Integer> wordCount = new HashMap<>();

        while (matcher.find()) {
            String word = matcher.group();
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        return wordCount;
    }
}