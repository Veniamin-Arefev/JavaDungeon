/*
Created by Veniamin_arefev
Special for School Project
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GamePanel extends JPanel {
    private BufferedImage battleGround = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
    private BufferedImage wallImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage wayImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage playerImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage bossImage = new BufferedImage(60,60,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage exitOpenedImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage exitClosedImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
    private BufferedImage trophyImage = new BufferedImage(20,20,BufferedImage.TYPE_INT_ARGB);
    private ArrayList<BufferedImage> enemyFirstImage = new ArrayList<>();
    private ArrayList<BufferedImage> enemySecondImage = new ArrayList<>();
    private ArrayList<BufferedImage> enemyThirdImage = new ArrayList<>();
    private ArrayList<Enemy> enemies;
    private Map map;
    private Graphics2D gf = battleGround.createGraphics();

    public GamePanel(ArrayList<Enemy> arrayList, Map map) {
        enemies = arrayList;
        this.map = map;
        try {
            wallImage = ImageIO.read(getClass().getResource("/Textures/Wall.png"));
            wayImage = ImageIO.read(getClass().getResource("/Textures/Way.png"));
            playerImage = ImageIO.read(getClass().getResource("/Textures/Player.png"));
            exitOpenedImage = ImageIO.read(getClass().getResource("/Textures/Exit/trapdoor_opened.png"));
            exitClosedImage = ImageIO.read(getClass().getResource("/Textures/Exit/trapdoor_closed.png"));
            trophyImage = ImageIO.read(getClass().getResource("/Textures/Exit/trophy.png"));
            bossImage = ImageIO.read(getClass().getResource("/Textures/monstro.png"));
            for (int i = 1;i < 4;i++) {
                enemyFirstImage.add(ImageIO.read(getClass().getResource("/Textures/Enemy/EnemyV1," + i + ".png")));
                enemySecondImage.add(ImageIO.read(getClass().getResource("/Textures/Enemy/EnemyV2," + i + ".png")));
                enemyThirdImage.add(ImageIO.read(getClass().getResource("/Textures/Enemy/EnemyV3," + i + ".png")));
            }
        } catch (Exception e) {
            Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, e,String::new);
        }
        gf.setColor(Color.MAGENTA);
        gf.drawRect(0, 0, 799, 599);
        gf.setColor(Color.BLACK);
    }

    public void resetImage() {
        gf.setColor(Color.BLACK);
        gf.fillRect(0, 0, 800, 600);
    }
    public void drawSome(int who, int x0, int y0) {
        switch (who) {
            case 1: {
                gf.drawImage(wallImage,null,x0*20,y0*20);
                break;
            }
            case 2: {
                gf.drawImage(wayImage,null,x0*20,y0*20);
                break;
            }
            case 3: {
                gf.drawImage(wayImage,null,x0*20,y0*20);
                gf.drawImage(playerImage,null,x0*20,y0*20);
                break;
            }
            case 4: {
                gf.drawImage(wayImage,null,x0*20,y0*20);
                Enemy enemy = enemies.get(enemies.indexOf(new Enemy(x0, y0)));
                switch (enemy.getModel()) {
                    case first:{
                        if (enemy.getHealth() > enemy.getMaxHealth()*2/3)
                            gf.drawImage(enemyFirstImage.get(0),null,x0*20,y0*20);
                        else if (enemy.getHealth() > enemy.getMaxHealth()/3)
                            gf.drawImage(enemyFirstImage.get(1),null,x0*20,y0*20);
                        else
                            gf.drawImage(enemyFirstImage.get(2),null,x0*20,y0*20);
                        break;
                    }
                    case second: {
                        if (enemy.getHealth() > enemy.getMaxHealth()*2/3)
                            gf.drawImage(enemySecondImage.get(0),null,x0*20,y0*20);
                        else if (enemy.getHealth() > enemy.getMaxHealth()/3)
                            gf.drawImage(enemySecondImage.get(1),null,x0*20,y0*20);
                        else
                            gf.drawImage(enemySecondImage.get(2),null,x0*20,y0*20);
                        break;
                    }
                    case third: {
                        if (enemy.getHealth() > enemy.getMaxHealth()*2/3)
                            gf.drawImage(enemyThirdImage.get(0),null,x0*20,y0*20);
                        else if (enemy.getHealth() > enemy.getMaxHealth()/3)
                            gf.drawImage(enemyThirdImage.get(1),null,x0*20,y0*20);
                        else
                            gf.drawImage(enemyThirdImage.get(2),null,x0*20,y0*20);
                        break;
                    }
                    case boss: {
                        gf.drawImage(bossImage, null, x0 * 20, y0 * 20);
                        break;
                    }
                }
                break;
            }
            case 5: {
                gf.drawImage(wayImage,null,x0*20,y0*20);
                if (map.getFloor() == 10) gf.drawImage(trophyImage,null,x0*20,y0*20);
                else if (map.isExitOpened()) gf.drawImage(exitOpenedImage,null,x0*20,y0*20);
                else gf.drawImage(exitClosedImage,null,x0*20,y0*20);
                break;
            }
            default:{
                Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, "ERROR WHILE TRYING TO FIEND PAINTING ID");
            }
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gr = (Graphics2D) g;
        gr.drawImage(battleGround, 0, 0, this);
    }
}
