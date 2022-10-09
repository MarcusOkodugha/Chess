package marcus.okodugha.chessv1.Model.Infinity;

import marcus.okodugha.chessv1.Model.Move;

import java.util.ArrayList;

public class Openings {//an opening is a sequence off moves
    ArrayList<Move> opening;

    public Openings() {

        this.opening = new ArrayList<Move>();
        initOpening();

    }

    private void initOpening() {
        //London system for Black
        opening.add(new Move(3,1,3,3));
        opening.add(new Move(2,0,5,3));
        opening.add(new Move(2,1,2,2));
        opening.add(new Move(4,1,4,2));
        opening.add(new Move(6,0,5,2));
        opening.add(new Move(5,0,3,2));
        opening.add(new Move(1,0,3,1));
        opening.add(new Move(4,0,7,0));


    }

    public ArrayList getOpening() {
        return opening;
    }

    public Move getOpeningMove(int index) {
        return opening.get(index);
    }

}
