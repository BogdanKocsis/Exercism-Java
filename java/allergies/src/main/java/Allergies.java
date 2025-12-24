import java.util.Arrays;
import java.util.List;

class Allergies {
    private final List<Allergen> allergens;
    private final int score;

    Allergies(int score) {
        this.score = score;
        this.allergens = Arrays.stream(Allergen.values())
                .filter(this::isAllergicTo)
                .toList();
    }

    boolean isAllergicTo(Allergen allergen) {
        if (allergen == null) {
            return false;
        }
        return (score & allergen.getScore()) != 0;
    }

    List<Allergen> getList() {
        return allergens;
    }
}
