class Acronym {
    private String phrase = "";

    Acronym(String phrase) {
        this.phrase = phrase;
    }

    String get() {
        if (phrase == null || phrase.isBlank()) return "";

        String lettersOnly = phrase.replace('-', ' ')
                .replaceAll("[^\\p{L}\\s]", "");

        StringBuilder acronym = new StringBuilder();
        for (String word : lettersOnly.trim().split("\\s+")) {
            if (!word.isEmpty()) {
                acronym.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        return acronym.toString();
    }

}
