import java.util.ArrayList;
import java.util.List;

record RailFenceCipher(int rows) {

    String getEncryptedData(String message) {
        if (rows <= 1 || message.isEmpty()) {
            return message;
        }

        List<StringBuilder> rails = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            rails.add(new StringBuilder());
        }

        int currentRow = 0;
        int direction = 1;

        for (char c : message.toCharArray()) {
            rails.get(currentRow).append(c);
            currentRow += direction;

            if (currentRow == rows - 1 || currentRow == 0) {
                direction *= -1;
            }
        }

        StringBuilder encryptedMessage = new StringBuilder();
        for (StringBuilder rail : rails) {
            encryptedMessage.append(rail);
        }

        return encryptedMessage.toString();
    }

    String getDecryptedData(String message) {
        if (rows <= 1 || message.isEmpty()) {
            return message;
        }

        char[] decrypted = new char[message.length()];
        int[] railLengths = new int[rows];
        int currentRow = 0;
        int direction = 1;

        for (int i = 0; i < message.length(); i++) {
            railLengths[currentRow]++;
            currentRow += direction;
            if (currentRow == rows - 1 || currentRow == 0) {
                direction *= -1;
            }
        }

        int messageIndex = 0;
        List<StringBuilder> rails = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            StringBuilder rail = new StringBuilder();
            int length = railLengths[i];
            rail.append(message, messageIndex, messageIndex + length);
            rails.add(rail);
            messageIndex += length;
        }

        currentRow = 0;
        direction = 1;
        int[] railPointers = new int[rows];

        for (int i = 0; i < message.length(); i++) {
            decrypted[i] = rails.get(currentRow).charAt(railPointers[currentRow]);
            railPointers[currentRow]++;
            currentRow += direction;
            if (currentRow == rows - 1 || currentRow == 0) {
                direction *= -1;
            }
        }

        return new String(decrypted);
    }
}