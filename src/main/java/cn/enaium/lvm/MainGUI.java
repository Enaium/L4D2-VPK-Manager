/**
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.lvm;

import cn.enaium.lvm.menu.MainMenu;
import cn.enaium.lvm.panel.MainPanel;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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
                MessageUtil.confirm(LangUtil.i18n("wantCloseWindow"), LangUtil.i18n("warning"), () -> {
                    dispose();
                    System.exit(0);
                    }, () -> {});
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
