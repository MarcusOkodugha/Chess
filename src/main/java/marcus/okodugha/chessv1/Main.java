package marcus.okodugha.chessv1;

import marcus.okodugha.chessv1.Model.Color;
import marcus.okodugha.chessv1.Model.Piece;
import marcus.okodugha.chessv1.Model.PieceType;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import static marcus.okodugha.chessv1.Model.Board.getBoardInstance;
import static marcus.okodugha.chessv1.View.BoardView.getViewInstance;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {


//        Board board = new Board();//one instance
        getBoardInstance().initBoard();//single instance
        getViewInstance().initBoardView();//single instance







    }

}
