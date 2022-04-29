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

package cn.enaium.lvm.menu;

import cn.enaium.lvm.dialog.SettingDialog;
import cn.enaium.lvm.dialog.VPKExtract;
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
        JMenu tool = new JMenu(LangUtil.i18n("menu.tool"));
        JMenuItem vpkExtract = new JMenuItem(LangUtil.i18n("menu.tool.vpkExtract"));
        vpkExtract.addActionListener(e -> {
            new VPKExtract().setVisible(true);
        });
        tool.add(vpkExtract);
        add(tool);

        JMenu help = new JMenu(LangUtil.i18n("menu.help"));
        JMenuItem about = new JMenuItem(LangUtil.i18n("menu.help.about"));
        about.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/Enaium/L4D2-VPK-Manager"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });
        help.add(about);
        JMenuItem setting = new JMenuItem(LangUtil.i18n("menu.help.setting"));
        setting.addActionListener(e -> {
            new SettingDialog().setVisible(true);
        });
        help.add(setting);
        add(help);
    }
}
