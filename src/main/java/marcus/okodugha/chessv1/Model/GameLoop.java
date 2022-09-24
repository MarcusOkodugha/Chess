package marcus.okodugha.chessv1.Model;

import marcus.okodugha.chessv1.View.BoardView;

import java.awt.event.MouseEvent;

import static java.awt.MouseInfo.getPointerInfo;

public class GameLoop {
    public GameLoop(Board board, BoardView boardView) {
        loop(board,boardView);
    }

    public void loop(Board board, BoardView boardView){
            boardView.showBoard();
        while (true){


        }
    }
}
