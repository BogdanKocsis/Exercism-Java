import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConnectTest {

    @Test
    @DisplayName("an empty board has no winner")
    public void anEmptyBoardHasNoWinner() {

        //GIVEN
        var board = new String[]{
            ". . . . .",
            " . . . . .",
            "  . . . . .",
            "   . . . . .",
            "    . . . . ."
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.NONE);
    }

    @Test
    @DisplayName("X can win on a 1x1 board")
    public void xCanWinOnA1x1Board() {

        //GIVEN
        var board = new String[]{
            "X"
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.PLAYER_X);

    }

    @Test
    @DisplayName("O can win on a 1x1 board")
    public void oCanWinOnA1x1Board() {

        //GIVEN
        var board = new String[]{
            "O"
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.PLAYER_O);

    }

    @Test
    @DisplayName("only edges does not make a winner")
    public void onlyEdgesDoesNotMakeAWinner() {

        //GIVEN
        var board = new String[]{
            "O O O X",
            " X . . X",
            "  X . . X",
            "   X O O O"
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.NONE);

    }

    @Test
    @DisplayName("illegal diagonal does not make a winner")
    public void illegalDiagonalDoesNotMakeAWinner() {

        //GIVEN
        var board = new String[]{
            "X O . .",
            " O X X X",
            "  O X O .",
            "   . O X .",
            "    X X O O"
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.NONE);

    }

    @Test
    @DisplayName("nobody wins crossing adjacent angles")
    public void nobodyWinsCrossingAdjacentAngles() {

        //GIVEN
        var board = new String[]{
            "X . . .",
            " . X O .",
            "  O . X O",
            "   . O . X",
            "    . . O ."
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.NONE);

    }

    @Test
    @DisplayName("X wins crossing from left to right")
    public void xWinsCrossingFromLeftToRight() {

        //GIVEN
        var board = new String[]{
            ". O . .",
            " O X X X",
            "  O X O .",
            "   X X O X",
            "    . O X ."
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.PLAYER_X);

    }

    @Test
    @DisplayName("O wins crossing from top to bottom")
    public void oWinsCrossingFromTopToBottom() {

        //GIVEN
        var board = new String[]{
            ". O . .",
            " O X X X",
            "  O O O .",
            "   X X O X",
            "    . O X ."
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.PLAYER_O);

    }

    @Test
    @DisplayName("X wins using a convoluted path")
    public void xWinsUsingConvolutedPath() {

        //GIVEN
        var board = new String[]{
            ". X X . .",
            " X . X . X",
            "  . X . X .",
            "   . X X . .",
            "    O O O O O"
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.PLAYER_X);

    }

    @Test
    @DisplayName("X wins using a spiral path")
    public void xWinsUsingASpiralPath() {

        //GIVEN
        var board = new String[]{
            "O X X X X X X X X",
            " O X O O O O O O O",
            "  O X O X X X X X O",
            "   O X O X O O O X O",
            "    O X O X X X O X O",
            "     O X O O O X O X O",
            "      O X X X X X O X O",
            "       O O O O O O O X O",
            "        X X X X X X X X O"
        };
        Connect cut = new Connect(board);

        //WHEN
        var winner = cut.computeWinner();

        //THEN
        assertThat(winner).isEqualTo(Winner.PLAYER_X);

    }
}
