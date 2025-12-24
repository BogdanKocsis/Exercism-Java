class PhoneNumber {
    private String numberString;

    PhoneNumber(String numberString) {
        this.numberString = numberString;

        if (numberString.chars().anyMatch(Character::isLetter)) {
            throw new IllegalArgumentException("letters not permitted");
        }

        if (numberString.chars()
                .anyMatch(c -> !Character.isDigit(c)
                        && !Character.isWhitespace(c)
                        && ".-()+".indexOf(c) == -1)) {
            throw new IllegalArgumentException("punctuations not permitted");
        }

        this.numberString = numberString.chars()
                .filter(Character::isDigit)
                .collect(StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();

        int len = this.numberString.length();
        if (len < 10)
            throw new IllegalArgumentException("must not be fewer than 10 digits");
        if (len > 11)
            throw new IllegalArgumentException("must not be greater than 11 digits");

        if (len == 11) {
            if (this.numberString.charAt(0) != '1')
                throw new IllegalArgumentException("11 digits must start with 1");
            this.numberString = this.numberString.substring(1);
        }

        if (this.numberString.charAt(0) == '0')
            throw new IllegalArgumentException("area code cannot start with zero");
        if (this.numberString.charAt(0) == '1')
            throw new IllegalArgumentException("area code cannot start with one");
        if (this.numberString.charAt(3) == '0')
            throw new IllegalArgumentException("exchange code cannot start with zero");
        if (this.numberString.charAt(3) == '1')
            throw new IllegalArgumentException("exchange code cannot start with one");
    }

    String getNumber() {
        return this.numberString;
    }
}