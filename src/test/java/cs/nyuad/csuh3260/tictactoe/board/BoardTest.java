package cs.nyuad.csuh3260.tictactoe.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs.nyuad.csuh3260.tictactoe.board.exceptions.GameFinishedException;
import cs.nyuad.csuh3260.tictactoe.board.exceptions.InvalidMoveException;

public class BoardTest {

    Board board;

    @BeforeEach
    private void initializeBoard() {
        board = new Board();
    }

    @Test
    public void playingMoveOutOfBoardThrowsInvalidMoveException() throws InvalidMoveException {
        assertThrows(InvalidMoveException.class, () -> {
            board.playMove(-1, 0);
        });
        assertThrows(InvalidMoveException.class, () -> {
            board.playMove(3, 0);
        });
        assertThrows(InvalidMoveException.class, () -> {
            board.playMove(0, -1);
        });
        assertThrows(InvalidMoveException.class, () -> {
            board.playMove(0, 3);
        });
    }

    @Test
    public void testPlayMoveOnBoundaries() throws Exception {
        // Test upper-left corner
        board.playMove(0, 0);
        assertEquals(Board.Player.X, board.getPlayerAtPos(0, 0));

        // Test lower-right corner
        board.playMove(2, 2);
        assertEquals(Board.Player.O, board.getPlayerAtPos(2, 2));
    }

    @Test
    public void testPlayMoveOnEmptyBoard() throws Exception {
        // Test playing moves on an empty board
        // Play moves on different positions
        board.playMove(0, 0);
        assertEquals(Board.Player.X, board.getPlayerAtPos(0, 0));

        board.playMove(1, 1);
        assertEquals(Board.Player.O, board.getPlayerAtPos(1, 1));

        board.playMove(2, 2);
        assertEquals(Board.Player.X, board.getPlayerAtPos(2, 2));

        board.playMove(0, 2);
        assertEquals(Board.Player.O, board.getPlayerAtPos(0, 2));

        board.playMove(1, 0);
        assertEquals(Board.Player.X, board.getPlayerAtPos(1, 0));

        // Ensure that other positions remain empty
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((i == 0 && j == 0) || (i == 1 && j == 1) || (i == 2 && j == 2) || (i == 0 && j == 2)
                        || (i == 1 && j == 0)) {
                    continue; // Skip positions where moves were played
                }
                assertEquals(Board.Player.NONE, board.getPlayerAtPos(i, j));
            }
        }
    }

    @Test
    public void testPlayMoveOnOccupiedBoard() throws Exception {
        // Test playing moves on a board with some squares already occupied by different
        // players

        // Play initial moves to occupy some squares
        board.playMove(0, 0); // X
        board.playMove(1, 1); // O
        board.playMove(2, 2); // X

        // Play moves on already occupied squares
        assertThrows(InvalidMoveException.class, () -> board.playMove(0, 0)); // Trying to play on already occupied
                                                                              // square
        assertThrows(InvalidMoveException.class, () -> board.playMove(1, 1)); // Trying to play on already occupied
                                                                              // square
        assertThrows(InvalidMoveException.class, () -> board.playMove(2, 2)); // Trying to play on already occupied
                                                                              // square
    }

    @Test
    public void testTieAndPlayMoveWhenTied() throws Exception {
        // Play moves to fill the board without any player winning
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (row == 2 && col == 2) {
                    assertThrows(GameFinishedException.class, () -> board.playMove(2, 2));
                } else {
                    board.playMove(row, col);
                }
            }
        }
        assertTrue(board.isTie());

        // Attempt to play another move
        assertThrows(InvalidMoveException.class, () -> board.playMove(0, 0));
    }

    @Test
    public void testWinningHorizontally() throws Exception {
        // Test winning horizontally
        board.playMove(0, 0); // X
        board.playMove(1, 0); // O
        board.playMove(0, 1); // X
        board.playMove(1, 1); // O
        board.playMove(0, 2); // X (should win)

        assertTrue(board.hasWon(0, 2));
        assertEquals(Board.Player.X, board.getWinner());
        assertEquals(board.getWinner(), board.getCurrentPlayer());
    }

    @Test
    public void testWinningVertically() throws Exception {
        // Test winning vertically
        board.playMove(0, 0); // X
        board.playMove(0, 1); // O
        board.playMove(1, 0); // X
        board.playMove(1, 1); // O
        board.playMove(2, 0); // X (should win)

        assertTrue(board.hasWon(2, 0));
        assertEquals(Board.Player.X, board.getWinner());
    }

    @Test
    public void testWinningDiagonally() throws Exception {
        // Test winning diagonally
        board.playMove(0, 0); // X
        board.playMove(0, 1); // O
        board.playMove(1, 1); // X
        board.playMove(1, 2); // O
        board.playMove(2, 2); // X (should win)

        assertTrue(board.hasWon(2, 2));
        assertEquals(Board.Player.X, board.getWinner());
    }

    @Test
    public void testGetSymbol() {
        // Test getSymbol method for player X
        assertEquals("X", board.getSymbol(Board.Player.X));

        // Test getSymbol method for player O
        assertEquals("O", board.getSymbol(Board.Player.O));

        // Test getSymbol method for an empty square
        assertEquals(" ", board.getSymbol(Board.Player.NONE));
    }

    @Test
    public void testPlayerAtPos() throws Exception {
        // Play moves to set up the board
        board.playMove(0, 0); // X
        board.playMove(0, 1); // O
        board.playMove(0, 2); // X

        // Verify getPlayerAtPos for various positions
        assertEquals(Board.Player.X, board.getPlayerAtPos(0, 0));
        assertEquals(Board.Player.O, board.getPlayerAtPos(0, 1));
        assertEquals(Board.Player.X, board.getPlayerAtPos(0, 2));
        assertEquals(Board.Player.NONE, board.getPlayerAtPos(2, 2));
    }

    @Test
    public void testGetWinner() throws Exception {
        // Play moves to set up the board
        board.playMove(0, 0); // X
        board.playMove(0, 1); // O
        board.playMove(0, 2); // X
        board.playMove(1, 0); // O
        board.playMove(1, 1); // X
        board.playMove(2, 0); // O
        board.playMove(1, 2); // X
        assertFalse(board.hasWon(2, 0)); // No winner yet
        board.playMove(2, 1); // O

        // Verify getWinner
        assertNull(board.getWinner());

        // Play the move that will make X win
        board.playMove(2, 2);
        assertTrue(board.hasWon(2, 2)); // X has won
        assertEquals(Board.Player.X, board.getWinner());
    }

    @Test
    public void testGetCurrentPlayer() throws Exception {
        board.playMove(2, 1); // X
        assertEquals(Board.Player.O, board.getCurrentPlayer());

        board.playMove(2, 2); // O
        assertEquals(Board.Player.X, board.getCurrentPlayer());
    }

}