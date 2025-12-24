import java.util.List;

class ResistorColorDuo {
    int value(String[] colors) {
        List<String> colorCodes = List.of(
                "black", "brown", "red", "orange", "yellow",
                "green", "blue", "violet", "grey", "white"
        );

        return 10 * colorCodes.indexOf(colors[0]) + colorCodes.indexOf(colors[1]);
    }
}
