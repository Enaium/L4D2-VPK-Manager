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
