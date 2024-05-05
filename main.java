import java.util.InputMismatchException;
import java.util.Scanner;

public class MancalaGame {
    private final Scanner scanner = new Scanner(System.in);
    private MancalaBoard board;
    private boolean player1Turn;

    public MancalaGame() {
        board = new MancalaBoard();
        player1Turn = true;
    }

    // Show start menu with exception handling
    private void showStartMenu() {
        while (true) {
            try {
                System.out.println("\nWelcome to Mancala!");
                System.out.println("1. Start Game");
                System.out.println("2. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.println("");
                    showSubmenu();
                    break;
                } else if (choice == 2) {
                    System.out.println("Goodbye!");
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    // Show submenu to either start or get the rules, with exception handling
    private void showSubmenu() {
        while (true) {
            try {
                System.out.println("1. Start Game Now");
                System.out.println("2. View Rules");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                if (choice == 1) {
                    //System.out.println("");
                    playGame();
                    break;
                } else if (choice == 2) {
                    showRules();
                    // Handling invalid input after showing rules
                    while (true) {
                        try {
                            System.out.print("Press 1 to start the game: ");
                            choice = scanner.nextInt();
                            if (choice == 1) {
                                playGame();
                                break;
                            } else {
                                System.out.println("Invalid choice. Please enter 1.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a number.");
                            scanner.nextLine(); // Clear the invalid input
                        }
                    }
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    // Show rules
    private void showRules() {
        System.out.println("\nMancala Rules:");
        System.out.println("Setup:");
        System.out.println("• The Mancala board has two rows of six pits, with a larger store (Mancala) at each end.");
        System.out.println("• Each of the 12 pits gets 4 stones, and the stores start empty.");
        System.out.println("• Player 1 controls the 6 pits in the bottom row. Player 2 controls the 6 pits in the top row.");

        System.out.println("\nObjective:");
        System.out.println("• Collect the most stones in your store by the end of the game.");

        System.out.println("\nGame Play:");
        System.out.println("Starting a Turn:");
        System.out.println("• On your turn, pick up all the stones from one of your six pits.");

        System.out.println("\nDistributing Stones:");
        System.out.println("• Moving counterclockwise, drop one stone in each pit and your store.");
        System.out.println("• Skip your opponent’s store but continue placing stones in the pits on their side.");

        System.out.println("\nSpecial Rules:");
        System.out.println("• If the last stone you drop ends up in your store, you get another turn.");
        System.out.println("• If the last stone you drop lands in an empty pit on your side and the opponent pit has stones, you capture those stones and your stone, putting them into your store.");

        System.out.println("\nEnding the Game:");
        System.out.println("• The game ends when one player has no stones left in any of their pits.");
        System.out.println("• The remaining stones on the opponent’s side are collected in their store.");
        System.out.println("• The player with the most stones in their store wins.\n");
    }


    // Play a game of Mancala
    private void playGame() {
        board = new MancalaBoard(); // Reset the board
        player1Turn = true;
        while (!board.isGameOver()) {
            System.out.println("");
            board.displayBoard();
            int move = getMove();
            boolean extraTurn = board.playTurn(move, player1Turn ? 1 : 2);
            if (!extraTurn) {
                player1Turn = !player1Turn; // Switch turn only if no extra turn
            }
        }
        displayFinalScore();
        askToPlayAgain();
    }

    // Prompt player for a move
    private int getMove() {
        // Determine the appropriate prompt message for the current player
        String promptMessage;
        if (player1Turn) {
            promptMessage = "\nIt is player 1's turn. Choose a pocket (1-6): ";
        } else {
            promptMessage = "\nIt is player 2's turn. Choose a pocket (8-13): ";
        }
        System.out.print(promptMessage);

        int move = 0;
        boolean validMove = false;

        // Validate the move input
        while (!validMove) {
            try {
                move = scanner.nextInt() - 1;
                if (board.isValidMove(move, player1Turn ? 1 : 2)) {
                    validMove = true;
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return move;
    }

    // Display the final score and determine the winner
    private void displayFinalScore() {
        System.out.println("Game Over!");
        int player1Score = board.getPlayerScore(1);
        int player2Score = board.getPlayerScore(2);

        System.out.println("Player 1 Score: " + player1Score);
        System.out.println("Player 2 Score: " + player2Score);

        if (player1Score > player2Score) {
            System.out.println("\nPlayer 1 Wins!");
        } else if (player2Score > player1Score) {
            System.out.println("\nPlayer 2 Wins!");
        } else {
            System.out.println("\nIt's a tie!");
        }
    }

    // Ask if the user wants to play again
    private void askToPlayAgain() {
        while (true) {
            try {
                System.out.print("Do you want to play again? (yes=1/no=2): ");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    playGame();
                    break;
                } else if (choice == 2) {
                    System.out.println("Thank you for playing. Goodbye!");
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    // Start the game
    public static void main(String[] args) {
        MancalaGame game = new MancalaGame();
        game.showStartMenu();
    }
}
