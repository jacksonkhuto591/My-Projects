import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Program extends JPanel implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;

    public static final int SCREEN_WIDTH = 600;
    public static final int SCREEN_HEIGHT = 400;
    public static final int BLOCK_SIZE = 10;
    public static final int BLOCKS_WIDE = SCREEN_WIDTH / BLOCK_SIZE;
    public static final int BLOCKS_HIGH = SCREEN_HEIGHT / BLOCK_SIZE;

    private boolean gameOver = false;
    private int snakeLength = 1;
    private int foodX = 0;
    private int foodY = 0;
    private int snakeX = BLOCKS_WIDE / 2;
    private int snakeY = BLOCKS_HIGH / 2;
    private int snakeXVelocity = 1;
    private int snakeYVelocity = 0;
    private ArrayList<Point> snake = new ArrayList<Point>();

    private Timer timer;

    public Program() {
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(60, this);
        timer.start();
        generateFood();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Program());
        frame.setVisible(true);
    }

    public void paint(Graphics g) {
        if(!gameOver){
             g.setColor(Color.BLACK);
             g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

             g.setColor(Color.GREEN);
             g.fillRect(foodX * BLOCK_SIZE, foodY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);

             g.setColor(Color.WHITE);
             for (Point point : snake) {
                 g.fillRect(point.x * BLOCK_SIZE, point.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
             }

              g.setColor(Color.red);
               g.setFont(new Font("Arial",Font.BOLD,20));
             g.drawString("Your Score: " + (snakeLength - 1), 0, 15);
            }
            else{
                GameOver(g);
            }
    }

    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            moveSnake();
            checkCollision();
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT && snakeXVelocity != 1) {
            snakeXVelocity = -1;
            snakeYVelocity = 0;
        }

        if (key == KeyEvent.VK_RIGHT && snakeXVelocity != -1) {
            snakeXVelocity = 1;
            snakeYVelocity = 0;
        }

        if (key == KeyEvent.VK_UP && snakeYVelocity != 1) {
            snakeXVelocity = 0;
            snakeYVelocity = -1;
        }

        if (key == KeyEvent.VK_DOWN && snakeYVelocity != -1) {
            snakeXVelocity = 0;
            snakeYVelocity = 1;
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public void keyTyped(KeyEvent e) {

    }



 private void generateFood() {
    Random random = new Random();
    foodX = random.nextInt(BLOCKS_WIDE);
    foodY = random.nextInt(BLOCKS_HIGH);

    // Make sure the food position is not occupied by the snake
    for (Point point : snake) {
        if (point.x == foodX && point.y == foodY) {
            generateFood();
            return;
        }
    }
}
private void moveSnake() {
    snakeX += snakeXVelocity;
    snakeY += snakeYVelocity;

    if (snakeX < 0 || snakeX >= BLOCKS_WIDE || snakeY < 0 || snakeY >= BLOCKS_HIGH) {
        gameOver = true;
        return;
    }

    snake.add(new Point(snakeX, snakeY));
    if (snake.size() > snakeLength) {
        snake.remove(0);
    }

    if (snakeX == foodX && snakeY == foodY) {
        snakeLength++;
        generateFood();
    }
}
private void checkCollision() {
    // Check if the snake has collided with itself
    for (int i = 0; i < snake.size() - 1; i++) {
        if (snake.get(i).x == snakeX && snake.get(i).y == snakeY) {
            gameOver = true;
            break;
        }
    }
}

 public void GameOver(Graphics g){
     //display game over text
     g.setColor(Color.WHITE);
     g.setFont(new Font("Arial",Font.BOLD,75));
     FontMetrics metric=getFontMetrics(g.getFont());
     g.drawString("Game Over",(SCREEN_WIDTH-metric.stringWidth("Game Over"))/2,SCREEN_HEIGHT/2);
 }
    
}





