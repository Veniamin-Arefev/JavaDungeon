/*
Created by Veniamin_arefev
Special for School Project
 */
import javax.swing.*;
import java.awt.*;
import static java.lang.Integer.valueOf;

public class StatsDialog extends JDialog{
    private TextField playerHealth = new TextField("200");
    private TextField playerDamage = new TextField("5");
    private TextField playerDefence = new TextField("2");

    private TextField firstHealth = new TextField("10");
    private TextField firstDamage = new TextField("3");
    private TextField firstDefence = new TextField("3");

    private TextField secondHealth = new TextField("15");
    private TextField secondDamage = new TextField("5");
    private TextField secondDefence = new TextField("4");

    private TextField thirdHealth = new TextField("30");
    private TextField thirdDamage = new TextField("7");
    private TextField thirdDefence = new TextField("5");

    public int getPlayerHealth() {
        return valueOf(playerHealth.getText());
    }
    public int getPlayerDamage() {
        return valueOf(playerDamage.getText());
    }
    public int getPlayerDefence() {
        return valueOf(playerDefence.getText());
    }
    public int getFirstHealth() {
        return valueOf(firstHealth.getText());
    }
    public int getFirstDamage() {
        return valueOf(firstDamage.getText());
    }
    public int getFirstDefence() {
        return valueOf(firstDefence.getText());
    }
    public int getSecondHealth() {
        return valueOf(secondHealth.getText());
    }
    public int getSecondDamage() {
        return valueOf(secondDamage.getText());
    }
    public int getSecondDefence() {
        return valueOf(secondDefence.getText());
    }
    public int getThirdHealth() {
        return valueOf(thirdHealth.getText());
    }
    public int getThirdDamage() {
        return valueOf(thirdDamage.getText());
    }
    public int getThirdDefence() {
        return valueOf(thirdDefence.getText());
    }
    public StatsDialog() {
        super();
        setTitle("Settings");
        setAlwaysOnTop(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        add(mainPanel);
        JPanel modelPanel = new JPanel(new GridLayout(2,2));
        mainPanel.add(modelPanel);

        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        playerPanel.setBorder(BorderFactory.createTitledBorder("Player stats"));
        modelPanel.add(playerPanel);
        playerPanel.add(playerHealth);
        playerPanel.add(playerDefence);
        playerPanel.add(playerDamage);

        JPanel firstPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        firstPanel.setBorder(BorderFactory.createTitledBorder("First model stats"));
        modelPanel.add(firstPanel);
        firstPanel.add(firstHealth);
        firstPanel.add(firstDefence);
        firstPanel.add(firstDamage);

        JPanel secondPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        secondPanel.setBorder(BorderFactory.createTitledBorder("Second model stats"));
        modelPanel.add(secondPanel);
        secondPanel.add(secondHealth);
        secondPanel.add(secondDefence);
        secondPanel.add(secondDamage);

        JPanel thirdPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        thirdPanel.setBorder(BorderFactory.createTitledBorder("Third model stats"));
        modelPanel.add(thirdPanel);
        thirdPanel.add(thirdHealth);
        thirdPanel.add(thirdDefence);
        thirdPanel.add(thirdDamage);
        pack();
    }
}
