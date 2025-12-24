class Proverb {
    private final String[] words;


    Proverb(String[] words) {
        if (words == null) {
            throw new IllegalArgumentException("Words array cannot be null");
        }
        this.words = words;
    }

    String recite() {
        if (words.length == 0) {
            return "";
        }
        final String templateLine = "For want of a %s the %s was lost.";
        final String templateEnd = "And all for the want of a %s.";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            result.append(String.format(templateLine, words[i], words[i + 1])).append("\n");
        }
        result.append(String.format(templateEnd, words[0]));
        return result.toString();

    }

}
