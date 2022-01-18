package cn.enaium.lvm.file;

import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.Util;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.io.File;

/**
 * @author Enaium
 */
public class FileTable extends JTable {

    private final String dir;

    public FileTable(String dir) {
        super(new DefaultTableModel(new Object[][]{}, new String[]{LangUtil.i18n("table.fileName"), LangUtil.i18n("table.title"), LangUtil.i18n("table.tagline"), LangUtil.i18n("table.author")}));
        this.dir = dir;
        getTableHeader().setReorderingAllowed(false);
        refresh();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public void refresh() {
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        model.setRowCount(0);
        File file = new File(dir);
        Util.getAll(file).forEach(it -> model.addRow(new Object[]{it, it.getTitle(), it.getTagline(), it.getAuthor()}));
    }
}
