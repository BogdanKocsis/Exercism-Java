import java.util.List;
import java.util.ArrayList;

class VariableLengthQuantity {

    List<String> encode(List<Long> numbers) {
        List<String> ans = new ArrayList<>();

        for (long n : numbers) {
            List<String> curr = new ArrayList<>();

            curr.add(String.format("0x%x", n & 0x7f));
            n >>= 7;

            while (n > 0) {
                curr.add(String.format("0x%x", n & 0x7f | 0x80));
                n >>= 7;
            }

            for (int i = curr.size() - 1; i >= 0; i--) ans.add(curr.get(i));
        }

        return ans;
    }

    List<String> decode(List<Long> bytes) {
        if ((bytes.getLast() & 0x80) != 0)
            throw new IllegalArgumentException("Invalid variable-length quantity encoding");

        List<String> ans = new ArrayList<>();
        long tmp = 0L;

        for (long b : bytes) {
            tmp <<= 7;
            tmp += b & 0x7f;

            if ((b & 0x80) == 0) {
                ans.add(String.format("0x%x", tmp));
                tmp = 0L;
            }
        }

        return ans;
    }
}