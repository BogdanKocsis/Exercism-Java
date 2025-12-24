import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ScrabbleScoreTest {

    @Test
    @DisplayName("lowercase letter")
    public void testALowerCaseLetter() {
        Scrabble scrabble = new Scrabble("a");
        assertThat(scrabble.getScore()).isEqualTo(1);
    }


    @Test
    @DisplayName("uppercase letter")
    public void testAUpperCaseLetter() {
        Scrabble scrabble = new Scrabble("A");
        assertThat(scrabble.getScore()).isEqualTo(1);
    }


    @Test
    @DisplayName("valuable letter")
    public void testAValuableLetter() {
        Scrabble scrabble = new Scrabble("f");
        assertThat(scrabble.getScore()).isEqualTo(4);
    }


    @Test
    @DisplayName("short word")
    public void testAShortWord() {
        Scrabble scrabble = new Scrabble("at");
        assertThat(scrabble.getScore()).isEqualTo(2);
    }


    @Test
    @DisplayName("short, valuable word")
    public void testAShortValuableWord() {
        Scrabble scrabble = new Scrabble("zoo");
        assertThat(scrabble.getScore()).isEqualTo(12);
    }


    @Test
    @DisplayName("medium word")
    public void testAMediumWord() {
        Scrabble scrabble = new Scrabble("street");
        assertThat(scrabble.getScore()).isEqualTo(6);
    }


    @Test
    @DisplayName("medium, valuable word")
    public void testAMediumValuableWord() {
        Scrabble scrabble = new Scrabble("quirky");
        assertThat(scrabble.getScore()).isEqualTo(22);
    }


    @Test
    @DisplayName("long, mixed-case word")
    public void testALongMixCaseWord() {
        Scrabble scrabble = new Scrabble("OxyphenButazone");
        assertThat(scrabble.getScore()).isEqualTo(41);
    }


    @Test
    @DisplayName("english-like word")
    public void testAEnglishLikeWord() {
        Scrabble scrabble = new Scrabble("pinata");
        assertThat(scrabble.getScore()).isEqualTo(8);
    }


    @Test
    @DisplayName("empty input")
    public void testAnEmptyInput() {
        Scrabble scrabble = new Scrabble("");
        assertThat(scrabble.getScore()).isEqualTo(0);
    }


    @Test
    @DisplayName("entire alphabet available")
    public void testEntireAlphabetAvailable() {
        Scrabble scrabble = new Scrabble("abcdefghijklmnopqrstuvwxyz");
        assertThat(scrabble.getScore()).isEqualTo(87);
    }

}
