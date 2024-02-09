package cs.nyuad.csuh3260.tictactoe;

import cs.nyuad.csuh3260.tictactoe.board.Board;
import cs.nyuad.csuh3260.tictactoe.board.exceptions.InvalidMoveException;
import cs.nyuad.csuh3260.tictactoe.board.exceptions.GameFinishedException;
import cs.nyuad.csuh3260.tictactoe.board.ScoreBoard;

import java.util.Scanner;

/**
 * Created by snadi on 2018-07-18.
 */
public class TicTacToeGame {

    private Board board;

    public TicTacToeGame() {
        board = new Board();
    }

    public void promptNextPlayer() {
        switch (board.getCurrentPlayer()) {
            case X:
                System.out.println("It's player " + board.getSymbol(board.getCurrentPlayer())
                        + "'s turn. Please enter the coordinates of your next move as x,y: ");
                break;
            case O:
                System.out.println("It's player " + board.getSymbol(board.getCurrentPlayer())
                        + "'s turn. Please enter the coordinates of your next move as x,y: ");
                break;

        }
    }

    public void playGame() {

        Scanner keyboardScanner = new Scanner(System.in);

        while (board.getWinner() == null) {
            board.printBoard();
            promptNextPlayer();
            String line = keyboardScanner.nextLine();
            String input[] = line.split(",");
            try {
                board.playMove(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            } catch (InvalidMoveException e) {
                System.out.println("Invalid coordinates. Try again");
                promptNextPlayer();
            } catch (GameFinishedException e) {
                System.out.println("Game finished: " + e.getMessage());
                break;
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                return;
            }
        }

        board.printBoard();
        board.printScores();
        System.out.println("Player " + board.getWinner() + " has won the game!");

        // Prompt the user whether they want to play another game
        System.out.println("Do you want to play another game? (yes/no)");
        String playAgain = keyboardScanner.nextLine().toLowerCase();

        if ("yes".equals(playAgain)) {

            board = new Board();
            playGame();
        } else {
            // display scoreboard here
            keyboardScanner.close();
        }
        keyboardScanner.close();
    }

    public static void main(String args[]) {
        TicTacToeGame game = new TicTacToeGame();
        game.playGame();
    }
}
