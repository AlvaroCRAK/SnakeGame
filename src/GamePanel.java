import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.Arrays;
import java.util.Scanner;

public class GamePanel extends JPanel implements ActionListener{
	
    static int cont = 0;
    static final int LIMIT = 3;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 24;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;

    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;

    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    char directionB;
    int limit = 0;
    int temp = 0;

    boolean running = false;
    Timer timer;
    Random random;

    int color1;
    int color2;
    int color3;
	
    GamePanel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(new Color(23, 23, 23));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        random = new Random();
        startGame();
    }
	
    public void startGame(){
        newApple();
        newColor();
        direction = 'R';
        Arrays.fill(x, 0);
        Arrays.fill(y, 0);
        //bodyParts = 6;
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
	
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
	
    public void draw(Graphics g){
        if (running) {
            /*for(int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++){
                g.drawLine(i *  UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }*/
            g.setColor(new Color(191, 0, 86));
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for(int i = 0; i < bodyParts; i++){
                if(i == 0){
                    g.setColor(new Color(color1, color2, color3));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else{
                    //g.setColor(new Color(45, 180, 0));
                    g.setColor(new Color(color1, color2, color3));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(new Color(239, 189, 255));
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            int a = LIMIT - cont;
            g.drawString("Score: " + applesEaten + ", Lives: " + a, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten + ", Lives: " + a)) / 2, g.getFont().getSize());

        }
        else if(cont == LIMIT) gameOver(g);

    }
	
    public void newColor(){
        color1 = random.nextInt(256);
        color2 = random.nextInt(256);
        color3 = random.nextInt(256);
    }
	
    public void newApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
	
    public void move() {

        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
	
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts ++;
            applesEaten ++;
            newApple();
            newColor();
        }
    }
	
    public void checkCollisions(){
        //Body
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
                cont ++;
            }
        }
        //Left
        if(x[0] < 0){
            running = false;
            cont ++;
        }
        //Right
        if(x[0] > SCREEN_WIDTH - 1){
            running = false;
            cont ++;
        }
        //Ceil
        if(y[0] < 0){
            running = false;
            cont ++;
        }
        //Floor
        if(y[0] > SCREEN_HEIGHT - 1){
            running = false;
            cont ++;
        }
        /*if(!running){
            timer.stop();
        }*/
    }
	
    public void gameOver(Graphics g){
        //Score
        g.setColor(new Color(color1, color2, color3));
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
        //Game Over TEXT
        g.setColor(new Color(color1, color2, color3));
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);
    }
	
    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            limit = 0;
            move();
            checkApple();
            checkCollisions();
        }else if(cont < LIMIT){
                timer.stop();
                startGame();

        }else if(cont == LIMIT) timer.stop();
        repaint();
    }
	
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            if(limit < 1){
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        if(direction != 'R'){
                            direction = 'L';
                            limit++;
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(direction != 'L'){
                            direction = 'R';
                            limit++;
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(direction != 'D'){
                            direction = 'U';
                            limit++;
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(direction != 'U'){
                            direction = 'D';
                            limit++;
                        }
                        break;
                }
            }
        }
    }
}
