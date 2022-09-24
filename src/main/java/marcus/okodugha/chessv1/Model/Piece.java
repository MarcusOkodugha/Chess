package marcus.okodugha.chessv1.Model;

public class Piece {
    private final Color color;
    private final PieceType pieceType;
    public final int imageIndex;

    public Piece(Color color, PieceType pieceType,int imageIndex) {
        this.color = color;
        this.pieceType = pieceType;
        this.imageIndex= imageIndex;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public Color getColor() {
        return color;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public String toString() {
        return color+""+pieceType;
    }
}
