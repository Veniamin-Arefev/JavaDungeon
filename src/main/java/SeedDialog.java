/*
Created by Veniamin_arefev
Special for School Project
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeedDialog extends JDialog implements ActionListener{
    private JLabel underText;
    private int seed = 0;
    private JTextField textField = new JTextField(10);
    public SeedDialog(SettingsWindow window){
        super(window,"Password",true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(400,300);
        setLocationRelativeTo(getOwner());
        JPanel panel = new JPanel(new GridLayout(4,1,0,10));
        JButton btnOK = new JButton("Enter seed");
        underText = new JLabel("<html><td>&nbsp;</td><br></html>");
        add(panel);
        panel.add(new Label("Enter your seed there"));
        panel.add(textField);
        panel.add(underText);
        panel.add(btnOK);

        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() ==KeyEvent.VK_ENTER) enterPressed();
                if (e.getKeyChar() ==KeyEvent.VK_ESCAPE) dispose();
            }
        });
        btnOK.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() ==KeyEvent.VK_ENTER) enterPressed();
                if (e.getKeyChar() ==KeyEvent.VK_ESCAPE) dispose();
            }
        });
        btnOK.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        enterPressed();
    }

    public int getSeed(){
        return seed;
    }

    private void enterPressed() {
        boolean correctly = true;
        try {
            Integer.parseInt(textField.getText());
        } catch (NumberFormatException e) {
            correctly = false;
            underText.setText("<html>Wrong Seed<br>It may contain only numbers<br>Try to enter it correctly</html>");
        } finally {
            if (correctly) {
                underText.setText("<html>Correct seed<br>Setting it...</html>");
                update(getGraphics());
                seed = Integer.parseInt(textField.getText());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, e,String::new);
                }
                dispose();
            }
        }
    }
}
