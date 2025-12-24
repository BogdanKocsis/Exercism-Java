import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RunLengthEncodingTest {
    private RunLengthEncoding runLengthEncoding;

    @BeforeEach
    public void setUp() {
        runLengthEncoding = new RunLengthEncoding();
    }

    @Test
    @DisplayName("empty string")
    public void encodeEmpty() {
        assertThat(runLengthEncoding.encode("")).isEmpty();
    }


    @Test
    @DisplayName("single characters only are encoded without count")
    public void encodeWithOnlySingleValues() {
        assertThat(runLengthEncoding.encode("XYZ")).isEqualTo("XYZ");
    }


    @Test
    @DisplayName("string with no single characters")
    public void encodeWithNoSingleValues() {
        assertThat(runLengthEncoding.encode("AABBBCCCC")).isEqualTo("2A3B4C");
    }


    @Test
    @DisplayName("single characters mixed with repeated characters")
    public void encodeWithMixedValues() {
        assertThat(runLengthEncoding.encode(
            "WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWB"))
            .isEqualTo("12WB12W3B24WB");
    }


    @Test
    @DisplayName("multiple whitespace mixed in string")
    public void encodeWithWhitespaceValues() {
        assertThat(runLengthEncoding.encode("  hsqq qww  "))
            .isEqualTo("2 hs2q q2w2 ");
    }


    @Test
    @DisplayName("lowercase characters")
    public void encodeWithLowercaseValues() {
        assertThat(runLengthEncoding.encode("aabbbcccc")).isEqualTo("2a3b4c");
    }


    @Test
    @DisplayName("empty string")
    public void decodeEmpty() {
        assertThat(runLengthEncoding.decode("")).isEmpty();
    }


    @Test
    @DisplayName("single characters only")
    public void decodeWithOnlySingleValues() {
        assertThat(runLengthEncoding.decode("XYZ")).isEqualTo("XYZ");
    }


    @Test
    @DisplayName("string with no single characters")
    public void decodeWithNoSingleValues() {
        assertThat(runLengthEncoding.decode("2A3B4C")).isEqualTo("AABBBCCCC");
    }


    @Test
    @DisplayName("single characters with repeated characters")
    public void decodeWithMixedValues() {
        assertThat(runLengthEncoding.decode("12WB12W3B24WB"))
            .isEqualTo("WWWWWWWWWWWWBWWWWWWWWWWWWBBBWWWWWWWWWWWWWWWWWWWWWWWWB");
    }


    @Test
    @DisplayName("multiple whitespace mixed in string")
    public void decodeWithWhitespaceValues() {
        assertThat(runLengthEncoding.decode("2 hs2q q2w2 ")).isEqualTo("  hsqq qww  ");
    }


    @Test
    @DisplayName("lowercase string")
    public void decodeWithLowercaseValues() {
        assertThat(runLengthEncoding.decode("2a3b4c")).isEqualTo("aabbbcccc");
    }


    @Test
    @DisplayName("encode followed by decode gives original string")
    public void encodeThenDecode() {
        String inOut = "zzz ZZ  zZ";
        String encoded = runLengthEncoding.encode(inOut);
        assertThat(runLengthEncoding.decode(encoded)).isEqualTo(inOut);
    }
}
