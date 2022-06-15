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

import cn.enaium.lvm.file.FileInfo;
import cn.enaium.lvm.file.FileList;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.MessageUtil;
import cn.enaium.lvm.util.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static cn.enaium.lvm.LVM.ADDONS_DIR;
import static cn.enaium.lvm.LVM.WORKSHOP_DIR;

/**
 * @author Enaium
 */
public class ViewWorkshopFolderDialog extends Dialog {
    public ViewWorkshopFolderDialog() {
        super(LangUtil.i18n("button.viewWorkshopFolder"));
        FileList fileList = new FileList(WORKSHOP_DIR);

        JPopupMenu jPopupMenu = new JPopupMenu() {
            {
                add(new JMenuItem(LangUtil.i18n("menu.copyToAddons")) {
                    {
                        addActionListener(e -> {
                            for (int selectedRow : fileList.getTable().getSelectedRows()) {
                                Object valueAt = fileList.getTable().getValueAt(selectedRow, 0);
                                if (valueAt instanceof FileInfo) {
                                    FileInfo fileInfo = (FileInfo) valueAt;
                                    try {
                                        Files.copy(fileInfo.getFile().toPath(), new File(ADDONS_DIR, fileInfo.getTitle().replaceAll("[\\s\\\\/:*?\"<>|]", "-") + ".vpk").toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    } catch (IOException ex) {
                                        MessageUtil.error(ex);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        };

        fileList.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isRightMouseButton(e) || fileList.getTable().getSelectedRow() == -1) return;
                jPopupMenu.show(fileList, e.getX(), e.getY());
            }
        });

        JButton workshopVpkToAddonDir = new JButton(LangUtil.i18n("button.workshopCopyToAddons"));
        workshopVpkToAddonDir.addActionListener(action -> {
            List<String> f = new ArrayList<>();
            for (FileInfo fileInfo : Util.getAll(new File(WORKSHOP_DIR))) {
                if (!fileInfo.getTitle().equals("NULL")) {
                    try {
                        Files.copy(fileInfo.getFile().toPath(), new File(ADDONS_DIR, fileInfo.getTitle().replaceAll("[\\s\\\\/:*?\"<>|]", "-") + ".vpk").toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (Throwable e) {
                        MessageUtil.error(e);
                    }
                } else {
                    f.add(fileInfo.getFile().getName());
                }
            }

            if (f.isEmpty()) {
                JOptionPane.showMessageDialog(null, LangUtil.i18n("success"));
            } else {
                StringBuilder message = new StringBuilder(LangUtil.i18n("warning.workshop.copy", f.size()));
                message.append("\n");
                f.forEach(it -> message.append(it).append("\n"));
                MessageUtil.warning(message.toString());
            }
        });
        add(workshopVpkToAddonDir, BorderLayout.NORTH);
        add(fileList, BorderLayout.CENTER);
    }
}
