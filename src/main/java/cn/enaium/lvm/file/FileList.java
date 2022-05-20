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

import cn.enaium.lvm.panel.FileInfoPanel;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * @author Enaium
 */
public class FileList extends JPanel {

    private final String dir;
    private final JTable table;

    public FileList(String dir) {
        super(new BorderLayout());
        table = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{LangUtil.i18n("table.fileName"), LangUtil.i18n("table.title"), LangUtil.i18n("table.tagline"), LangUtil.i18n("table.author")})) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.dir = dir;
        table.getTableHeader().setReorderingAllowed(false);
        refresh();

        JSplitPane comp = new JSplitPane();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e) && table.getSelectedRow() != -1) {
                    for (int selectedRow : table.getSelectedRows()) {
                        Object valueAt = table.getValueAt(selectedRow, 0);
                        if (valueAt instanceof FileInfo) {
                            comp.setRightComponent(new JScrollPane(new FileInfoPanel((FileInfo) valueAt)));
                        }
                    }
                }
            }
        });


        comp.setLeftComponent(new JScrollPane(table));
        comp.setResizeWeight(0.8f);
        comp.setDividerLocation(Integer.MAX_VALUE);
        add(comp, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public void refresh() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        File file = new File(dir);
        Util.getAll(file).forEach(it -> {
            model.addRow(new Object[]{it, it.getTitle(), it.getTagline(), it.getAuthor()});
        });
    }
}
