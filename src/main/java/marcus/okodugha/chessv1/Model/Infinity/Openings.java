package marcus.okodugha.chessv1.Model.Infinity;

import marcus.okodugha.chessv1.Model.Color;
import marcus.okodugha.chessv1.Model.Move;

import java.util.ArrayList;

public class Openings {//an opening is a sequence off moves
    private ArrayList<Move> opening;
    private int nrOfOpeningMoves=-1;

    public Openings(Color color) {

        this.opening = new ArrayList<Move>();
        if (color==Color.WHITE)initLondonWhite();
        if (color==Color.BLACK)initLondonBlack();

    }

    public Openings(OpeningType openingType){
        this.opening = new ArrayList<Move>();
        switch (openingType){
            case LondonWhite -> initLondonWhite();
            case LondonBlack -> initLondonBlack();
        }
    }


    private void initLondonWhite() {
        //London system for White
        opening.add(new Move(3,6,3,4));
        opening.add(new Move(2,7,5,4));
        opening.add(new Move(2,6,2,5));
        opening.add(new Move(6,7,5,5));
        opening.add(new Move(4,6,4,5));
        opening.add(new Move(5,7,3,5));
        opening.add(new Move(1,7,3,6));
        opening.add(new Move(4,7,7,7));
    }
    private void initLondonBlack() {
        //London system for Black
        opening.add(new Move(3,1,3,3));
        opening.add(new Move(2,0,5,3));
        opening.add(new Move(2,1,2,2));
        opening.add(new Move(6,0,5,2));
        opening.add(new Move(4,1,4,2));
        opening.add(new Move(5,0,3,2));
        opening.add(new Move(1,0,3,1));
        opening.add(new Move(4,0,7,0));
    }



    public ArrayList<Move> getOpening() {
        return opening;
    }

    public Move getOpeningMove(int index) {
        if (index>=opening.size()){
            return null;
        }
        return opening.get(index);
    }

    public Move getNextOpeningMove(){
        nrOfOpeningMoves++;
        return opening.get(nrOfOpeningMoves);
    }

}

enum OpeningType {
    LondonBlack, LondonWhite;

}
