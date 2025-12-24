import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FoodChainTest {
    private FoodChain foodChain;

    @BeforeEach
    public void setup() {
        foodChain = new FoodChain();
    }

    @Test
    @DisplayName("fly")
    public void fly() {
        int verse = 1;
        String expected = "I know an old lady who swallowed a fly.\n" +
                "I don't know why she swallowed the fly. Perhaps she'll die.";

        assertThat(foodChain.verse(verse)).isEqualTo(expected);
    }

    @Test
    @DisplayName("spider")
    public void spider() {
        int verse = 2;
        String expected = """
                I know an old lady who swallowed a spider.
                It wriggled and jiggled and tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.""";

        assertThat(foodChain.verse(verse)).isEqualTo(expected);
    }

    @Test
    @DisplayName("bird")
    public void bird() {
        int verse = 3;
        String expected = """
                I know an old lady who swallowed a bird.
                How absurd to swallow a bird!
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.""";

        assertThat(foodChain.verse(verse)).isEqualTo(expected);
    }

    @Test
    @DisplayName("cat")
    public void cat() {
        int verse = 4;
        String expected = """
                I know an old lady who swallowed a cat.
                Imagine that, to swallow a cat!
                She swallowed the cat to catch the bird.
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.""";

        assertThat(foodChain.verse(verse)).isEqualTo(expected);
    }


    @Test
    @DisplayName("dog")
    public void dog() {
        int verse = 5;
        String expected = """
                I know an old lady who swallowed a dog.
                What a hog, to swallow a dog!
                She swallowed the dog to catch the cat.
                She swallowed the cat to catch the bird.
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.""";

        assertThat(foodChain.verse(verse)).isEqualTo(expected);
    }

    @Test
    @DisplayName("goat")
    public void goat() {
        int verse = 6;
        String expected = """
                I know an old lady who swallowed a goat.
                Just opened her throat and swallowed a goat!
                She swallowed the goat to catch the dog.
                She swallowed the dog to catch the cat.
                She swallowed the cat to catch the bird.
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.""";

        assertThat(foodChain.verse(verse)).isEqualTo(expected);
    }

    @Test
    @DisplayName("cow")
    public void cow() {
        int verse = 7;
        String expected = """
                I know an old lady who swallowed a cow.
                I don't know how she swallowed a cow!
                She swallowed the cow to catch the goat.
                She swallowed the goat to catch the dog.
                She swallowed the dog to catch the cat.
                She swallowed the cat to catch the bird.
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.""";

        assertThat(foodChain.verse(verse)).isEqualTo(expected);
    }

    @Test
    @DisplayName("horse")
    public void horse() {
        int verse = 8;
        String expected = "I know an old lady who swallowed a horse.\n" +
                "She's dead, of course!";

        assertThat(foodChain.verse(verse)).isEqualTo(expected);
    }


    @Test
    @DisplayName("multiple verses")
    public void multipleVerses() {
        int startVerse = 1;
        int endVerse = 3;
        String expected = """
                I know an old lady who swallowed a fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a spider.
                It wriggled and jiggled and tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a bird.
                How absurd to swallow a bird!
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.""";

        assertThat(foodChain.verses(startVerse, endVerse)).isEqualTo(expected);
    }


    @Test
    @DisplayName("full song")
    public void wholeSong() {
        int startVerse = 1;
        int endVerse = 8;
        String expected = """
                I know an old lady who swallowed a fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a spider.
                It wriggled and jiggled and tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a bird.
                How absurd to swallow a bird!
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a cat.
                Imagine that, to swallow a cat!
                She swallowed the cat to catch the bird.
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a dog.
                What a hog, to swallow a dog!
                She swallowed the dog to catch the cat.
                She swallowed the cat to catch the bird.
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a goat.
                Just opened her throat and swallowed a goat!
                She swallowed the goat to catch the dog.
                She swallowed the dog to catch the cat.
                She swallowed the cat to catch the bird.
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a cow.
                I don't know how she swallowed a cow!
                She swallowed the cow to catch the goat.
                She swallowed the goat to catch the dog.
                She swallowed the dog to catch the cat.
                She swallowed the cat to catch the bird.
                She swallowed the bird to catch the spider that wriggled and jiggled and \
                tickled inside her.
                She swallowed the spider to catch the fly.
                I don't know why she swallowed the fly. Perhaps she'll die.
                
                I know an old lady who swallowed a horse.
                She's dead, of course!""";

        assertThat(foodChain.verses(startVerse, endVerse)).isEqualTo(expected);
    }
}
