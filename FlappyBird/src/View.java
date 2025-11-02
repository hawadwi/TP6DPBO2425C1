import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel{
    int width = 360;
    int height = 640;
    private Logic logic;
    Image bgImage;

    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        bgImage = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();
        setFocusable(true);
        addKeyListener(logic);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
//        Graphics2D g2 = (Graphics2D) g;

        if (bgImage != null) {
            g.drawImage(bgImage, 0, 0, width, height, null);
        } else {
            g.setColor(new Color(168, 241, 255));
            g.fillRect(0, 0, width, height);
        }

        Player player = logic.getPlayer();
        if (player != null){
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        ArrayList<Pipe> pipes = logic.getPipes();
        if (pipes != null){
            for (int i = 0; i < pipes.size(); i++){
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        g.setColor(Color.black);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        FontMetrics fm = g.getFontMetrics();
        String scoreText = "Score:" + logic.getScore();
        g.drawString(scoreText, 20, 40);

        if (logic.getGameOver() != 0){
            g.setColor(Color.black);
            g.setFont(new Font("Georgia", Font.BOLD, 40));
            fm = g.getFontMetrics();
            String gameOverText = "GAME OVER!";
            int x = (width - fm.stringWidth(gameOverText)) / 2;
            int y = ((height - fm.getHeight()) / 2) + fm.getAscent();
            g.drawString(gameOverText, x, y);

            g.setFont(new Font("Verdana", Font.PLAIN, 20));
            fm = g.getFontMetrics();
            String restartText = "Press R to Restart";
            int restartX = (width - fm.stringWidth(restartText)) / 2;
            int restartY = y + 60;
            g.drawString(restartText, restartX, restartY);
        }

    }

}
