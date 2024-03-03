package cs.nyuad.csuh3260.tictactoe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs.nyuad.csuh3260.tictactoe.board.Board;
import cs.nyuad.csuh3260.tictactoe.board.Board.Player;
import cs.nyuad.csuh3260.tictactoe.board.ScoreBoard;


public class TicTacToeGameTest {

    private TicTacToeGame game;

    @BeforeEach
    public void setUp() {
        game = new TicTacToeGame();
    }

    @Test
    public void testPromptNextPlayer() {
        // Set up output stream to capture printed output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Player X's turn
        game.board.setCurrentPlayer(Player.X);
        game.promptNextPlayer();
        assertEquals("It's player X's turn. Please enter the coordinates of your next move as x,y: ".trim(), outContent.toString().trim());

        // Reset the output stream
        outContent.reset();

        // Player O's turn
        game.board.setCurrentPlayer(Player.O);
        game.promptNextPlayer();
        assertEquals("It's player O's turn. Please enter the coordinates of your next move as x,y: ".trim(), outContent.toString().trim());

        // Reset the output stream
        outContent.reset();


        //Player null
        // Mock the Board class to simulate a finished game
        Board mockBoard = mock(Board.class);
        when(mockBoard.getCurrentPlayer()).thenReturn(Player.X); // return a valid Player instance
        game.board = mockBoard;
        // Call the method to be tested
        game.promptNextPlayer();
        // Check the printed output
        assertEquals("It's player null's turn. Please enter the coordinates of your next move as x,y: ".trim(), outContent.toString().trim());
        // Reset the output stream
        outContent.reset();

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


        // Test invalid input
        input = "invalid\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(null, game.board.getWinner());

        // Test unexpected input leading to game termination after a move
        input = "0,0\nunexpected\n";
        in = new ByteArrayInputStream(input.getBytes());
        game.playGame(in);
        assertEquals(null, game.board.getWinner());

        // Set up input stream for out of bounds input
        input = "3,3\n0,0\n0,1\n1,1\n0,2\n2,2\nno\n";
        in = new ByteArrayInputStream(input.getBytes());
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