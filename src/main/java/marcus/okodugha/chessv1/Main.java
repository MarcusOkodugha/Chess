package marcus.okodugha.chessv1;

import marcus.okodugha.chessv1.Model.*;
import marcus.okodugha.chessv1.View.BoardView;

import java.io.IOException;
import java.util.ArrayList;

import static marcus.okodugha.chessv1.Model.Board.column;
import static marcus.okodugha.chessv1.Model.Board.row;

public class Main {
    public static void main(String[] args) throws IOException {


        Board board = new Board();
        BoardView boardView = new BoardView(board);
        GameLoop gameLoop = new GameLoop(board,boardView);
        board.movePiece(0,0,3,3);


        board.showBoardInTerminal();
    }
}
