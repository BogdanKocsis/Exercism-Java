import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GrepToolTest {
    private GrepTool grepTool;

    @BeforeEach
    public void setUp() throws IOException {
        List<String> iliadText = Arrays.asList(
            "Achilles sing, O Goddess! Peleus' son;",
            "His wrath pernicious, who ten thousand woes",
            "Caused to Achaia's host, sent many a soul",
            "Illustrious into Ades premature,",
            "And Heroes gave (so stood the will of Jove)",
            "To dogs and to all ravening fowls a prey,",
            "When fierce dispute had separated once",
            "The noble Chief Achilles from the son",
            "Of Atreus, Agamemnon, King of men."
        );
        writeToFile("iliad.txt", iliadText);

        List<String> midsummerNightText = Arrays.asList(
            "I do entreat your grace to pardon me.",
            "I know not by what power I am made bold,",
            "Nor how it may concern my modesty,",
            "In such a presence here to plead my thoughts;",
            "But I beseech your grace that I may know",
            "The worst that may befall me in this case,",
            "If I refuse to wed Demetrius."
        );
        writeToFile("midsummer-night.txt", midsummerNightText);

        List<String> paradiseLostText = Arrays.asList(
            "Of Mans First Disobedience, and the Fruit",
            "Of that Forbidden Tree, whose mortal tast",
            "Brought Death into the World, and all our woe,",
            "With loss of Eden, till one greater Man",
            "Restore us, and regain the blissful Seat,",
            "Sing Heav'nly Muse, that on the secret top",
            "Of Oreb, or of Sinai, didst inspire",
            "That Shepherd, who first taught the chosen Seed"
        );
        writeToFile("paradise-lost.txt", paradiseLostText);

        grepTool = new GrepTool();
    }

    @AfterEach
    public void tearDown() throws IOException {
        deleteFile("iliad.txt");
        deleteFile("midsummer-night.txt");
        deleteFile("paradise-lost.txt");
    }

    @Test
    @DisplayName("One file, one match, no flags")
    public void oneFileOneMatchNoFlags() {
        String expected = "Of Atreus, Agamemnon, King of men.";

        String actual = grepTool.grep(
            "Agamemnon",
            Collections.emptyList(),
            Collections.singletonList("iliad.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, one match, print line numbers flag")
    public void oneFileOneMatchPrintLineNumbersFlag() {
        String expected = "2:Of that Forbidden Tree, whose mortal tast";

        String actual = grepTool.grep(
            "Forbidden",
            Collections.singletonList("-n"),
            Collections.singletonList("paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, one match, case-insensitive flag")
    public void oneFileOneMatchCaseInsensitiveFlag() {
        String expected = "Of that Forbidden Tree, whose mortal tast";

        String actual = grepTool.grep(
            "FORBIDDEN",
            Collections.singletonList("-i"),
            Collections.singletonList("paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, one match, print file names flag")
    public void oneFileOneMatchPrintFileNamesFlag() {
        String expected = "paradise-lost.txt";

        String actual = grepTool.grep(
            "Forbidden",
            Collections.singletonList("-l"),
            Collections.singletonList("paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, one match, match entire lines flag")
    public void oneFileOneMatchEntireLinesFlag() {
        String expected = "With loss of Eden, till one greater Man";

        String actual = grepTool.grep(
            "With loss of Eden, till one greater Man",
            Collections.singletonList("-x"),
            Collections.singletonList("paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, one match, multiple flags")
    public void oneFileOneMatchMultipleFlags() {
        String expected = "9:Of Atreus, Agamemnon, King of men.";

        String actual = grepTool.grep(
            "OF ATREUS, Agamemnon, KIng of MEN.",
            Arrays.asList("-n", "-i", "-x"),
            Collections.singletonList("iliad.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, several matches, no flags")
    public void oneFileSeveralMatchesNoFlags() {
        String expected = """
                Nor how it may concern my modesty,
                But I beseech your grace that I may know
                The worst that may befall me in this case,""";

        String actual = grepTool.grep(
            "may",
            Collections.emptyList(),
            Collections.singletonList("midsummer-night.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, several matches, print line numbers flag")
    public void oneFileSeveralMatchesPrintLineNumbersFlag() {
        String expected = """
                3:Nor how it may concern my modesty,
                5:But I beseech your grace that I may know
                6:The worst that may befall me in this case,""";

        String actual = grepTool.grep(
            "may",
            Collections.singletonList("-n"),
            Collections.singletonList("midsummer-night.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, several matches, match entire lines flag")
    public void oneFileSeveralMatchesMatchEntireLineFlag() {
        String expected = "";

        String actual = grepTool.grep(
            "may",
            Collections.singletonList("-x"),
            Collections.singletonList("midsummer-night.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, several matches, case-insensitive flag")
    public void oneFileSeveralMatchesCaseInsensitiveFlag() {
        String expected = "Achilles sing, O Goddess! Peleus' son;\n"
            + "The noble Chief Achilles from the son";

        String actual = grepTool.grep(
            "ACHILLES",
            Collections.singletonList("-i"),
            Collections.singletonList("iliad.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, several matches, inverted flag")
    public void oneFileSeveralMatchesInvertedFlag() {
        String expected = """
                Brought Death into the World, and all our woe,
                With loss of Eden, till one greater Man
                Restore us, and regain the blissful Seat,
                Sing Heav'nly Muse, that on the secret top
                That Shepherd, who first taught the chosen Seed""";

        String actual = grepTool.grep(
            "Of",
            Collections.singletonList("-v"),
            Collections.singletonList("paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, no matches, various flags")
    public void oneFileNoMatchesVariousFlags() {
        String expected = "";

        String actual = grepTool.grep(
            "Gandalf",
            Arrays.asList("-n", "-l", "-x", "-i"),
            Collections.singletonList("iliad.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, one match, file flag takes precedence over line flag")
    public void oneFileOneMatchFileFlagTakesPrecedenceOverLineFlag() {
        String expected = "iliad.txt";

        String actual = grepTool.grep(
            "ten",
            Arrays.asList("-n", "-l"),
            Collections.singletonList("iliad.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("One file, several matches, inverted and match entire lines flags")
    public void oneFileSeveralMatchesInvertedAndMatchEntireLinesFlags() {
        String expected = """
                Achilles sing, O Goddess! Peleus' son;
                His wrath pernicious, who ten thousand woes
                Caused to Achaia's host, sent many a soul
                And Heroes gave (so stood the will of Jove)
                To dogs and to all ravening fowls a prey,
                When fierce dispute had separated once
                The noble Chief Achilles from the son
                Of Atreus, Agamemnon, King of men.""";

        String actual = grepTool.grep(
            "Illustrious into Ades premature,",
            Arrays.asList("-x", "-v"),
            Collections.singletonList("iliad.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, one match, no flags")
    public void multipleFilesOneMatchNoFlags() {
        String expected = "iliad.txt:Of Atreus, Agamemnon, King of men.";

        String actual = grepTool.grep(
            "Agamemnon",
            Collections.emptyList(),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, several matches, no flags")
    public void multipleFilesSeveralMatchesNoFlags() {
        String expected = """
                midsummer-night.txt:Nor how it may concern my modesty,
                midsummer-night.txt:But I beseech your grace that I may know
                midsummer-night.txt:The worst that may befall me in this case,""";

        String actual = grepTool.grep(
            "may",
            Collections.emptyList(),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, several matches, print line numbers flag")
    public void multipleFilesSeveralMatchesPrintLineNumbersFlag() {
        String expected = """
                midsummer-night.txt:5:But I beseech your grace that I may know
                midsummer-night.txt:6:The worst that may befall me in this case,
                paradise-lost.txt:2:Of that Forbidden Tree, whose mortal tast
                paradise-lost.txt:6:Sing Heav'nly Muse, that on the secret top""";

        String actual = grepTool.grep(
            "that",
            Collections.singletonList("-n"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, one match, print file names flag")
    public void multipleFilesOneMatchPrintFileNamesFlag() {
        String expected = "iliad.txt\n"
            + "paradise-lost.txt";

        String actual = grepTool.grep(
            "who",
            Collections.singletonList("-l"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, several matches, case-insensitive flag")
    public void multipleFilesSeveralMatchesCaseInsensitiveFlag() {
        String expected = """
                iliad.txt:Caused to Achaia's host, sent many a soul
                iliad.txt:Illustrious into Ades premature,
                iliad.txt:And Heroes gave (so stood the will of Jove)
                iliad.txt:To dogs and to all ravening fowls a prey,
                midsummer-night.txt:I do entreat your grace to pardon me.
                midsummer-night.txt:In such a presence here to plead my thoughts;
                midsummer-night.txt:If I refuse to wed Demetrius.
                paradise-lost.txt:Brought Death into the World, and all our woe,
                paradise-lost.txt:Restore us, and regain the blissful Seat,
                paradise-lost.txt:Sing Heav'nly Muse, that on the secret top""";

        String actual = grepTool.grep(
            "TO",
            Collections.singletonList("-i"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, several matches, inverted flag")
    public void multipleFilesSeveralMatchesInvertedFlag() {
        String expected = """
                iliad.txt:Achilles sing, O Goddess! Peleus' son;
                iliad.txt:The noble Chief Achilles from the son
                midsummer-night.txt:If I refuse to wed Demetrius.""";

        String actual = grepTool.grep(
            "a",
            Collections.singletonList("-v"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, one match, match entire lines flag")
    public void multipleFilesOneMatchEntireLinesFlag() {
        String expected = "midsummer-night.txt:But I beseech your grace that I may know";

        String actual = grepTool.grep(
            "But I beseech your grace that I may know",
            Collections.singletonList("-x"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, one match, multiple flags")
    public void multipleFilesOneMatchMultipleFlags() {
        String expected = "paradise-lost.txt:4:With loss of Eden, till one greater Man";

        String actual = grepTool.grep(
            "WITH LOSS OF EDEN, TILL ONE GREATER MAN",
            Arrays.asList("-n", "-i", "-x"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, no matches, various flags")
    public void multipleFilesNoMatchesVariousFlags() {
        String expected = "";

        String actual = grepTool.grep(
            "Frodo",
            Arrays.asList("-n", "-l", "-x", "-i"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, several matches, file flag takes precedence over line number flag")
    public void multipleFilesSeveralMatchesFileFlagTakesPrecedenceOverLineNumberFlag() {
        String expected = "iliad.txt\n"
            + "paradise-lost.txt";

        String actual = grepTool.grep(
            "who",
            Arrays.asList("-n", "-l"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Multiple files, several matches, inverted and match entire lines flags")
    public void multipleFilesSeveralMatchesInvertedAndMatchEntireLinesFlags() {
        String expected = """
                iliad.txt:Achilles sing, O Goddess! Peleus' son;
                iliad.txt:His wrath pernicious, who ten thousand woes
                iliad.txt:Caused to Achaia's host, sent many a soul
                iliad.txt:And Heroes gave (so stood the will of Jove)
                iliad.txt:To dogs and to all ravening fowls a prey,
                iliad.txt:When fierce dispute had separated once
                iliad.txt:The noble Chief Achilles from the son
                iliad.txt:Of Atreus, Agamemnon, King of men.
                midsummer-night.txt:I do entreat your grace to pardon me.
                midsummer-night.txt:I know not by what power I am made bold,
                midsummer-night.txt:Nor how it may concern my modesty,
                midsummer-night.txt:In such a presence here to plead my thoughts;
                midsummer-night.txt:But I beseech your grace that I may know
                midsummer-night.txt:The worst that may befall me in this case,
                midsummer-night.txt:If I refuse to wed Demetrius.
                paradise-lost.txt:Of Mans First Disobedience, and the Fruit
                paradise-lost.txt:Of that Forbidden Tree, whose mortal tast
                paradise-lost.txt:Brought Death into the World, and all our woe,
                paradise-lost.txt:With loss of Eden, till one greater Man
                paradise-lost.txt:Restore us, and regain the blissful Seat,
                paradise-lost.txt:Sing Heav'nly Muse, that on the secret top
                paradise-lost.txt:Of Oreb, or of Sinai, didst inspire
                paradise-lost.txt:That Shepherd, who first taught the chosen Seed""";

        String actual = grepTool.grep(
            "Illustrious into Ades premature,",
            Arrays.asList("-x", "-v"),
            Arrays.asList("iliad.txt", "midsummer-night.txt", "paradise-lost.txt")
        );

        assertThat(actual).isEqualTo(expected);
    }

    private void writeToFile(String filename, List<String> contents) throws IOException {
        Path file = Paths.get(filename);
        Files.write(file, contents, StandardCharsets.UTF_8);
    }

    private void deleteFile(String filename) throws IOException {
        Files.deleteIfExists(Paths.get(filename));
    }
}
