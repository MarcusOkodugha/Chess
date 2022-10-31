package marcus.okodugha.chessv1.View;

import marcus.okodugha.chessv1.Model.*;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

import static marcus.okodugha.chessv1.Model.Board.emptyPiece;
import static marcus.okodugha.chessv1.Model.Board.getBoardInstance;

public class Sound {
    public static void makeSound(Piece destPiece) {
        Board board = getBoardInstance();
        String moveType = "normalMove";
        if (destPiece==emptyPiece) moveType="normalMove";
        if (destPiece.getPieceType().getValue()>0) moveType="captureMove";
        if (destPiece.getPieceType().getValue()==9) moveType="queenCapture";
        if (board.whiteKingIsInCheck||board.blackKingIsInCheck) moveType="check";

        String soundFileName = switch (moveType) {
            case "normalMove" -> "C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\moveSound.wav";
            case "captureMove" -> "C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\captureSound.wav";
            case "check" -> "C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\kingInCheckSound.wav";
            case "queenCapture" ->"C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\shortAhhh.wav";
            default -> "C:\\JavaProgram\\ChessV1\\src\\main\\java\\marcus\\okodugha\\chessv1\\resources\\moveSound.wav";
        };

        playSound(soundFileName);
    }

    private static void playSound(String soundFileName){
        try {
            Clip clip;
            AudioInputStream sound;
            File file = new File(soundFileName);
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
            clip.start();
        } catch (LineUnavailableException ex) {
            System.out.println(ex);;
        } catch (IOException ex) {
            System.out.println(ex);;
        } catch (UnsupportedAudioFileException e) {
            System.out.println(e);;
        }
    }



}
