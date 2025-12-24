public class Say {
    final static String[] onesAndTeens = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",
            "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };
    final static String[] tens = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };
    final static String[] thousands = {
            "", "thousand", "million", "billion"
    };

    static String makeTail(long number, String separator, long order) {
        String result = "";
        if (number % order > 0) {
            result = separator + say(number % order);
        }
        return result;
    }

    static String convertLarge(long number, String scaleWord, long order) {
        return say(number / order) + " " + scaleWord + makeTail(number, " ", order);
    }

    public static String say(long number) {
        if (number < 0 || number >= 1_000_000_000_000L) {
            throw new IllegalArgumentException("Number out of range");
        }
        if (number == 0) return "zero";
        else if (number < onesAndTeens.length) {
            return onesAndTeens[(int) number];
        } else if (number / 10 < 10) {
            return tens[(int) number / 10] + makeTail(number, "-", 10);
        } else if (number / 100 < 10) {
            return convertLarge(number, "hundred", 100);
        } else {
            int order = (int) (Math.log(number) / Math.log(1000));
            return convertLarge(number, thousands[order], (long) Math.pow(1000, order));
        }
    }
}
