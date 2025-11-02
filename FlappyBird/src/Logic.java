import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener{
    int frameWidth = 360;
    int frameHeight = 640;
    int playerStartPosX = frameWidth/2;
    int playerStartPosY = frameHeight/2;
    int playerWidth = 34;
    int playerHeight = 24;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    View view ;
    Image birdImage;
    Player player;

    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    int gameOver = 0;
    int gameStart = 0;
    int score = 0;


    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    int pipeVelocityX = -2;

    public Logic(){
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<>();

        pipesCooldown = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ActionEvent) {
                System.out.println("Pipa");
                placePipes();
            }
        });
        gameLoop = new Timer(1000/60, this);
    }

    public void setView(View view){
        this.view = view;
    }

    public Player getPlayer(){
        return player;
    }

    public ArrayList<Pipe> getPipes(){
        return pipes;
    }

    public int getGameOver(){
        return gameOver;
    }

    public int getScore(){
        return score;
    }

    public void startGame(){
        if (gameStart == 0){
            gameStart = 1;
            pipesCooldown.start();
            gameLoop.start();
            System.out.println("GAME STARTED!");
        }
    }

    public void placePipes(){
        int randomPosY = (int) (pipeStartPosY - pipeHeight/4 - Math.random() * (pipeHeight/2));
        int openingSpace = frameWidth/2;

        Pipe upperpipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperpipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth,
                pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public boolean isPlayerOutOfBounds(){
        return player.getPosY() + player.getHeight() > frameHeight;
    }

    public boolean checkCollisionWithPipes(){
        Rectangle playerBounds = new Rectangle(player.getPosX(), player.getPosY(),
                player.getWidth(), player.getHeight());

        for (Pipe pipe : pipes){
            Rectangle pipeBounds = new Rectangle(pipe.getPosX(), pipe.getPosY(),
                    pipe.getWidth(), pipe.getHeight());

            // If player rectangle intersects with pipe rectangle, collision detected
            if (playerBounds.intersects(pipeBounds)){
                return true;
            }
        }
        return false;
    }

    public void checkPipePassed(){
        for (Pipe pipe : pipes){
            // Jika pipe belum counted dan posisi pipe sudah lewat dari tengah player
            if (!pipe.isPassed() && pipe.getPosX() + pipe.getWidth() < player.getPosX()){
                pipe.setPassed(true);
                // Hanya increment score sekali per pair pipa (lower pipe saja)
                // Karena setiap pair terdiri dari upper dan lower pipe
                // Kita hanya count ketika lower pipe yang terlewati
                ArrayList<Pipe> pipes = this.pipes;
                int pipeIndex = pipes.indexOf(pipe);
                if (pipeIndex % 2 == 1){ // index ganjil = lower pipe
                    score++;
                    System.out.println("Score: " + score);
                }
            }
        }
    }

    public void endGame(){
        gameOver = 1;
        gameLoop.stop();
        pipesCooldown.stop();
        System.out.println("GAME OVER!");
    }

    public void restart(){
        if (gameOver != 0){
            gameOver = 0;
            player.setPosX(playerStartPosX);
            player.setPosY(playerStartPosY);
            player.setVelocityY(0);
            pipes.clear();

            pipesCooldown = new Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ActionEvent) {
                    System.out.println("Pipa");
                    placePipes();
                }
            });
            pipesCooldown.start();
            gameLoop.start();
            System.out.println("GAME RESTARTED!");
        }
    }

    public void move(){
        if (gameStart == 0 || gameOver != 0){
            return;
        }
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for (int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);
        }

        checkPipePassed();

        if (isPlayerOutOfBounds()){
            endGame();
        } else if (checkCollisionWithPipes()){
            endGame();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        move();
        if (view != null){
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_UP && gameStart != 0 && gameOver == 0){
            player.setVelocityY(-10);
        }
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver != 0){
            restart();
        }
    }
    public void keyReleased(KeyEvent e){}
}
