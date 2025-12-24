class BottleSong {

    private String getNumberAsString(int number) {
        return switch (number) {
            case 1 -> "One";
            case 2 -> "Two";
            case 3 -> "Three";
            case 4 -> "Four";
            case 5 -> "Five";
            case 6 -> "Six";
            case 7 -> "Seven";
            case 8 -> "Eight";
            case 9 -> "Nine";
            case 10 -> "Ten";
            default -> "No";
        };
    }

    private String getEndingOfNumber(int number) {
        return number == 1 ? "" : "s";
    }

    private String getString(int startBottles, String song) {
        String firstNumberWord = getNumberAsString(startBottles);
        String secondNumberWord = getNumberAsString(startBottles - 1).toLowerCase();
        String endingOfFirstNumber = getEndingOfNumber(startBottles);
        String endingOfSecondNumber = getEndingOfNumber(startBottles - 1);

        return String.format(
                song,
                firstNumberWord,
                endingOfFirstNumber,
                firstNumberWord,
                endingOfFirstNumber, secondNumberWord, endingOfSecondNumber);
    }

    String recite(int startBottles, int takeDown) {
        StringBuilder stringBuilder = new StringBuilder();

        String song = """
                %s green bottle%s hanging on the wall,
                %s green bottle%s hanging on the wall,
                And if one green bottle should accidentally fall,
                There'll be %s green bottle%s hanging on the wall.\n""";

        while (takeDown != 0) {
            String preparedString = getString(startBottles, song);
            takeDown--;
            startBottles--;
            stringBuilder.append(preparedString);
            if (takeDown != 0) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

}