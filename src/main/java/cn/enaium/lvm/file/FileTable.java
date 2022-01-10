package cn.enaium.lvm.file;

import cn.enaium.lvm.util.LangUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author Enaium
 */
public class FileTable extends JTable {
    public FileTable(TableModel dm) {
        super(dm);
        getTableHeader().setReorderingAllowed(false);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public static DefaultTableModel getTableModel() {
        return new DefaultTableModel(new Object[][]{}, new String[]{LangUtil.i18n("table.fileName"), LangUtil.i18n("table.title"), LangUtil.i18n("table.tagline"), LangUtil.i18n("table.author")});
    }
}
