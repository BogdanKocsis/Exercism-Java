class Bob {

    String hey(String input) {
        input = input.trim();

        if (input.isEmpty()) return "Fine. Be that way!";

        boolean question = input.endsWith("?");
        boolean yelling = input.matches(".*[A-Z].*") && input.equals(input.toUpperCase());

        if (yelling && question) return "Calm down, I know what I'm doing!";
        if (yelling) return "Whoa, chill out!";
        if (question) return "Sure.";
        return "Whatever.";

    }

}