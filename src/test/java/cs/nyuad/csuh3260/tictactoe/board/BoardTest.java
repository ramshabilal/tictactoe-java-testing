package cs.nyuad.csuh3260.tictactoe.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs.nyuad.csuh3260.tictactoe.board.exceptions.InvalidMoveException;

public class BoardTest {

    Board board;

    @BeforeEach
    private void initializeBoard() {
        board = new Board();
    }

    @Test
    public void boardInitializationCreatesThreeByThreeMatrix() throws Exception {
        // Use reflection to access the private method initBoard()
        Method initBoardMethod = Board.class.getDeclaredMethod("initBoard");
        initBoardMethod.setAccessible(true);
        initBoardMethod.invoke(board);

        // Use reflection to access the private field board
        Field boardField = Board.class.getDeclaredField("board");
        boardField.setAccessible(true);
        Board.Player[][] actualBoard = (Board.Player[][]) boardField.get(board);

        // Verify that all cells are initialized to Player.NONE
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(Board.Player.NONE, actualBoard[i][j]);
            }
        }
    }
}