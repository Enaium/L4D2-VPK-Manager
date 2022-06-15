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

import cn.enaium.lvm.file.FileList;
import cn.enaium.lvm.file.FileInfo;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static cn.enaium.lvm.LVM.ADDONS_DIR;
import static cn.enaium.lvm.LVM.DISABLE_ADDONS_DIR;

/**
 * @author Enaium
 */
public class ViewDisableFolderDialog extends Dialog {
    public ViewDisableFolderDialog() {
        super(LangUtil.i18n("button.viewDisabledFolder"));
        FileList fileList = new FileList(DISABLE_ADDONS_DIR);
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem(LangUtil.i18n("menu.enable"));
        jMenuItem.addActionListener(e -> {
            for (int selectedRow : fileList.getTable().getSelectedRows()) {
                Object valueAt = fileList.getTable().getValueAt(selectedRow, 0);
                if (valueAt instanceof FileInfo) {
                    File disableFile = ((FileInfo) valueAt).getFile();
                    try {
                        Files.move(disableFile.toPath(), new File(ADDONS_DIR, disableFile.getName()).toPath());
                    } catch (IOException ex) {
                        MessageUtil.error(ex);
                    }
                }
            }
            fileList.refresh();
        });
        jPopupMenu.add(jMenuItem);
        fileList.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isRightMouseButton(e) || fileList.getTable().getSelectedRow() == -1) return;
                jPopupMenu.show(fileList, e.getX(), e.getY());
            }
        });
        add(fileList, BorderLayout.CENTER);
    }
}
