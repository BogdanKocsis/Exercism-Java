import javax.sound.midi.MidiDeviceReceiver;

class SqueakyClean {

    public static String kebabToCamel(String identifier) {
        StringBuilder result = new StringBuilder();
        boolean upperNext = false;
        for (char c : identifier.toCharArray()) {
            if (c == '-') {
                upperNext = true;
            } else {
                if (upperNext) {
                    result.append(Character.toUpperCase(c));
                    upperNext = false;
                } else {
                    result.append(c);
                }
            }
        }
        return result.toString();
    }

    public static String leetToNormal(String identifier) {
        return identifier
                .replace('4', 'a')
                .replace('3', 'e')
                .replace('0', 'o')
                .replace('1', 'l')
                .replace('7', 't');
    }

    static String clean(String identifier) {
        if (identifier.length() < 2) return identifier;
        identifier = identifier.replace(' ', '_');
        identifier = kebabToCamel(identifier);
        identifier = leetToNormal(identifier);
        identifier = identifier.replaceAll("[^a-zA-Z_\\s]", "");
        return identifier;
    }
}
