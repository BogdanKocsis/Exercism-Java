import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;

class Robot {
    private static final Pattern PATTERN = Pattern.compile("[A-Z]{2}\\d{3}");
    private static final Random RAND = new Random();
    private static final Set<String> USED = new HashSet<>();

    private String name;

    String getName() {
        return name == null ? name = generateUniqueName() : name;
    }

    private String generateUniqueName() {
        String newName;
        do {
            newName = String.format("%c%c%03d",
                    (char) ('A' + RAND.nextInt(26)),
                    (char) ('A' + RAND.nextInt(26)),
                    RAND.nextInt(1000));
        } while (!PATTERN.matcher(newName).matches() || !USED.add(newName));
        USED.add(newName);
        return newName;
    }

    void reset() {
        USED.remove(name);
        name = generateUniqueName();
    }

}