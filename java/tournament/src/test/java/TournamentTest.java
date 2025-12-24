import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TournamentTest {

    private Tournament tournament;

    @BeforeEach
    public void setUp() {
        tournament = new Tournament();
    }

    @Test
    @DisplayName("just the header if no input")
    public void justTheHeaderIfNoInput() {
        assertThat(tournament.printTable())
                .isEqualTo("Team                           | MP |  W |  D |  L |  P\n");
    }

    @Test
    @DisplayName("a win is three points, a loss is zero points")
    public void aWinIsThreePointsALossIsZeroPoints() {
        tournament.applyResults("Allegoric Alaskans;Blithering Badgers;win");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Allegoric Alaskans             |  1 |  1 |  0 |  0 |  3
                                Blithering Badgers             |  1 |  0 |  0 |  1 |  0
                                """);
    }

    @Test
    @DisplayName("a win can also be expressed as a loss")
    public void aWinCanAlsoBeExpressedAsALoss() {
        tournament.applyResults("Blithering Badgers;Allegoric Alaskans;loss");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Allegoric Alaskans             |  1 |  1 |  0 |  0 |  3
                                Blithering Badgers             |  1 |  0 |  0 |  1 |  0
                                """);
    }

    @Test
    @DisplayName("a different team can win")
    public void aDifferentTeamCanWin() {
        tournament.applyResults("Blithering Badgers;Allegoric Alaskans;win");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Blithering Badgers             |  1 |  1 |  0 |  0 |  3
                                Allegoric Alaskans             |  1 |  0 |  0 |  1 |  0
                                """);
    }

    @Test
    @DisplayName("a draw is one point each")
    public void aDrawIsOnePointEach() {
        tournament.applyResults("Allegoric Alaskans;Blithering Badgers;draw");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Allegoric Alaskans             |  1 |  0 |  1 |  0 |  1
                                Blithering Badgers             |  1 |  0 |  1 |  0 |  1
                                """);
    }

    @Test
    @DisplayName("There can be more than one match")
    public void thereCanBeMoreThanOneMatch() {
        tournament.applyResults(
                "Allegoric Alaskans;Blithering Badgers;win\n" +
                        "Allegoric Alaskans;Blithering Badgers;win");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Allegoric Alaskans             |  2 |  2 |  0 |  0 |  6
                                Blithering Badgers             |  2 |  0 |  0 |  2 |  0
                                """);
    }

    @Test
    @DisplayName("There can be more than one winner")
    public void thereCanBeMoreThanOneWinner() {
        tournament.applyResults(
                "Allegoric Alaskans;Blithering Badgers;loss\n" +
                        "Allegoric Alaskans;Blithering Badgers;win");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Allegoric Alaskans             |  2 |  1 |  0 |  1 |  3
                                Blithering Badgers             |  2 |  1 |  0 |  1 |  3
                                """);
    }

    @Test
    @DisplayName("There can be more than two teams")
    public void thereCanBeMoreThanTwoTeams() {
        tournament.applyResults(
                """
                        Allegoric Alas
                        kans;Blithering Badgers;win
                        Blithering Badgers;Courageous Californians;win
                        Courageous Californians;Allegoric Alaskans;loss""");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Allegoric Alaskans             |  2 |  2 |  0 |  0 |  6
                                Blithering Badgers             |  2 |  1 |  0 |  1 |  3
                                Courageous Californians        |  2 |  0 |  0 |  2 |  0
                                """);
    }

    @Test
    @DisplayName("typical input")
    public void typicalInput() {
        tournament.applyResults(
                """
                        Allegoric Alaskans;Blithering Badgers;win
                        Devastating Donkeys;Courageous Californians;draw
                        Devastating Donkeys;Allegoric Alaskans;win
                        Courageous Californians;Blithering Badgers;loss
                        Blithering Badgers;Devastating Donkeys;loss
                        Allegoric Alaskans;Courageous Californians;win""");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Devastating Donkeys            |  3 |  2 |  1 |  0 |  7
                                Allegoric Alaskans             |  3 |  2 |  0 |  1 |  6
                                Blithering Badgers             |  3 |  1 |  0 |  2 |  3
                                Courageous Californians        |  3 |  0 |  1 |  2 |  1
                                """);
    }

    @Test
    @DisplayName("incomplete competition (not all pairs have played)")
    public void incompleteCompetition() {
        tournament.applyResults(
                """
                        Allegoric Alaskans;Blithering Badgers;loss
                        Devastating Donkeys;Allegoric Alaskans;loss
                        Courageous Californians;Blithering Badgers;draw
                        Allegoric Alaskans;Courageous Californians;win""");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Allegoric Alaskans             |  3 |  2 |  0 |  1 |  6
                                Blithering Badgers             |  2 |  1 |  1 |  0 |  4
                                Courageous Californians        |  2 |  0 |  1 |  1 |  1
                                Devastating Donkeys            |  1 |  0 |  0 |  1 |  0
                                """);
    }

    @Test
    @DisplayName("ties broken alphabetically")
    public void tiesBrokenAlphabetically() {
        tournament.applyResults(
                """
                        Courageous Californians;Devastating Donkeys;win
                        Allegoric Alaskans;Blithering Badgers;win
                        Devastating Donkeys;Allegoric Alaskans;loss
                        Courageous Californians;Blithering Badgers;win
                        Blithering Badgers;Devastating Donkeys;draw
                        Allegoric Alaskans;Courageous Californians;draw""");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Allegoric Alaskans             |  3 |  2 |  1 |  0 |  7
                                Courageous Californians        |  3 |  2 |  1 |  0 |  7
                                Blithering Badgers             |  3 |  0 |  1 |  2 |  1
                                Devastating Donkeys            |  3 |  0 |  1 |  2 |  1
                                """);
    }

    @Test
    @DisplayName("ensure points sorted numerically")
    public void pointsSortedNumerically() {
        tournament.applyResults(
                """
                        Devastating Donkeys;Blithering Badgers;win
                        Devastating Donkeys;Blithering Badgers;win
                        Devastating Donkeys;Blithering Badgers;win
                        Devastating Donkeys;Blithering Badgers;win
                        Blithering Badgers;Devastating Donkeys;win""");
        assertThat(tournament.printTable())
                .isEqualTo(
                        """
                                Team                           | MP |  W |  D |  L |  P
                                Devastating Donkeys            |  5 |  4 |  0 |  1 | 12
                                Blithering Badgers             |  5 |  1 |  0 |  4 |  3
                                """);
    }
}
