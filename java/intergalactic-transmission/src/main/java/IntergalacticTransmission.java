import java.util.*;
import java.util.stream.*;

public class IntergalacticTransmission {

    public static List<Integer> getTransmitSequence(List<Integer> message) {
        String bits = message.stream()
                .map(i -> toBinaryString(i, 8))
                .collect(Collectors.joining());
        bits = bits.concat("0".repeat((7 - bits.length() % 7) % 7));

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < bits.length() / 7; i++) {
            int current = Integer.parseInt(bits.substring(i * 7, i * 7 + 7), 2);
            result.add(current << 1 | getParityBit(current));
        }
        return result;
    }

    public static List<Integer> decodeSequence(List<Integer> sequence) {
        String bits = sequence.stream()
                .map(i -> {
                    int current = i >> 1;
                    if (getParityBit(current) != (i & 1)) throw new IllegalArgumentException();
                    return toBinaryString(current, 7);
                })
                .collect(Collectors.joining());

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < bits.length() / 8; i++) {
            result.add(Integer.parseInt(bits.substring(i * 8, i * 8 + 8), 2));
        }
        return result;
    }

    private static String toBinaryString(int number, int digit) {
        return String.format("%" + digit + "s", Integer.toBinaryString(number)).replace(' ', '0');
    }

    private static int getParityBit(int number) {
        return Integer.bitCount(number) % 2;
    }
}