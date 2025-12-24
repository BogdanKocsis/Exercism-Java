class CryptoSquare {
    private final String normalizedPlaintext;
    private int columns;

    CryptoSquare(String plaintext) {
        this.normalizedPlaintext = plaintext.toLowerCase().replaceAll("[^a-z0-9]", "");
        this.columns = 0;
    }

    String getCiphertext() {
        if (normalizedPlaintext.isEmpty()) {
            return "";
        }

        double length = normalizedPlaintext.length();
        this.columns = (int) Math.ceil(Math.sqrt(length));
        int rows = (int) Math.ceil(length / columns);

        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < columns; i++) {
            StringBuilder chunk = new StringBuilder();
            for (int j = 0; j < rows; j++) {
                int index = i + j * columns;
                if (index < length) {
                    chunk.append(normalizedPlaintext.charAt(index));
                } else {
                    chunk.append(" ");
                }
            }
            ciphertext.append(chunk);
            if (i < columns - 1) {
                ciphertext.append(" ");
            }
        }

        return ciphertext.toString();
    }
}