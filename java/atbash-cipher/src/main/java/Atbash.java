
class Atbash {

    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    String reverseAlphabet = "zyxwvutsrqponmlkjihgfedcba";

    String encode(String input) {
        StringBuilder text = new StringBuilder();
        input = input.replaceAll(" ", "").replaceAll("[,?.]", "").toLowerCase();
        for (int i = 0; i < input.length(); i++) {
            if (Character.isLetter(input.toLowerCase().charAt(i))) {
                text.append(reverseAlphabet.charAt(alphabet.indexOf(input.charAt(i))));
            } else {
                text.append(input.charAt(i));
            }
            if (i % 5 == 4 && i != input.length() - 1) {
                text.append(" ");
            }
        }
        return text.toString();
    }

    String decode(String input) {
        String text = "";
        input = input.replaceAll(" ", "").replaceAll("[,?.]", "").toLowerCase();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) >= 97 && input.charAt(i) <= 122) {
                text += alphabet.charAt(reverseAlphabet.indexOf(input.charAt(i)));
            } else {
                text += input.charAt(i);
            }
        }
        return text;
    }

}