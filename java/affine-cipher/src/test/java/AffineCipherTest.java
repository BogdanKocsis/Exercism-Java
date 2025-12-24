import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AffineCipherTest {

    private final AffineCipher affineCipher = new AffineCipher();

    @Test
    @DisplayName("encode yes")
    public void testEncodeYes() {
        assertThat(affineCipher.encode("yes", 5, 7)).isEqualTo("xbt");
    }

    @Test
    @DisplayName("encode no")
    public void testEncodeNo() {
        assertThat(affineCipher.encode("no", 15, 18)).isEqualTo("fu");
    }

    @DisplayName("encode OMG")
    @Test
    public void testEncodeOMG() {
        assertThat(affineCipher.encode("OMG", 21, 3)).isEqualTo("lvz");
    }

    @Test
    @DisplayName("encode O M G")
    public void testEncodeOMGWithSpaces() {
        assertThat(affineCipher.encode("O M G", 25, 47)).isEqualTo("hjp");
    }

    @Test
    @DisplayName("encode mindblowingly")
    public void testEncodeMindBlowingly() {
        assertThat(affineCipher.encode("mindblowingly", 11, 15)).isEqualTo("rzcwa gnxzc dgt");
    }

    @Test
    @DisplayName("encode numbers")
    public void testEncodeNumbers() {
        assertThat(affineCipher.encode("Testing,1 2 3, testing.", 3, 4))
                .isEqualTo("jqgjc rw123 jqgjc rw");
    }

    @Test
    @DisplayName("encode deep thought")
    public void testEncodeDeepThought() {
        assertThat(affineCipher.encode("Truth is fiction.", 5, 17))
                .isEqualTo("iynia fdqfb ifje");
    }

    @Test
    @DisplayName("encode all the letters")
    public void testEncodeAllTheLetters() {
        assertThat(affineCipher.encode("The quick brown fox jumps over the lazy dog.", 17, 33))
                .isEqualTo("swxtj npvyk lruol iejdc blaxk swxmh qzglf");
    }

    @Test
    @DisplayName("encode with a not coprime to m")
    public void testEncodeThrowsMeaningfulException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> affineCipher.encode("This is a test", 6, 17))
            .withMessage("Error: keyA and alphabet size must be coprime.");
    }

    @Test
    @DisplayName("decode exercism")
    public void testDecodeExercism() {
        assertThat(affineCipher.decode("tytgn fjr", 3, 7))
                .isEqualTo("exercism");
    }

    @Test
    @DisplayName("decode a sentence")
    public void testDecodeSentence() {
        assertThat(affineCipher.decode("qdwju nqcro muwhn odqun oppmd aunwd o", 19, 16))
                .isEqualTo("anobstacleisoftenasteppingstone");
    }

    @Test
    @DisplayName("decode numbers")
    public void testDecodeNumbers() {
        assertThat(affineCipher.decode("odpoz ub123 odpoz ub", 25, 7))
                .isEqualTo("testing123testing");
    }

    @Test
    @DisplayName("decode all the letters")
    public void testDecodeAllTheLetters() {
        assertThat(affineCipher.decode("swxtj npvyk lruol iejdc blaxk swxmh qzglf", 17, 33))
                .isEqualTo("thequickbrownfoxjumpsoverthelazydog");
    }

    @Test
    @DisplayName("decode with no spaces in input")
    public void testDecodeWithNoSpaces() {
        assertThat(affineCipher.decode("swxtjnpvyklruoliejdcblaxkswxmhqzglf", 17, 33))
                .isEqualTo("thequickbrownfoxjumpsoverthelazydog");
    }

    @Test
    @DisplayName("decode with too many spaces")
    public void testDecodeWithTooManySpaces() {
        assertThat(affineCipher.decode("vszzm    cly   yd cg    qdp", 15, 16))
                .isEqualTo("jollygreengiant");
    }

    @Test
    @DisplayName("decode with a not coprime to m")
    public void testDecodeThrowsMeaningfulException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> affineCipher.decode("Test", 13, 5))
            .withMessage("Error: keyA and alphabet size must be coprime.");
    }

}
