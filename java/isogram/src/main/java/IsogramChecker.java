import java.util.HashSet;
import java.util.Set;

class IsogramChecker {

    boolean isIsogram(String phrase) {

        String cleanString = phrase.toLowerCase().replaceAll("[^a-z]", "");

        Set<Character> set = new HashSet<>();
        for (char c : cleanString.toCharArray()) {
            if (!set.add(c)) {
                return false;
            }
        }
        return true;
    }

}
