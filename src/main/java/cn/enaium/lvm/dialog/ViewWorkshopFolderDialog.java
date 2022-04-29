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

package cn.enaium.lvm.dialog;

import cn.enaium.lvm.file.FileTable;
import cn.enaium.lvm.file.FileTableNode;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.MessageUtil;
import cn.enaium.lvm.util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static cn.enaium.lvm.LVM.ADDONS_DIR;
import static cn.enaium.lvm.LVM.WORKSHOP_DIR;

/**
 * @author Enaium
 */
public class ViewWorkshopFolderDialog extends Dialog {
    public ViewWorkshopFolderDialog() {
        super(LangUtil.i18n("button.viewWorkshopFolder"));
        FileTable fileTable = new FileTable(WORKSHOP_DIR);
        JButton workshopVpkToAddonDir = new JButton(LangUtil.i18n("button.workshopCopyToAddons"));
        workshopVpkToAddonDir.addActionListener(action -> {
            for (FileTableNode fileTableNode : Util.getAll(new File(WORKSHOP_DIR))) {
                if (!fileTableNode.getTitle().equals("NULL")) {
                    try {
                        Files.copy(fileTableNode.getFile().toPath(), new File(ADDONS_DIR, fileTableNode.getTitle().replaceAll("[\\s\\\\/:*?\"<>|]", "-") + ".vpk").toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (Throwable e) {
                        MessageUtil.error(e);
                    }
                }
            }
            JOptionPane.showMessageDialog(null, LangUtil.i18n("success"));
        });
        add(workshopVpkToAddonDir, BorderLayout.NORTH);
        add(new JScrollPane(fileTable), BorderLayout.CENTER);
    }
}
