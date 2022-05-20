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

package cn.enaium.lvm.panel;

import cn.enaium.lvm.FileDropTarget;
import cn.enaium.lvm.dialog.ViewDisableFolderDialog;
import cn.enaium.lvm.dialog.ViewWorkshopFolderDialog;
import cn.enaium.lvm.file.FileList;
import cn.enaium.lvm.file.FileInfo;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static cn.enaium.lvm.LVM.ADDONS_DIR;
import static cn.enaium.lvm.LVM.DISABLE_ADDONS_DIR;

/**
 * @author Enaium
 */
public class MainPanel extends JPanel {
    public MainPanel() {
        setLayout(new BorderLayout());

        //Move to addons dir
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new FileDropTarget(".vpk", files -> {
            for (File file : files) {
                try {
                    Files.move(file.toPath(), Paths.get(ADDONS_DIR, file.getName()));
                    MessageUtil.info(LangUtil.i18n("success"));
                } catch (IOException e) {
                    MessageUtil.error(e);
                }
            }
        }));

        FileList fileList = new FileList(ADDONS_DIR);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, 3));
        JButton refresh = new JButton(LangUtil.i18n("button.refresh"));
        refresh.addActionListener(e -> fileList.refresh());
        topPanel.add(refresh);
        JButton delete = new JButton(LangUtil.i18n("button.viewDisabledFolder"));
        delete.addActionListener(e -> new ViewDisableFolderDialog().setVisible(true));
        topPanel.add(delete);
        JButton viewWorkshopFolder = new JButton(LangUtil.i18n("button.viewWorkshopFolder"));
        viewWorkshopFolder.addActionListener(e -> new ViewWorkshopFolderDialog().setVisible(true));
        topPanel.add(viewWorkshopFolder);
        add(topPanel, BorderLayout.NORTH);
        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem(LangUtil.i18n("menu.disable"));
        jMenuItem.addActionListener(e -> {
            for (int selectedRow : fileList.getTable().getSelectedRows()) {
                Object valueAt = fileList.getTable().getValueAt(selectedRow, 0);
                if (valueAt instanceof FileInfo) {
                    File file = ((FileInfo) valueAt).getFile();
                    try {
                        Files.move(file.toPath(), new File(DISABLE_ADDONS_DIR, file.getName()).toPath());
                    } catch (IOException ex) {
                        MessageUtil.error(ex);
                    }
                }
            }
            fileList.refresh();
        });
        jPopupMenu.add(jMenuItem);
        fileList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isRightMouseButton(e) || fileList.getTable().getSelectedRow() == -1) return;
                jPopupMenu.show(fileList, e.getX(), e.getY());
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
        add(fileList, BorderLayout.CENTER);
    }


}
