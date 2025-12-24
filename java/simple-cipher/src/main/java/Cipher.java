public class Cipher {

    private static final int MINIMUM_KEY_LENGTH = 100;
    private static final int ALPHABET_LENGTH = 26;
    private static final int ASCII_CHAR_FIRST = 97;
    private static final int ASCII_CHAR_LAST = 122;
    private final String key;

    public Cipher() {
        StringBuilder key = new StringBuilder();
        for (int i = (int) (MINIMUM_KEY_LENGTH + (MINIMUM_KEY_LENGTH * Math.random())); i > 0; i--) {
            key.append((char) (ASCII_CHAR_FIRST + Math.random() * (ALPHABET_LENGTH - 1)));
        }
        this.key = String.valueOf(key);
    }

    public Cipher(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String encode(String plainText) {
        return shiftString(plainText, 1);
    }

    private String shiftString(String plainText, int direction) {
        char[] characters = plainText.toLowerCase().toCharArray();
        for (int i = 0; i < characters.length; i++) {
            characters[i] = rotateChar(characters[i], extendKey(plainText.length()).charAt(i), direction);
        }
        return String.valueOf(characters);
    }

    private char rotateChar(int character, int amount, int direction) {
        int result = character + direction * (amount - ASCII_CHAR_FIRST);
        while (result < ASCII_CHAR_FIRST || result > ASCII_CHAR_LAST) {
            result -= direction * ALPHABET_LENGTH;
        }
        return (char) result;
    }

    public String decode(String cipherText) {
        return shiftString(cipherText, -1);
    }

    private String extendKey(int newLength) {
        StringBuilder matchingKey = new StringBuilder(key);
        while (matchingKey.length() < newLength) {
            matchingKey.append(matchingKey);
        }
        return matchingKey.toString();
    }
}