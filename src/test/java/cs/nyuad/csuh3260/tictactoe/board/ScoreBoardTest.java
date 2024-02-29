package cs.nyuad.csuh3260.tictactoe.board;
import cs.nyuad.csuh3260.tictactoe.board.ScoreBoard;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreBoardTest {

    private ScoreBoard scoreboard;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;

    @BeforeEach
    public void setUp() {
        scoreboard = new ScoreBoard();

        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    // Test if initial scores are all set to zero
    @Test
    public void testInitialScores() {
        assertEquals(0, scoreboard.xWins);
        assertEquals(0, scoreboard.oWins);
        assertEquals(0, scoreboard.ties);
    }

        // Test updating scores when X wins
    @Test
    public void testUpdateScores_XWins() {
        scoreboard.updateScores(Board.Player.X); // X wins
        assertEquals(1, scoreboard.xWins);       // X wins incremented
        assertEquals(0, scoreboard.oWins);       // O wins remain 0
        assertEquals(0, scoreboard.ties);        // Ties remain 0
    }

    // Test updating scores when O wins
    @Test
    public void testUpdateScores_OWins() {
        scoreboard.updateScores(Board.Player.O); // O wins
        assertEquals(0, scoreboard.xWins);       // X wins remain 0
        assertEquals(1, scoreboard.oWins);       // O wins incremented
        assertEquals(0, scoreboard.ties);        // Ties remain 0
    }

    // Test updating scores when the game is a tie
    @Test
    public void testUpdateScores_Tie() {
        scoreboard.updateScores(null);      // Tie
        assertEquals(0, scoreboard.xWins);  // X wins remain 0
        assertEquals(0, scoreboard.oWins);  // O wins remain 0
        assertEquals(1, scoreboard.ties);   // Ties incremented
    }

    // Test updating scores after multiple games
    @Test
    public void testUpdateScores_MultipleGames() {
        // Simulate multiple games with alternating winners and ties
        scoreboard.updateScores(Board.Player.X); // X wins
        scoreboard.updateScores(null); // Tie
        scoreboard.updateScores(Board.Player.O); // O wins
        scoreboard.updateScores(Board.Player.O); // O wins again
        scoreboard.updateScores(null); // Tie
        scoreboard.updateScores(null); // Tie
        scoreboard.updateScores(Board.Player.X); // X wins again
        
        // Verify that the scoreboard has been updated correctly after multiple games
        assertEquals(2, scoreboard.xWins); // Check if X wins count is 2
        assertEquals(2, scoreboard.oWins); // Check if O wins count is 2
        assertEquals(3, scoreboard.ties); // Check if ties count is 3
    }

    @Test
    public void testPrintScores() {
        // Mock PrintStream
        PrintStream mockedPrintStream = mock(PrintStream.class);
        
        // Replace System.out with mocked PrintStream
        System.setOut(mockedPrintStream);
        
        // Call the method that prints scores
        scoreboard.printScores();
        
        // Verify that the correct output was printed
        verify(mockedPrintStream).println("X wins: 0");
        verify(mockedPrintStream).println("O wins: 0");
        verify(mockedPrintStream).println("Ties: 0");
        
        // Reset System.out
        System.setOut(originalOut);
    }

    // Test updating scores with null multiple times
    @Test
    public void testUpdateScoresWithNullMultipleTimes() {
        // Initialize ScoreBoard
        ScoreBoard scoreboard = new ScoreBoard();
        
        // Update scores with null multiple times
        for (int i = 0; i < 10; i++) {
            scoreboard.updateScores(null);
        }

        // Check if ties count is correct
        assertEquals(10, scoreboard.ties);
    }

    // Test updating scores with invalid player
    @Test
    public void testUpdateScores_InvalidPlayer() {
        // Pass an invalid player (NONE) to the updateScores method
        scoreboard.updateScores(Board.Player.NONE);

        // Verify that the scores remain unchanged
        assertEquals(0, scoreboard.xWins);
        assertEquals(0, scoreboard.oWins);
        assertEquals(0, scoreboard.ties);
    }   
}
