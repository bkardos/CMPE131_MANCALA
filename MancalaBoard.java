public class MancalaBoard {
    public static final int TOTAL_PITS = 12;
    public static final int PITS_PER_PLAYER = 6;
    public static final int INITIAL_STONES = 4;
    public static final int PLAYER_1_MANALA = 6;
    public static final int PLAYER_2_MANALA = 13;

    private int[] board;

    // Initialize the board
    public MancalaBoard() {
        board = new int[14];
        // Initialize pockets with 4 stones each, excluding manalas
        for (int i = 0; i < 14; i++) {
            if (i != PLAYER_1_MANALA && i != PLAYER_2_MANALA) {
                board[i] = INITIAL_STONES;
            }
        }
    }

    // Display the board with formatted alignment
    public void displayBoard() {
        System.out.println("          13    12    11    10    9     8          <--- Pit #'s");
        System.out.println("---------------------------------------------------------------");

        System.out.print("P2:  ");
        for (int i = PLAYER_2_MANALA - 1; i > PITS_PER_PLAYER; i--) {
            System.out.print(String.format("    %2d", board[i]));
        }
        System.out.println();

        System.out.println(String.format("      %2d -------------------------------- %2d", board[PLAYER_2_MANALA], board[PLAYER_1_MANALA]));

        System.out.print("P1:  ");
        for (int i = 0; i < PITS_PER_PLAYER; i++) {
            System.out.print(String.format("    %2d", board[i]));
        }
        System.out.println();
        System.out.println("---------------------------------------------------------------");
        System.out.println("          1     2     3     4     5     6          <--- Pit #'s");
    }

    // Play a turn for a player
    public boolean playTurn(int pitIndex, int currentPlayer) {
        int stones = board[pitIndex];
        board[pitIndex] = 0;
        int currentIndex = pitIndex;

        while (stones > 0) {
            currentIndex = (currentIndex + 1) % board.length;

            // Skip the opponent's manala
            if ((currentPlayer == 1 && currentIndex == PLAYER_2_MANALA) ||
                    (currentPlayer == 2 && currentIndex == PLAYER_1_MANALA)) {
                continue;
            }

            board[currentIndex]++;
            stones--;
        }

        // Check if last stone landed in player's manala for extra turn
        boolean extraTurn = (currentPlayer == 1 && currentIndex == PLAYER_1_MANALA) ||
                (currentPlayer == 2 && currentIndex == PLAYER_2_MANALA);

        // Capture stones if last stone lands in an empty pit owned by the player
        captureStones(currentIndex, currentPlayer);

        return extraTurn;
    }

    // Capture stones for a player
    private void captureStones(int currentIndex, int currentPlayer) {
        boolean isPlayer1Pit = currentPlayer == 1 && currentIndex >= 0 && currentIndex < PITS_PER_PLAYER;
        boolean isPlayer2Pit = currentPlayer == 2 && currentIndex > PITS_PER_PLAYER && currentIndex < PLAYER_2_MANALA;

        if (isPlayer1Pit || isPlayer2Pit) {
            // Check if the last stone landed in an empty pit
            if (board[currentIndex] == 1) {
                int oppositeIndex = 12 - currentIndex;
                int capture = board[oppositeIndex];
                board[oppositeIndex] = 0;

                if (currentPlayer == 1) {
                    board[PLAYER_1_MANALA] += capture + 1;
                    board[currentIndex] = 0;
                } else {
                    board[PLAYER_2_MANALA] += capture + 1;
                    board[currentIndex] = 0;
                }
            }
        }
    }

    // Check if the move is valid
    public boolean isValidMove(int pitIndex, int currentPlayer) {
        // Adjust bounds to include pocket 13 as valid for Player 2
        boolean isPlayer1Pit = pitIndex >= 0 && pitIndex < PITS_PER_PLAYER;
        boolean isPlayer2Pit = pitIndex > PITS_PER_PLAYER && pitIndex <= PLAYER_2_MANALA - 1;

        return pitIndex >= 0 && pitIndex < board.length && board[pitIndex] != 0 &&
                ((currentPlayer == 1 && isPlayer1Pit) ||
                        (currentPlayer == 2 && isPlayer2Pit));
    }

    // Check if the game is over
    public boolean isGameOver() {
        boolean player1Empty = true;
        boolean player2Empty = true;

        for (int i = 0; i < PITS_PER_PLAYER; i++) {
            if (board[i] != 0) {
                player1Empty = false;
            }
            if (board[i + PITS_PER_PLAYER + 1] != 0) {
                player2Empty = false;
            }
        }

        return player1Empty || player2Empty;
    }

    // Get the score of a player
    public int getPlayerScore(int player) {
        int score = (player == 1) ? board[PLAYER_1_MANALA] : board[PLAYER_2_MANALA];

        for (int i = 0; i < PITS_PER_PLAYER; i++) {
            if (player == 1) {
                score += board[i];
            } else {
                score += board[i + PITS_PER_PLAYER + 1];
            }
        }

        return score;
    }
}
