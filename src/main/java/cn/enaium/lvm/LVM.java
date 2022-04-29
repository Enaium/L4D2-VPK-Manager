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

import cn.enaium.lvm.config.Config;
import cn.enaium.lvm.util.ConfigUtil;
import cn.enaium.lvm.util.MessageUtil;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.io.File;

/**
 * @author Enaium
 */
public class LVM {
    public static String WORKSHOP_DIR;
    public static String ADDONS_DIR;
    public static String DISABLE_ADDONS_DIR;
    public static Config CONFIG;

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        CONFIG = ConfigUtil.read(Config.class);
        CONFIG.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> ConfigUtil.save(CONFIG)));

        switch (CONFIG.theme.getValue()) {
            case "Light":
                UIManager.setLookAndFeel(new FlatLightLaf());
                break;
            case "Dark":
                UIManager.setLookAndFeel(new FlatDarkLaf());
                break;
        }

        String dir = null;
        File file = new File(CONFIG.l4d2Dir.getValue());
        if (!file.exists()) {
            String inputL4D2Dir = JOptionPane.showInputDialog("Input L4D2 DIR", "C:/Program Files (x86)/Steam/steamapps/common/Left 4 Dead 2");
            if (new File(inputL4D2Dir).exists()) {
                CONFIG.l4d2Dir.setValue(inputL4D2Dir);
                dir = inputL4D2Dir;
            }
        } else {
            dir = CONFIG.l4d2Dir.getValue();
        }

        if (dir != null) {
            ADDONS_DIR = dir + "/left4dead2/addons";
            DISABLE_ADDONS_DIR = dir + "/left4dead2/disable-addons";
            WORKSHOP_DIR = dir + "/left4dead2/addons/workshop";
            if (!new File(DISABLE_ADDONS_DIR).exists()) {
                new File(DISABLE_ADDONS_DIR).mkdirs();
            }
        } else {
            MessageUtil.error(new Exception("DIR NOT FOUND!"));
            System.exit(0);
        }
        new MainGUI().setVisible(true);
    }
}
