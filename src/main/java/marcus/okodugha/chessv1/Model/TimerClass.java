package marcus.okodugha.chessv1.Model;

import javax.swing.*;
import java.util.TimerTask;

public class TimerClass  extends TimerTask{
    Board board = Board.getBoardInstance();
        public void run() {
            if (board.isWhiteTurn()){
                board.getInfinityWhite().infinityMove();
            }else {
                board.getInfinityBlack().infinityMove();
            }
        }

}
