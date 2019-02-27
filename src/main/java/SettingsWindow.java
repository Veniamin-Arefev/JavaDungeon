/*
Created by Veniamin_arefev
Special for School Project
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Logger;

public class SettingsWindow extends JDialog  {
    enum difficult {easy,normal,hard,godmod}
    private JCheckBox debug = new JCheckBox("Debug mod", false);
    private difficult diff;
    private int seed = 0;
    private GodModDialog godModDialog;
    private SeedDialog seedDialog;
    private Label godmod = new Label();
    private ButtonGroup btnGroup = new ButtonGroup();
    private JPanel diffpanel;
    private JCheckBox easy = new JCheckBox("Easy diff");
    private JCheckBox normal = new JCheckBox("Normal diff");
    private JCheckBox hard = new JCheckBox("Hard diff");
    private JLabel seedLabel = new JLabel("Random");
    private JButton godMod;
    private JButton seedChangeBtn;

    difficult getDiff() {
        if (!(diff == difficult.godmod)) {
            if (easy.getModel() == btnGroup.getSelection()) diff = difficult.easy;
            if (normal.getModel() == btnGroup.getSelection()) diff = difficult.normal;
            if (hard.getModel() == btnGroup.getSelection()) diff = difficult.hard;
        }
        return diff;
    }
    boolean getDebug() {
        return debug.isSelected();
    }
    int getSeed() {
        return seed;
    }

    public SettingsWindow(JFrame frame) throws HeadlessException {
        super(frame, "Settings", true);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        add(mainPanel);
        diffpanel = new JPanel(new GridLayout(1, 3));
        mainPanel.add(diffpanel);
        diffpanel.setPreferredSize(new Dimension(500,50));
        diffpanel.setBorder(BorderFactory.createTitledBorder("Difficulty"));

        diffpanel.add(godmod);
        godmod.setVisible(false);

        easy.addKeyListener(keyAdapter);
        normal.addKeyListener(keyAdapter);
        hard.addKeyListener(keyAdapter);

        diffpanel.add(easy);
        diffpanel.add(normal);
        diffpanel.add(hard);

        btnGroup.add(easy);
        btnGroup.add(normal);
        btnGroup.add(hard);
        btnGroup.setSelected(easy.getModel(), true);

        JPanel seedPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        mainPanel.add(seedPanel);
        seedChangeBtn = new JButton("Set seed");
        seedChangeBtn.addKeyListener(keyAdapter);
        seedChangeBtn.setActionCommand(seedChangeBtn.getText());
        seedChangeBtn.addActionListener(actionListener);
        seedPanel.setLayout(new GridLayout(1, 2));
        seedPanel.setBorder(BorderFactory.createTitledBorder("Seed"));
        seedPanel.add(seedLabel);
        seedPanel.add(seedChangeBtn);

        JPanel godmodPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mainPanel.add(godmodPanel);
        godMod = new JButton("SUPER secret settings");
        godMod.addKeyListener(keyAdapter);
        godMod.setActionCommand("Enable GogMod");
        godMod.addActionListener(actionListener);
        godmod.setPreferredSize(new Dimension(100,50));
        godmodPanel.add(godMod);

        mainPanel.add(debug,FlowLayout.RIGHT);
        debug.addKeyListener(keyAdapter);
    }
    private KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            super.keyTyped(e);
            if (e.getKeyChar() == KeyEvent.VK_ESCAPE) dispose();
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                if (easy.isFocusOwner()) easy.doClick();
                if (normal.isFocusOwner()) normal.doClick();
                if (hard.isFocusOwner()) hard.doClick();
                if (debug.isFocusOwner()) debug.doClick();
                if (seedChangeBtn.isFocusOwner()) seedChangeBtn.doClick();
                if (godMod.isFocusOwner()) godMod.doClick();
            }
        }
    };

    private ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Set seed": {
                if (seedDialog == null) seedDialog = new SeedDialog(SettingsWindow.this);
                seedDialog.setVisible(true);
                seedDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        super.windowDeactivated(e);
                        if (!(seedDialog.getSeed() == 0)) {
                            Logger.getLogger(MainClassOfThisGame.class.getName()).info("Setted  seed to : "+seed);
                            seed = seedDialog.getSeed();
                            seedLabel.setText("" + seed);
                        }
                        else {
                            Logger.getLogger(MainClassOfThisGame.class.getName()).info("Seed doesn't setted : ");
                        }
                    }
                });

            }
                break;
            case "Enable GogMod": {
                if (godModDialog == null) godModDialog = new GodModDialog(SettingsWindow.this);
                godModDialog.setVisible(true);
                godModDialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        super.windowDeactivated(e);
                        if (godModDialog.getPass() && !godModDialog.isVisible()) {
                            diff = difficult.godmod;
                            JPanel panel = ((JPanel) easy.getParent());
                            easy.getParent().remove(easy);
                            normal.getParent().remove(normal);
                            hard.getParent().remove(hard);
//                            panel.repaint();
                            panel.update(panel.getGraphics());
                            panel.setLayout(new FlowLayout(FlowLayout.CENTER));
                            panel.repaint();
                            godmod.setText("GOD MOD ACTIVATED");
                            godmod.setVisible(true);
                            diffpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                            godMod.removeActionListener(actionListener);
                            godMod.setEnabled(false);
                            Logger.getLogger(MainClassOfThisGame.class.getName()).info("GOD MOD ACTIVATED");
                        } else {
                            Logger.getLogger(MainClassOfThisGame.class.getName()).info("God mod not activated");
                        }

                    }
                });
            }
                break;
            }
        }
};
}
