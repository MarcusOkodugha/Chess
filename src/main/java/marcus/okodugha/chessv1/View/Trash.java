package marcus.okodugha.chessv1.View;

import java.awt.*;

import static marcus.okodugha.chessv1.Model.Board.column;

public class Trash {
//    JPanel pn = new JPanel() {//shows board
//            @Override
//            public void paint(Graphics g) {
////                System.out.println(frame.getBounds());//shows size of frame
////                boolean white = true;
////                for (int i = 0; i < 8; i++) {
////                    for (int j = 0; j < 8; j++) {
////                        if (white) {
////                            g.setColor(Color.white);
////                        } else {
////                            g.setColor(Color.PINK);
////                        }
////                        g.fillRect(i * 64, j * 64, 64, 64);
////                        white = !white;
////                    }
////                    white = !white;
////                }
//
////                flipWhite=true;
////                flipBlack = true;
//                for (int i = 0; i < row; i++) {//showpieces on board
//                    for (int j = 0; j < column; j++) {
//                        if (board.getBoard().get(i).get(j).getImageIndex() != 12) {//if imige index = 11 dont draw
//
//                            if (board.getBoard().get(i).get(j).getColor()== marcus.okodugha.chessv1.Model.Color.WHITE){
//                                if (flipWhite){
//                                    g.drawImage(imgs[board.getBoard().get(i).get(j).getImageIndex()], j * 64+64, i * 64+64, -64,-64,this);//whitePiece
//                                }else {
//                                    g.drawImage(imgs[board.getBoard().get(i).get(j).getImageIndex()], j * 64, i * 64, this);//whitePiece
//                                }
//                            }
//
//                            if (board.getBoard().get(i).get(j).getColor()== marcus.okodugha.chessv1.Model.Color.BLACK){
//                                if (flipBlack){
//                                    g.drawImage(imgs[board.getBoard().get(i).get(j).getImageIndex()], j * 64+64, i * 64+64,-64,-64, this);//blackPiece
//                                }else {
//                                    g.drawImage(imgs[board.getBoard().get(i).get(j).getImageIndex()], j * 64, i * 64, this);//blackPiece
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        };



//     for(int i = 0; i < row; i++) {
//        for(int j = 0; j < column; j++) {
//            if(e.getSource() ==(numberTiles[i][j]) ) {
//                System.out.println("pressed "+i+" "+j);
//                // we got the row and column - now call the appropriate controller method
//                numberTiles[i][j].setBackground(Color.RED);
//                srcRow=i;
//                srcColumn=j;
//            }
//            if (pressCount==2){
//                board.movePiece(0,0,3,3);
////                            board.movePiece(srcRow,srcColumn,i,j);
////                            showBoard();
////                        sudokuController.handelTileClick(i,j);
////                        return;
//            }
//        }
//    }

    //    public ArrayList<Move> getAllLegalMovesForBlack() {
//        allLegalBlackMoves.clear();
//        for (int i = 0; i < row; i++) {
//            for (int j = 0; j < column; j++) {
//                for (int k = 0; k < row; k++) {
//                    for (int l = 0; l < column; l++) {
//                        if (board.get(i).get(j).getColor()==Color.BLACK){
//                            if (rules.isLegalMove(j,i,l,k,board.get(i).get(j))){
//                                allLegalBlackMoves.add(new Move(j,i,l,k));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return allLegalBlackMoves;
//    }


}
