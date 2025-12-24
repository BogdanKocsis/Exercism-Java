class MicroBlog {

    public String truncate(String input) {
        if (input == null) {
            throw new NullPointerException("Input is null");
        }

        int MAX_LENGTH = 5;
        if (input.codePointCount(0, input.length()) <= MAX_LENGTH) {
            return input;
        }
        int endIndex = input.offsetByCodePoints(0, MAX_LENGTH);
        return input.substring(0, endIndex);
    }
}
