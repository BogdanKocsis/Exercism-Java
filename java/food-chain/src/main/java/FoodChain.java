import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FoodChain {

    private enum Animal {
        FLY(1, "fly", BLANK, BLANK),
        SPIDER(2, "spider", "It wriggled and jiggled and tickled inside her.",
                " that wriggled and jiggled and tickled inside her"),
        BIRD(3, "bird", "How absurd to swallow a bird!", BLANK),
        CAT(4, "cat", "Imagine that, to swallow a cat!", BLANK),
        DOG(5, "dog", "What a hog, to swallow a dog!", BLANK),
        GOAT(6, "goat", "Just opened her throat and swallowed a goat!", BLANK),
        COW(7, "cow", "I don't know how she swallowed a cow!", BLANK),
        HORSE(8, "horse", "She's dead, of course!", BLANK);

        final Integer verseNumber;
        final String animal;
        final String description;
        final String secondaryDescription;

        Animal(Integer verseNumber, String animal, String description, String secondaryDescription) {
            this.verseNumber = verseNumber;
            this.animal = animal;
            this.description = description;
            this.secondaryDescription = secondaryDescription;
        }
    }

    private static final Comparator<Animal> ANIMAL_COMPARATOR = (a1, a2) -> a2.verseNumber - a1.verseNumber;

    private static final String VERSE_START = "I know an old lady who swallowed a %s.";
    private static final String VERSE_ANIMALS = "She swallowed the %s to catch the %s%s.";
    private static final String VERSE_END = "I don't know why she swallowed the fly. Perhaps she'll die.";

    private static final Integer LAST_VERSE = 8;

    private static final String BLANK = "";
    private static final String LINE_BREAK = "\n";
    private static final String DOUBLE_LINE_BREAK = "\n\n";
    private static final String MULTIPLE_LINE_BREAKS_REGEX = "\n+";

    public FoodChain() {
    }

    public String verses(Integer startVerse, Integer endVerse) {
        return IntStream.rangeClosed(startVerse, endVerse).mapToObj(this::verse)
                .collect(Collectors.joining(DOUBLE_LINE_BREAK));
    }

    public String verse(Integer verseNumber) {
        return LAST_VERSE.equals(verseNumber) ? getLastVerse() : getGenericVerse(verseNumber);
    }

    private static  String getLastVerse() {
        Animal lastAnimal = Arrays.stream(Animal.values()).filter(a -> LAST_VERSE.equals(a.verseNumber))
                .findFirst().get();
        String verseStart = String.format(VERSE_START, lastAnimal.animal);

        return String.join(LINE_BREAK, verseStart, lastAnimal.description);
    }

    private static  String getGenericVerse(Integer verseNumber) {
        List<Animal> animals = Arrays.stream(Animal.values()).filter(a -> a.verseNumber <= verseNumber)
                .sorted(ANIMAL_COMPARATOR).collect(Collectors.toList());

        String verseStart = String.format(VERSE_START, animals.getFirst().animal);
        String secondLine = animals.getFirst().description;
        String animalLines = getIntermediateLines(animals);
        String verse = String.join(LINE_BREAK, verseStart, secondLine, animalLines, VERSE_END);

        return verse.replaceAll(MULTIPLE_LINE_BREAKS_REGEX, LINE_BREAK);
    }

    private static String getIntermediateLines(List<Animal> animals) {
        List<String> lines = new ArrayList<>();

        for (int i = 0; i < animals.size() - 1; i++) {
            String animalLine = String.format(VERSE_ANIMALS, animals.get(i).animal, animals.get(i + 1).animal,
                    animals.get(i + 1).secondaryDescription);
            lines.add(animalLine);
        }

        return String.join(LINE_BREAK, lines);
    }

}