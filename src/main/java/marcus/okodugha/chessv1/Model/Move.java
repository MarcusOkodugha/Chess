package marcus.okodugha.chessv1.Model;

public class Move {
    public int srcX;
    public int srcY;
    public int destX;
    public int destY;

    public Move(int srcX, int srcY, int destX, int destY) {
        this.srcX = srcX;
        this.srcY = srcY;
        this.destX = destX;
        this.destY = destY;
    }

    public Move() {
    }

    @Override
    public String toString() {
        return "Move{" +
                "srcX=" + srcX +
                ", srcY=" + srcY +
                ", destX=" + destX +
                ", destY=" + destY +
                '}';
    }
}
