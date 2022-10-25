package marcus.okodugha.chessv1.Model;

import static marcus.okodugha.chessv1.Model.Board.column;
import static marcus.okodugha.chessv1.Model.Board.row;

public class DevTools extends Board{


    public void showBoardInTerminal(Board board){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.format("%-15s", board.getBoard().get(i).get(j));
            }
            System.out.println("\n");
        }
    }
}
