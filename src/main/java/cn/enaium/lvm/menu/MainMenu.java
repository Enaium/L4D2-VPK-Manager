package cn.enaium.lvm.menu;

import cn.enaium.lvm.dialog.SettingDialog;
import cn.enaium.lvm.util.LangUtil;

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
        JMenu jMenu = new JMenu(LangUtil.i18n("menu.help"));
        JMenuItem about = new JMenuItem(LangUtil.i18n("menu.help.about"));
        about.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/Enaium/L4D2-VPK-Manager"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        jMenu.add(about);
        JMenuItem setting = new JMenuItem(LangUtil.i18n("menu.help.setting"));
        setting.addActionListener(e -> {
            new SettingDialog().setVisible(true);
        });
        jMenu.add(setting);
        add(jMenu);
    }
}
