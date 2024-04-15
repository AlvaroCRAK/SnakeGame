import javax.swing.*;

public class GameFrame extends JFrame {


    GameFrame() {
        GamePanel gamePanel = new GamePanel();
        this.add(gamePanel);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        ImageIcon image = new ImageIcon("icon.png");
        this.setIconImage(image.getImage());
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }
}