package marcus.okodugha.chessv1;

import marcus.okodugha.chessv1.Model.*;
import marcus.okodugha.chessv1.Model.Infinity.Infinity;
import marcus.okodugha.chessv1.View.BoardView;

import java.io.IOException;
import java.util.ArrayList;

import static marcus.okodugha.chessv1.Model.Board.column;
import static marcus.okodugha.chessv1.Model.Board.row;

public class Main {
    public static void main(String[] args) throws IOException {

        Board board = new Board();//one instance
        Rules rules = new Rules(board);//can be multiple instance
//        Infinity infinity = new Infinity(board,Color.WHITE);
//        Infinity infinity2 = new Infinity(board,Color.BLACK);
        BoardView boardView = new BoardView(board,rules);//one instance

//        GameState gameState = new GameState(board);
//        gameState.initGameStateList();






//        board.showBoardInTerminal(gameState.gameStateList.get(0));
    }
}
