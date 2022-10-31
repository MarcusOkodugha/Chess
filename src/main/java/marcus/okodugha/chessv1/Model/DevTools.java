package marcus.okodugha.chessv1.Model;

import java.util.ArrayList;

public class DevTools extends Board{
    public static boolean devMode;
    public static boolean autoBlack;


    public void showBoardInTerminal(Board board){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.format("%-15s", board.getBoard().get(i).get(j));
            }
            System.out.println("\n");
        }
    }


    public void show2DListInTerminal(ArrayList<ArrayList<Piece>> lists){
        System.out.println("\n");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.format("%-15s",lists.get(i).get(j));
            }
            System.out.println("\n");
        }
    }

    public void copyBoard(ArrayList<ArrayList<Piece>> fromBoard,ArrayList<ArrayList<Piece>> tooBoard){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                tooBoard.get(i).set(i,fromBoard.get(i).get(j));
            }
        }
        System.out.println("copy board done");
//        show2DListInTerminal(tooBoard);
    }


}
