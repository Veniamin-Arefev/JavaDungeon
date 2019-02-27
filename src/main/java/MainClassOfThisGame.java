/*
Created by Veniamin_arefev
Special for School Project
 */
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;



class MainClassOfThisGame {
    private static final Logger logger = Logger.getLogger(MainClassOfThisGame.class.getName());
    public static void main(String[] args) {
        try {
            LogManager.getLogManager().readConfiguration(MainClassOfThisGame.class.getResourceAsStream("logging.properties"));
            logger.info("Hello world");
            Window window = new Window();
            window.setVisible(true);
            window.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    Logger.getLogger("MainClassOfThisGame").info("System Exit");
                }
            });
        } catch (Exception e) {
            Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, e, String::new);
        }
    }
}