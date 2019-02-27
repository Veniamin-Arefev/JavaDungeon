/*
Created by Veniamin_arefev
Special for School Project
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.logging.Logger;

public class Window extends JFrame implements ActionListener {
    private SettingsWindow.difficult diff = SettingsWindow.difficult.easy;
    private boolean debug = false;
    private int seed;
    private SettingsWindow settingsWindow;
    private Game game;
    private Timer timer;
    private JPanel testPanel;
    private JButton settings;
    private JButton play;
    public static final StatsDialog STATS_DIALOG = new StatsDialog();

    public Window() throws HeadlessException {
        super("MainWindow");
//        STATS_DIALOG.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        settings = new JButton("Settings");
        play = new JButton("Play");
        JPanel forButtons = new JPanel(new FlowLayout());
        forButtons.add(play);
        play.setActionCommand(play.getText());
        play.addActionListener(this);
        forButtons.add(settings);
        settings.setActionCommand("Open settingsGUI");
        settings.addActionListener(this);
        play.addKeyListener(keyAdapter);
        settings.addKeyListener(keyAdapter);
        add(forButtons);
        setSize(1000, 500);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        play.requestFocusInWindow();

        timer = new Timer(100, this);
        timer.setActionCommand("Timer");
        timer.setRepeats(true);

        JPanel debuggPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(debuggPanel);
        testPanel = new JPanel(new GridLayout(20, 1));
        debuggPanel.add(testPanel);
        testPanel.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        File mainFile = new File("");
        File[] files = new File("").getAbsoluteFile().listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().toLowerCase().contains("javadungeon") && (files[i].getName().toLowerCase().contains(".jar"))) {
                mainFile = files[i];
            }
        }
        testPanel.add(new Label(mainFile.getAbsolutePath()));
        File file = new File(getClass().getResource("/Textures/Wall.png").getPath());
        testPanel.add(new Label(file.getAbsolutePath()));
        testPanel.setVisible(false);
        testPanel.add(new JLabel("Настя - самая лучшая какаха"));
        testPanel.add(new JLabel("All code written by Veniamin Arefev"));
        testPanel.add(new JLabel("specifically for the project Java Dungeon"));
    }
    private void debug() {
        if (debug) testPanel.setVisible(true);
        else testPanel.setVisible(false);
    }
    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                if (settings.isFocusOwner()) settings.doClick();
                if (play.isFocusOwner()) play.doClick();
            }
        }
    };

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Open settingsGUI": {
                Logger.getLogger(MainClassOfThisGame.class.getName()).info("Setting opened");
                if (settingsWindow == null) settingsWindow = new SettingsWindow(this);
                settingsWindow.setVisible(true);
                settingsWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        super.windowDeactivated(e);
                        diff = settingsWindow.getDiff();
                        debug = settingsWindow.getDebug();
                        debug();
                        if (!(settingsWindow.getSeed() == 0)) seed = settingsWindow.getSeed();
                        Logger.getLogger(MainClassOfThisGame.class.getName()).info("Setting closed");
                    }
                });
            }
                break;
            case "Play": {
                dispose();
                    game = new Game(diff, seed);
                    game.setSize(game.getWidth() - 10, game.getHeight() - 10);
                    game.setResizable(false);
                    STATS_DIALOG.setVisible(false);
                    timer.restart();
            }
                break;
            case "Timer" : {
                if (!game.isVisible()) {
                    setVisible(true);
//                    STATS_DIALOG.setVisible(true);
                    game = null;
                    timer.stop();
                }
            }
            break;
        }
    }
}
