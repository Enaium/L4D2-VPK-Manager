package cn.enaium.lvm.panel;

import cn.enaium.lvm.dialog.SettingDialog;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author Enaium
 */
public class MainMenu extends JMenuBar {
    public MainMenu() {
        JMenu jMenu = new JMenu("Help");
        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/Enaium"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        jMenu.add(about);
        JMenuItem setting = new JMenuItem("Setting");
        setting.addActionListener(e -> {
            new SettingDialog().setVisible(true);
        });
        jMenu.add(setting);
        add(jMenu);
    }
}
