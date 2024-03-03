package cs.nyuad.csuh3260.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs.nyuad.csuh3260.tictactoe.board.Board;
import cs.nyuad.csuh3260.tictactoe.board.Board.Player;
import cs.nyuad.csuh3260.tictactoe.board.ScoreBoard;


public class TicTacToeGameTest {

    private TicTacToeGame game;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() 
    {
        game = new TicTacToeGame();
    }

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void resetStreams() {
        System.setOut(originalOut);
    }

        @Test
        public void testPromptNextPlayerForX() {
            TicTacToeGame game = new TicTacToeGame();
            game.board.setCurrentPlayer(Board.Player.X); // Assuming you have a way to set the current player
            game.promptNextPlayer();
            assertTrue(outContent.toString().contains("It's player X's turn. Please enter the coordinates of your next move as x,y:"));
        }

        @Test
        public void testPromptNextPlayerForO() {
            TicTacToeGame game = new TicTacToeGame();
            game.board.setCurrentPlayer(Board.Player.O);
            game.promptNextPlayer();
            assertTrue(outContent.toString().contains("It's player O's turn. Please enter the coordinates of your next move as x,y:"));
        }

        @Test
        public void testPromptNextPlayerForNull() {
            TicTacToeGame game = new TicTacToeGame();
            game.board.setCurrentPlayer(Board.Player.NONE);
            game.promptNextPlayer();
            assertTrue(outContent.toString().contains("No Player"));
        }


    

    @Test
    public void testPlayGame() {
        // Set up input stream for a game where X wins
        String input = "0,0\n0,1\n1,1\n0,2\n2,2\nno\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.X, game.board.getWinner());

        // Set up input stream for a game where O wins
        input = "1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.O, game.board.getWinner());

        // Set up input stream for a tie game
        input = "0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(null, game.board.getWinner());

        //user input for a game where the player decides to continue after the game ends
        // Player X wins before and after continuing the game
        input = "0,0\n0,1\n1,1\n1,0\n2,2\nyes\n0,0\n1,1\n0,1\n1,2\n0,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.X, game.board.getWinner());

        // Player O wins before and after continuing the game
        input = "1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nyes\n1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.O, game.board.getWinner());

        // Tie before and player X wins after continuing the game
        input = "0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nyes\n0,0\n0,1\n1,1\n0,2\n2,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.X, game.board.getWinner());

        // Tie before and player O wins after continuing the game
        input = "0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nyes\n1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.O, game.board.getWinner());

        // Tie before and after continuing the game
        input = "0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nyes\n1,0\n0,0\n1,1\n0,1\n0,2\n2,0\n2,1\n1,2\n2,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(null, game.board.getWinner());

        // Player X wins before then player O wins after continuing the game
        input = "0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nyes\n1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.O, game.board.getWinner());

        // Player O wins before then player X wins after continuing the game
        input = "1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nyes\n0,0\n0,1\n1,1\n0,2\n2,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.X, game.board.getWinner());


        // Player X wins before then tie after continuing the game
        input = "0,0\n0,1\n1,1\n0,2\n2,2\nyes\n0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(null, game.board.getWinner());
        
        // Player O wins before then tie after continuing the game
        input = "1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nyes\n0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(null, game.board.getWinner());
    
    }

    @Test
    public void invalid() {
    // Test invalid input
    String input = "invalid\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    game.playGame(in);
    assertEquals(null, game.board.getWinner());
    }


    @Test
    public void unexpected() {
   // Test unexpected input leading to game termination after a move
    String input = "0,0\nunexpected\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    game.playGame(in);
    assertEquals(null, game.board.getWinner());
    }

    @Test
    public void outofbounds() {
    // Set up input stream for out of bounds input
    String input = "3,3\n0,0\n0,1\n1,1\n0,2\n2,2\nno\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    game.playGame(in);
    assertEquals(Player.X, game.board.getWinner());
    }
    

    @Test
    public void outofboundsinmiddle() {
    // Set up input stream for out of bounds input in middle
    String input = "0,0\n0,1\n1,1\n3,2\n0,2\n2,2\nno\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    game.playGame(in);
    assertEquals(Player.X, game.board.getWinner());

    }
    
    // Edge case: Player X wins with the last move
    @Test
    public void testPlayGame_PlayerXWinsLastMove() {
    String input = "1,1\n0,0\n0,1\n0,2\n2,2\n1,2\n1,0\n2,0\n2,1\nno\n";
    InputStream in = new ByteArrayInputStream(input.getBytes());
    game.playGame(in);
    assertEquals(Player.X, game.board.getWinner());
    }

    // Edge case: Player O wins with the last move
    @Test
    public void testPlayGame_PlayerOWinsLastMove() {
        String input = "0,0\n1,0\n0,1\n1,1\n2,0\n2,2\n2,1\n2,2\n1,2\nno/n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.O, game.board.getWinner());
    }
    
    // Player enters invalid coordinates multiple times
    @Test
    public void testPlayGame_InvalidCoordinates() {
        String input = "0,0\n0,0\n0,0\n0,1\n1,1\n0,2\n2,2\nno\n"; 
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(Player.X, game.board.getWinner());
    }

    @Test
    public void testPlayGame_ScoreUpdated_XWins() {
        // Set up input stream for a game where X wins
        String input = "0,0\n0,1\n1,1\n0,2\n2,2\nno\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);

        ScoreBoard scoreboard = game.getScoreboard();
        assertEquals(1, scoreboard.getXWins());
        assertEquals(0, scoreboard.getOWins());
        assertEquals(0, scoreboard.getties());
    }

    @Test
    public void testPlayGame_ScoreUpdated_OWins() {
        // Set up input stream for a game where O wins
        String input = "1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nno\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);

        ScoreBoard scoreboard = game.getScoreboard();
        assertEquals(0, scoreboard.getXWins());
        assertEquals(1, scoreboard.getOWins());
        assertEquals(0, scoreboard.getties());
    }

    @Test
    public void testPlayGame_ScoreUpdated_Tie() {
        // Set up input stream for a tie game
        String input = "0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nno\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);

        ScoreBoard scoreboard = game.getScoreboard();
        assertEquals(0, scoreboard.getXWins());
        assertEquals(0, scoreboard.getOWins());
        assertEquals(1, scoreboard.getties());
    }

    @Test
    public void testPlayGame_ScoreUpdated_Continue_XWins() {
        // Set up input stream for a game where X wins before and after continuing the game
        String input = "0,0\n0,1\n1,1\n1,0\n2,2\nyes\n0,0\n1,1\n0,1\n1,2\n0,2\nno\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);

        ScoreBoard scoreboard = game.getScoreboard();
        assertEquals(2, scoreboard.getXWins());
        assertEquals(0, scoreboard.getOWins());
        assertEquals(0, scoreboard.getties());
    }

    @Test
    public void testPlayGame_ScoreUpdated_Continue_Tie() {
        // Set up input stream for a tie game before and after continuing the game
        String input = "0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nyes\n0,0\n0,1\n0,2\n1,0\n1,1\n2,0\n2,1\n2,2\n1,2\nno\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);

        ScoreBoard scoreboard = game.getScoreboard();
        assertEquals(0, scoreboard.getXWins());
        assertEquals(0, scoreboard.getOWins());
        assertEquals(2, scoreboard.getties());
    }

    @Test
    public void testPlayGame_ScoreUpdated_Continue_OWins() {
        // Set up input stream for a game where O wins before and after continuing the game
        String input = "1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nyes\n1,0\n0,0\n1,1\n0,1\n2,2\n0,2\nno\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);

        ScoreBoard scoreboard = game.getScoreboard();
        assertEquals(0, scoreboard.getXWins());
        assertEquals(2, scoreboard.getOWins());
        assertEquals(0, scoreboard.getties());
    }
    
    

   
}