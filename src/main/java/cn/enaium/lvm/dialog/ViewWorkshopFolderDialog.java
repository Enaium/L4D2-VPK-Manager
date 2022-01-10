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
        FileTable fileTable = new FileTable(FileTable.getTableModel());
        DefaultTableModel model = (DefaultTableModel) fileTable.getModel();
        File file = new File(WORKSHOP_DIR);
        Util.getAll(file).forEach(it -> model.addRow(new Object[]{it.getFile().getName(), it.getTitle(), it.getTagline(), it.getAuthor()}));
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
        });
        add(workshopVpkToAddonDir, BorderLayout.NORTH);
        add(new JScrollPane(fileTable), BorderLayout.CENTER);
    }
}
