/*
Created by Veniamin_arefev
Special for School Project
 */
import static java.lang.Integer.valueOf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game extends JFrame implements ActionListener {
    private GamePanel gamePanel;
    private Map map;
    private CheatFrame cheatFrame;
    private Timer checkTimer;
    private JLabel health;
    private JLabel damage;
    private JLabel defence;
    private JProgressBar progressBar;
    private JButton cheatButton;
    private JButton secretButton;
    private JLabel possibleLvlLabel;
    private JLabel lvlsObtainedLabel;
    private JLabel floorLabel;
    private JButton lvlUpBtn;
    private LvlUPDialog lvlUPDialog;
    private JLabel keyLabel;
    public int lvlsObtained = 0;
    public static ImageIcon keyIcon = new ImageIcon();

    public Game(SettingsWindow.difficult diff, int seed) throws HeadlessException {
        super("Game");
        setVisible(true);
        setPreferredSize(new Dimension(1040, 660));
        pack();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        if (seed == 0) {
            map = new Map(diff);
            seed = map.getSeed();
        } else {
            map = new Map(diff, seed);
        }

        gamePanel = new GamePanel(map.getEnemysArray(),map);
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(keyAdapter);
        gamePanel.requestFocusInWindow();
        gamePanel.setLocation(5, 5);
        gamePanel.setSize(800, 600);
        add(gamePanel);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
        infoPanel.setLocation(810, 5);
        infoPanel.setSize(200, 600);
        infoPanel.setBorder(BorderFactory.createDashedBorder(Color.red));
        add(infoPanel);

        JPanel seedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        seedPanel.setBorder(BorderFactory.createTitledBorder("Seed"));
        seedPanel.setMaximumSize(new Dimension(150,50));
        infoPanel.add(seedPanel);
        JLabel seedLabel = new JLabel("<html><strong>Seed = </strong></html>");
        JTextField seedTextField = new JTextField();
        seedTextField.setEditable(false);
        seedTextField.setBorder(null);
        seedPanel.add(seedLabel);
        seedPanel.add(seedTextField);
        seedTextField.setText(""+seed);
        seedTextField.addKeyListener(keyAdapter);

        JPanel floorPanel = new JPanel(new GridLayout(2,1));
        floorPanel.setBorder(BorderFactory.createTitledBorder("Floor"));
        floorPanel.setMaximumSize(new Dimension(300,50));
        infoPanel.add(floorPanel);
        floorLabel = new JLabel();
        floorPanel.add(floorLabel);
        JLabel floorSpecialLabel = new JLabel("Special = Nope");
        floorPanel.add(floorSpecialLabel);
        
        JPanel playerStats = new JPanel(new GridLayout(3,1));
        playerStats.setMaximumSize(new Dimension(300,100));
        infoPanel.add(playerStats);
        playerStats.setBorder(BorderFactory.createTitledBorder("Player Stats"));
        health = new JLabel();
        playerStats.add(health);
        damage = new JLabel();
        playerStats.add(damage);
        defence = new JLabel();
        playerStats.add(defence);

        JPanel collectiblesPanel = new JPanel(new GridLayout(1,2));
        collectiblesPanel.setBorder(BorderFactory.createTitledBorder("Key and pieces of them"));
        infoPanel.add(collectiblesPanel);
        collectiblesPanel.setMaximumSize(new Dimension(300,80));
        keyLabel = new JLabel(keyIcon);
        collectiblesPanel.add(keyLabel);

        JPanel expPanel = new JPanel(new GridLayout(4,1));
        expPanel.setMaximumSize(new Dimension(200,120));
        infoPanel.add(expPanel);
        progressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        expPanel.setBorder(BorderFactory.createTitledBorder("Experience Bar"));
        expPanel.add(progressBar);
        progressBar.setBorderPainted(true);
        progressBar.setStringPainted(true);
        progressBar.setMaximum(1000);
        progressBar.addChangeListener(e -> progressBar.setString(""+(float)Math.round(progressBar.getPercentComplete()*1000)/10+"%"));
        possibleLvlLabel = new JLabel();
        lvlsObtainedLabel = new JLabel();
        expPanel.add(possibleLvlLabel);
        expPanel.add(lvlsObtainedLabel);
        lvlUpBtn = new JButton("Level UP");
        lvlUpBtn.addActionListener(this);
        lvlUpBtn.setActionCommand("LVL UP");
        lvlUpBtn.addKeyListener(keyAdapter);
        lvlUpBtn.setEnabled(false);
        expPanel.add(lvlUpBtn);

        JPanel cheatPanel = new JPanel();
        cheatPanel.setBorder(BorderFactory.createTitledBorder("ONLY FOR PRO"));
        cheatPanel.setMaximumSize(new Dimension(200,150));
        infoPanel.add(cheatPanel);
        cheatButton = new JButton("Debug");
        cheatButton.setActionCommand(cheatButton.getText());
        cheatButton.addActionListener(this);
        cheatButton.addKeyListener(keyAdapter);
        cheatButton.setEnabled(false);
        cheatPanel.add(cheatButton);
        secretButton = new JButton("Secret button");
        secretButton.addKeyListener(keyAdapter);
        secretButton.setActionCommand("God mod");
        secretButton.addActionListener(this);
        cheatPanel.add(secretButton);
        cheatFrame = new CheatFrame(this,map);

        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.add(authorPanel);
        authorPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        authorPanel.setMaximumSize(new Dimension(200,200));
        authorPanel.add(new JLabel("All code written"));
        authorPanel.add(new JLabel("by Veniamin Arefev."));
        authorPanel.add(new JLabel("Specifically for the"));
        authorPanel.add(new JLabel("project Java Dungeon"));

        Timer repaintTimer = new Timer(100, this);
        repaintTimer.setRepeats(true);
        repaintTimer.setActionCommand("Repaint");
        repaintTimer.start();
        checkTimer = new Timer(100, this);
        checkTimer.setRepeats(true);
        checkTimer.setActionCommand("Check");
        checkTimer.start();

        Logger.getLogger(MainClassOfThisGame.class.getName()).info("Game started");
    }
    private class CheatFrame extends JDialog implements ActionListener {
        private ArrayList<TextField> player = new ArrayList<>();
        private Map map;
        private CheatFrame(JFrame frame, Map map) {
            super(frame, "Debug frame");
            this.map = map;
            setAlwaysOnTop(true);
            setSize(400, 300);
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            JPanel mainPanel = new JPanel(new GridLayout(2,2));
            add(mainPanel);
            JPanel playerPanel = new JPanel(new GridLayout(4,3));
            playerPanel.setBorder(BorderFactory.createTitledBorder("PlayerStats"));
            mainPanel.add(playerPanel);
            for (int i = 0; i < 4 ;i ++ ) {
                String string;
                if (i == 0) string = "Health";
                else if (i == 1) string = "MAXHealth";
                else if (i == 2) string = "Defence";
                else string = "Damage";
                playerPanel.add(new JLabel(string));
                TextField textField = new TextField("player" + string);
                textField.setText("");
                textField.setColumns(9);
                textField.invalidate();
                player.add(textField);
                playerPanel.add(textField);
                JButton button = new JButton("Set" + string);
                playerPanel.add(button);
                button.setActionCommand(button.getText());
                button.addActionListener(this);
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                switch (e.getActionCommand()) {
                    case "SetHealth": {
                        map.setPlHP(valueOf(player.get(0).getText()));
                        break;
                    }
                    case "SetMAXHealth": {
                        map.setPlMAXHP(valueOf(player.get(1).getText()));
                        break;
                    }
                    case "SetDefence": {
                        map.setPlDef(valueOf(player.get(2).getText()));
                        break;
                    }
                    case "SetDamage": {
                        map.setPlDmg(valueOf(player.get(3).getText()));
                        break;
                    }
                }
            } catch (NumberFormatException ignored){}
        }


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "God mod" : {
                GodModDialog dialog = new GodModDialog(this);
                dialog.setVisible(true);
                dialog.addWindowFocusListener(new WindowAdapter() {
                    @Override
                    public void windowLostFocus(WindowEvent e) {
                        super.windowLostFocus(e);
                        if (!dialog.isVisible()) {
                            if (dialog.getPass()) {
                                cheatButton.setEnabled(true);
                                secretButton.setEnabled(false);
                            }
                        }
                    }
                });
            }
            break;
            case "LVL UP" : {
                if (lvlUPDialog == null) lvlUPDialog = new LvlUPDialog(this,map);
                lvlUPDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        super.windowClosed(e);
                        lvlUPDialog = null;
                    }
                });
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, e1, String::new);
                }
                lvlUPDialog.setVisible(true);
            }
            break;
            case "Debug" : {
                if (cheatFrame.isVisible()) cheatFrame.setVisible(false);
                else cheatFrame.setVisible(true);
            }
            break;
            case "Repaint": {
                gamePanel.resetImage();
                for (int i = 0; i < 40; i++) {
                    for (int j = 0; j < 30; j++) {
                        gamePanel.drawSome(map.getId(i, j), i, j);
                    }
                }
                gamePanel.repaint();
                health.setText("Health = "+map.getPlHP()+" (Max = "+map.getPlMAXHP()+")");
                damage.setText("Damage = "+map.getPlDmg());
                defence.setText("Defence = "+map.getPlDef());
                floorLabel.setText("Floor = "+map.getFloor());
                keyLabel.updateUI();
            }
            break;
            case "Check": {
                progressBar.setValue(map.getPlayerEXP());
                if (map.getLvlUP() == 0) lvlUpBtn.setEnabled(false);
                else lvlUpBtn.setEnabled(true);
                possibleLvlLabel.setText("Level can obtain "+map.getLvlUP());
                if (lvlsObtained!=1) lvlsObtainedLabel.setText("Already obtained "+lvlsObtained+" levels");
                else lvlsObtainedLabel.setText("Already obtained 1 level");
                EndGameDialog endGameDialog;
                if (map.isGameover()) {
                    endGameDialog = new EndGameDialog(this, "Gameover", EndGameDialog.type.gameover);
                    endGameDialog.setVisible(true);
                    endGameDialog.update(endGameDialog.getGraphics());
                }
                if (map.isGamewin()) {
                    endGameDialog = new EndGameDialog(this, "You win this game", EndGameDialog.type.gamewin);
                    endGameDialog.setVisible(true);
                    endGameDialog.update(endGameDialog.getGraphics());
                }
                try {
                    if (map.isGamewin() || map.isGameover()) {
                        update(getGraphics());
                        checkTimer.stop();
                        Thread.sleep(4000);
                        dispose();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, ex,String::new);
                }
            }
            break;
        }
    }

    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            switch (e.getKeyCode()) {
                case KeyEvent.VK_W:
                    if (map.checkPositionToMove(Map.direction.up)) map.playerMove(Map.direction.up);
                    break;
                case KeyEvent.VK_S:
                    if (map.checkPositionToMove(Map.direction.down)) map.playerMove(Map.direction.down);
                    break;
                case KeyEvent.VK_A:
                    if (map.checkPositionToMove(Map.direction.left)) map.playerMove(Map.direction.left);
                    break;
                case KeyEvent.VK_D:
                    if (map.checkPositionToMove(Map.direction.right)) map.playerMove(Map.direction.right);
                    break;
                case KeyEvent.VK_SPACE:
                    gamePanel.grabFocus();
                    map.enemyTurn();
                    break;
            }
        }
    };
}