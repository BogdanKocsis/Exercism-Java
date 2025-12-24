class LuhnValidator {

    boolean isValid(String candidate) {
        String cleanedString = candidate.replaceAll("\\s+", "");
        if (cleanedString.length() <= 1 || !cleanedString.matches("\\d+")) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < cleanedString.length(); i++) {
            int digit = Character.getNumericValue(cleanedString.charAt(cleanedString.length() - 1 - i));
            if (i % 2 != 0) {
                digit = digit * 2 > 9 ? digit * 2 - 9 : digit * 2;
            }
            sum += digit;
        }
        return sum % 10 == 0;
    }

}
