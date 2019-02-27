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

public class GodModDialog extends JDialog implements ActionListener{
    private boolean pass = false;
    private JPasswordField passField;
    private JLabel underPass;
    public GodModDialog(SettingsWindow window) {
        super(window, "Password", true);
        init();
    }
    public GodModDialog(Game window) {
        super(window, "Password", true);
        init();
    }
    private void init(){
        Logger.getLogger(MainClassOfThisGame.class.getName()).info("Godmod dialog opened");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setSize(400,300);
        setLocationRelativeTo(getOwner());
        JPanel panel = new JPanel(new GridLayout(4,1,0,10));
        JButton btnOK = new JButton("OK");
        passField = new JPasswordField();
        underPass = new JLabel("<html><td>&nbsp;</td><br><td>&nbsp;</td></html>");
        add(panel);
        panel.add(new Label("Enter your password there"));
        panel.add(passField);
        panel.add(underPass);
        panel.add(btnOK);
        btnOK.setActionCommand(btnOK.getText());
        passField.setFocusable(true);
        passField.requestFocus();
        passField.addKeyListener(new KeyAdapter() {
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

    public boolean getPass(){
        return pass;
    }

    private void enterPressed() {
        String pass = new String(passField.getPassword());
        pass = pass.toLowerCase();
        if (pass.equals("godpass")) {
            this.pass = true;
            underPass.setText("<html>Correct PASSWORD<br>You are welcome</html>");
            update(getGraphics());
                try {
                    Thread.sleep(2500);
                } catch (InterruptedException e) {
                    Logger.getLogger(MainClassOfThisGame.class.getName()).log(Level.SEVERE, e,String::new);
                }
            dispose();
        }
        else {
            underPass.setText("<html>WRONG PASSWORD<br>You are just human<br>Just leave this place</html>");
        }
    }
}