class IsbnVerifier {
    boolean isValid(String input) {
        int sum = 0;
        int index = 10;
        for (char c : input.toCharArray()) {
            if (c == '-') { continue; }
            if (c == 'X' &&  index == 1) { sum += 10; }
            else if ('0' <= c && c <= '9') { sum +=index  * (c - '0'); }
            else { return false; }
            index--;
        }
        return index == 0 && sum % 11 == 0;
    }
}