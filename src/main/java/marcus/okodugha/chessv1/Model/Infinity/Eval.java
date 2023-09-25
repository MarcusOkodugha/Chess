package marcus.okodugha.chessv1.Model.Infinity;

import marcus.okodugha.chessv1.Model.Board;
import marcus.okodugha.chessv1.Model.Color;
import marcus.okodugha.chessv1.Model.Piece;

import java.util.ArrayList;

import static marcus.okodugha.chessv1.Model.Board.column;
import static marcus.okodugha.chessv1.Model.Board.row;

public class Eval {
    private int eval;
//    private Board board;

//    public Eval(Board board) {
//        this.board=board;
//    }

    public Eval() {

    }

    public int getEval(ArrayList<ArrayList<Piece>>  board) {
        eval=0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                 if (board.get(i).get(j).getColor()== Color.WHITE){//color is white eval +
                    eval=eval+board.get(i).get(j).getPieceType().getValue();
                }
                if (board.get(i).get(j).getColor()== Color.BLACK){//color is black eval -
                    eval=eval-board.get(i).get(j).getPieceType().getValue();
                }
            }
        }
        System.out.println("Eval : "+eval);//todo remove
        return eval;
    }
}
