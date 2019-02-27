import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LvlUPDialog extends JDialog implements ActionListener{
    private Map map;
    public LvlUPDialog(Game game, Map map) {
        super(game,"Choose your ability",true);
        this.map = null;
        setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel panel = new JPanel(new GridLayout(1,3));
        add(panel);
        JButton dmgUP = new JButton("Damage");
        dmgUP.setActionCommand("Damage");
        dmgUP.addActionListener(this);
        dmgUP.setMaximumSize(new Dimension(300,300));
        dmgUP.setMinimumSize(new Dimension(300,300));
        panel.add(dmgUP);
        JButton defUP = new JButton("Defence");
        defUP.setActionCommand("Defence");
        defUP.addActionListener(this);
        defUP.setMaximumSize(new Dimension(300,300));
        defUP.setMinimumSize(new Dimension(300,300));
        panel.add(defUP);
        JButton maxHPUP = new JButton("Add 50 HP to Max");
        maxHPUP.setActionCommand("Add max HP");
        maxHPUP.addActionListener(this);
        maxHPUP.setMaximumSize(new Dimension(300,300));
        maxHPUP.setMinimumSize(new Dimension(300,300));
        maxHPUP.setPreferredSize(new Dimension(300,300));
        panel.add(maxHPUP);
        pack();
        this.map = map;

        setLocationRelativeTo(game);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Add max HP" : {
                map.setPlMAXHP(map.getPlMAXHP()+50);
                map.removeLvlUp();
                ((Game)getOwner()).lvlsObtained++;
                dispose();
            }
            break;
            case "Damage" : {
                map.setPlDmg(map.getPlDmg()+1);
                map.removeLvlUp();
                ((Game)getOwner()).lvlsObtained++;
                dispose();
            }
            break;
            case "Defence" : {
                map.setPlDef(map.getPlDef()+1);
                map.removeLvlUp();
                ((Game)getOwner()).lvlsObtained++;
                dispose();
            }
            break;
        }
    }
}
