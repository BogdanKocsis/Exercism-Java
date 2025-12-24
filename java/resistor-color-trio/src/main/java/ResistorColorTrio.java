import java.util.List;

class ResistorColorTrio {
    String label(String[] colors) {
        List<String> colorCodes = List.of(
                "black", "brown", "red", "orange", "yellow",
                "green", "blue", "violet", "grey", "white"
        );
        final String[] UNITS = {"ohms", "kiloohms", "megaohms", "gigaohms"};

        int firstDigit = colorCodes.indexOf(colors[0]);
        int secondDigit = colorCodes.indexOf(colors[1]);
        int multiplier = colorCodes.indexOf(colors[2]);

        long value = (firstDigit * 10L + secondDigit) * (long) Math.pow(10, multiplier);

        int unitIndex = 0;
        while (unitIndex < UNITS.length - 1 && value % 1000 == 0 && value != 0) {
            value /= 1000;
            unitIndex++;
        }
        return value + " " + UNITS[unitIndex];
    }
}
