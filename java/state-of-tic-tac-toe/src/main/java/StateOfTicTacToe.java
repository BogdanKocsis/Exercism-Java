import java.util.Arrays;

class StateOfTicTacToe {
    public GameState determineState(String[] board) {
        int xCount = (int) Arrays.stream(board).flatMapToInt(String::chars).filter(c -> c == 'X').count();
        int oCount = (int) Arrays.stream(board).flatMapToInt(String::chars).filter(c -> c == 'O').count();
        if (xCount > oCount + 1) throw new IllegalArgumentException("Wrong turn order: X went twice");
        if (oCount > xCount) throw new IllegalArgumentException("Wrong turn order: O started");

        boolean xWins = hasWon(board, 'X'), oWins = hasWon(board, 'O');
        if (xWins && oWins)
            throw new IllegalArgumentException("Impossible board: game should have ended after the game was won");
        if (xWins) return xCount == oCount + 1 ? GameState.WIN : null;
        if (oWins) return xCount == oCount ? GameState.WIN : null;
        return xCount + oCount == 9 ? GameState.DRAW : GameState.ONGOING;
    }

    private static boolean hasWon(String[] board, char p) {
        for (int i = 0; i < 3; i++) {
            if (board[i].chars().allMatch(c -> c == p) || board[0].charAt(i) == p && board[1].charAt(i) == p && board[2].charAt(i) == p)
                return true;
        }
        return board[0].charAt(0) == p && board[1].charAt(1) == p && board[2].charAt(2) == p ||
                board[0].charAt(2) == p && board[1].charAt(1) == p && board[2].charAt(0) == p;
    }
}