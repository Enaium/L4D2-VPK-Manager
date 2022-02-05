package cn.enaium.lvm.dialog;

import javax.swing.*;

import static cn.enaium.lvm.MainGUI.ICON;

/**
 * @author Enaium
 */
public class Dialog extends JFrame {
    public Dialog(String title) {
        super(title);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setIconImage(ICON);
    }
}
