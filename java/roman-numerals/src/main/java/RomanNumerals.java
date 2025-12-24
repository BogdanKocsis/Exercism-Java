import java.util.*;
import java.util.stream.Collectors;

enum RomanNumeral {
    M(1000),
    CM(900),
    D(500),
    CD(400),
    C(100),
    XC(90),
    L(50),
    XL(40),
    X(10),
    IX(9),
    V(5),
    IV(4),
    I(1);

    private final int value;

    RomanNumeral(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<RomanNumeral> getReversedValues() {
        return Arrays.stream(values())
                .sorted(Comparator.comparing((RomanNumeral r) -> r.value).reversed())
                .collect(Collectors.toList());
    }
}

class RomanNumerals {
    private int number;

    RomanNumerals(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be non-negative.");
        }
        this.number = number;
    }

    String getRomanNumeral() {
        int i = 0;
        StringBuilder romanNumeral = new StringBuilder();
        List<RomanNumeral> romanNumerals = RomanNumeral.getReversedValues();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                romanNumeral.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return romanNumeral.toString();
    }
}


