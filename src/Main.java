import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
public class Main{
    public static void main(String[] args)throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File file = new File("audio.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        new GameFrame();
        //clip.close();

    }
}