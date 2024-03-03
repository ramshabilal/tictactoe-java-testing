package cs.nyuad.csuh3260.tictactoe.board;

// ScoreBoard class to keep track of scores
public class ScoreBoard {
    int xWins;
    int oWins;
    int ties; 

    @SuppressWarnings("unused")
    public ScoreBoard() {
        int xWins = 0;
        int oWins = 0;
        int ties = 0; 
    }

    // update scores when a player wins or game is a tie
    public void updateScores (Board.Player winner) {
        if (winner == Board.Player.X) {
            xWins++;
        }
        else if (winner == Board.Player.O) {
            oWins++;
        }
        else if (winner == null){
            ties++;
        }
    }

    // print scores 
    public void printScores() {
        System.out.println("X wins: " + xWins);
        System.out.println("O wins: " + oWins);
        System.out.println("Ties: " + ties);
    }

    // Getter for xWins
    public int getXWins() {
        return xWins;
    }

    // Getter for xWins
    public int getOWins() {
        return oWins;
    }
    // Getter for xWins
    public int getties() {
        return ties;
    }
}