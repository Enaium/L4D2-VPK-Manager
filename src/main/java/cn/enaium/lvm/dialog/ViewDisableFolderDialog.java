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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        FileTable fileTable = new FileTable(DISABLE_ADDONS_DIR);
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem(LangUtil.i18n("menu.enable"));
        jMenuItem.addActionListener(e -> {
            for (int selectedRow : fileTable.getSelectedRows()) {
                Object valueAt = fileTable.getValueAt(selectedRow, 0);
                if (valueAt instanceof FileTableNode) {
                    File disableFile = ((FileTableNode) valueAt).getFile();
                    try {
                        Files.move(disableFile.toPath(), new File(ADDONS_DIR, disableFile.getName()).toPath());
                    } catch (IOException ex) {
                        MessageUtil.error(ex);
                    }
                }
            }
            fileTable.refresh();
        });
        jPopupMenu.add(jMenuItem);
        fileTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isRightMouseButton(e) || fileTable.getSelectedRow() == -1) return;
                jPopupMenu.show(fileTable, e.getX(), e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(new JScrollPane(fileTable), BorderLayout.CENTER);
    }
}
