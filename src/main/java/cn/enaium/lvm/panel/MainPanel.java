package cn.enaium.lvm.panel;

import cn.enaium.lvm.dialog.ViewWorkshopFolderDialog;
import cn.enaium.lvm.file.FileTable;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

import static cn.enaium.lvm.LVM.ADDONS_DIR;

/**
 * @author Enaium
 */
public class MainPanel extends JPanel {
    public MainPanel() {
        setLayout(new BorderLayout());
        FileTable fileTable = new FileTable(FileTable.getTableModel());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(1, 2));
        JButton refresh = new JButton(LangUtil.i18n("button.refresh"));
        refresh.addActionListener(e -> refresh(fileTable));
        jPanel.add(refresh);
        JButton viewWorkshopFolder = new JButton(LangUtil.i18n("button.viewWorkshopFolder"));
        viewWorkshopFolder.addActionListener(e -> new ViewWorkshopFolderDialog().setVisible(true));
        jPanel.add(viewWorkshopFolder);
        add(jPanel, BorderLayout.NORTH);
        refresh(fileTable);

        add(new JScrollPane(fileTable), BorderLayout.CENTER);
    }

    private void refresh(FileTable fileTable) {
        DefaultTableModel model = (DefaultTableModel) fileTable.getModel();
        model.setRowCount(0);
        File file = new File(ADDONS_DIR);
        Util.getAll(file).forEach(it -> model.addRow(new Object[]{it.getFile().getName(), it.getTitle(), it.getTagline(), it.getAuthor()}));
    }
}
