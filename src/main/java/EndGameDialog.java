/*
Created by Veniamin_arefev
Special for School Project
 */
import javax.swing.*;
import java.util.logging.Logger;


public class EndGameDialog extends JDialog{
    public enum type {gameover,gamewin}

    public EndGameDialog(Object o,String string, type type) {
        super((JFrame)o,string);
        JPanel panel = new JPanel();
        add(panel);
        setSize(400,300);
        setLocationRelativeTo(getOwner());
        switch (type) {
            case gameover: {
                panel.add(new JLabel("<html>You just die in fighting<br>You must monitor your health<br>If you want to survive</html>"));
                Logger.getLogger(MainClassOfThisGame.class.getName()).info("Gameover");
            }
                break;
            case gamewin: {
                panel.add(new JLabel("<html>You just win this one<br>My congratulations to you<br>Enjoy game with pass - godpass</html>"));
                Logger.getLogger(MainClassOfThisGame.class.getName()).info("Game win");
            }
                break;
        }
    }
}
