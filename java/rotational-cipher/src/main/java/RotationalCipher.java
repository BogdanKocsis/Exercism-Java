import java.util.stream.Collectors;

record RotationalCipher(int shiftKey) {

    String rotate(String data) {
        return data.chars().map(c -> Character.isLowerCase(c) ?
                        ((c - 'a' + shiftKey) % 26 + 'a') :
                        Character.isUpperCase(c) ?
                                ((c - 'A' + shiftKey) % 26 + 'A') :
                                c)
                .mapToObj(i -> String.valueOf((char) i)).collect(Collectors.joining());
    }
}
