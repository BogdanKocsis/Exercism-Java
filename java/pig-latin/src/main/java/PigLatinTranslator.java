import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PigLatinTranslator {
    public String translate(String word) {
        return Arrays.stream(word.split("\\s+"))
                .map(this::translateWord)
                .reduce((a, b) -> a + " " + b)
                .orElse("");
    }

    public String translateWord(String word) {
        if (word.matches("^(?:[aeiou]|xr|yt).*")) {
            return word + "ay";
        }
        Matcher qu = Pattern.compile("^([^aeiou]*qu)(.*)").matcher(word);
        if (qu.find()) {
            return qu.group(2) + qu.group(1) + "ay";
        }

        Matcher y = Pattern.compile("^([^aeiou]+)(y.*)").matcher(word);
        if (y.find()) {
            return y.group(2) + y.group(1) + "ay";
        }

        Matcher cons = Pattern.compile("^([^aeiou]+)(.*)").matcher(word);
        if (cons.find()) {
            return cons.group(2) + cons.group(1) + "ay";
        }
        return word + "ay";
    }

}