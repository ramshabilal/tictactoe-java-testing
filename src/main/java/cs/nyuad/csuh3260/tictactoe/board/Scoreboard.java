package cs.nyuad.csuh3260.tictactoe.board;

public class ScoreBoard {
    int xWins;
    int oWins;
    int ties; 

    public ScoreBoard() {
        int xWins = 0;
        int oWins = 0;
        int ties = 0; 
    }

    public void update Scores (Board.Player winner) {
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

    public void printScores() {
        System.out.println("X wins: " + xWins);
        System.out.println("O wins: " + oWins);
        System.out.println("Ties: " + ties);
    }
}