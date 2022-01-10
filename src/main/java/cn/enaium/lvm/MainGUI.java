package cn.enaium.lvm;

import cn.enaium.lvm.panel.MainMenu;
import cn.enaium.lvm.panel.MainPanel;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.MessageUtil;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Enaium
 */
public class MainGUI extends JFrame {

    public static final Image ICON = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainGUI.class.getResource("/icon.jpg")).getScaledInstance(256, 256, 2)).getImage();

    public MainGUI() {
        super("L4D2 VPK Manager");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());
        setIconImage(ICON);
        add(new MainPanel(), BorderLayout.CENTER);
        setJMenuBar(new MainMenu());
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                int i = JOptionPane.showConfirmDialog(null, LangUtil.i18n("wantCloseWindow"), LangUtil.i18n("warning"), JOptionPane.OK_CANCEL_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    dispose();
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}
