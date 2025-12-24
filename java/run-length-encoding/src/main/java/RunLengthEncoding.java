import java.util.stream.IntStream;

class RunLengthEncoding {

    String encode(String data) {
        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            int count = 1;
            while (i + 1 < data.length() && data.charAt(i) == data.charAt(i + 1)) {
                count++;
                i++;
            }
            if (count == 1) {
                encoded.append(data.charAt(i));
                continue;
            }
            encoded.append(count).append(data.charAt(i));
        }
        return encoded.toString();
    }

    String decode(String data) {
        StringBuilder decoded = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char ch = data.charAt(i);
            StringBuilder numberString = new StringBuilder();
            if (Character.isDigit(ch)) {
                while (Character.isDigit(ch)) {
                    numberString.append(ch);
                    i++;
                    ch = data.charAt(i);
                }
            }
            int count = !numberString.isEmpty() ? Integer.parseInt(numberString.toString()) : 1;
            char finalCh = ch;
            IntStream.range(0, count).forEach(j -> decoded.append(finalCh));
        }
        return decoded.toString();
    }
}