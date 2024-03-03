package cs.nyuad.csuh3260.tictactoe;

import java.io.InputStream;
import java.util.Scanner;

import cs.nyuad.csuh3260.tictactoe.board.Board;
import cs.nyuad.csuh3260.tictactoe.board.ScoreBoard;
import cs.nyuad.csuh3260.tictactoe.board.exceptions.GameFinishedException;
import cs.nyuad.csuh3260.tictactoe.board.exceptions.InvalidMoveException;

/**
 * Created by snadi on 2018-07-18.
 */
public class TicTacToeGame {

    Board board;
    private ScoreBoard scoreboard; // to keep track of scores
    private Scanner scanner;

    public TicTacToeGame() {
        this.scanner = new Scanner(System.in);
        board = new Board();
        scoreboard = new ScoreBoard(); 
    }

    public void promptNextPlayer() {
        switch (board.getCurrentPlayer()) {
            case X:
                System.out.println("It's player " + board.getSymbol(board.getCurrentPlayer()) + "'s turn. Please enter the coordinates of your next move as x,y: ");
                break;
            case O:
                System.out.println("It's player " + board.getSymbol(board.getCurrentPlayer()) + "'s turn. Please enter the coordinates of your next move as x,y: ");
                break;
            default:
                System.out.println("No Player");
                break;

        }
        
    }

    public void playGame(InputStream inputStream) {

        Scanner keyboardScanner = new Scanner(inputStream);
        //board = new Board();
        boolean keepPlaying = true;
        while (keepPlaying) {
            board = new Board();
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
            scoreboard.updateScores(board.getWinner()); // update scores when a player wins
            scoreboard.printScores(); // print scores after each game

            System.out.println("Player " + board.getWinner() + " has won the game!");

            // Prompt the user whether they want to play another game
            System.out.println("Do you want to play another game? (yes/no)");
            String playAgain = keyboardScanner.nextLine().toLowerCase();
            keepPlaying = "yes".equals(playAgain);

        }
        keyboardScanner.close();
}

    public static void main(String args[]) {
        TicTacToeGame game = new TicTacToeGame();
        game.playGame(System.in);
    }
    public ScoreBoard getScoreboard() {
        return scoreboard;
    }


}
