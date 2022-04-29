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

import cn.enaium.lvm.FileDropTarget;
import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.MessageUtil;
import com.connorhaigh.javavpk.core.Archive;
import com.connorhaigh.javavpk.core.Directory;
import com.connorhaigh.javavpk.core.Entry;
import com.connorhaigh.javavpk.exceptions.ArchiveException;
import com.connorhaigh.javavpk.exceptions.EntryException;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 */
public class VPKExtract extends Dialog {
    public VPKExtract() {
        super(LangUtil.i18n("menu.tool.vpkExtract"));
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new FileDropTarget(".vpk", files -> {
            for (File file : files) {
                try {
                    Archive archive = new Archive(file);
                    archive.load();
                    for (Directory directory : archive.getDirectories()) {
                        for (Entry entry : directory.getEntries()) {
                            File out = new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf(".")), directory.getPath() + "/" + entry.getFullName());
                            if (!out.getParentFile().exists()) {
                                Files.createDirectories(out.getParentFile().toPath());
                            }
                            Files.write(out.toPath(), entry.readData());
                        }
                    }
                } catch (Exception e) {
                    MessageUtil.error(e);
                }
            }
            JOptionPane.showMessageDialog(null, LangUtil.i18n("success"));
        }), true);
        add(new JLabel(LangUtil.i18n("menu.tool.vpkExtract.drop"), JLabel.CENTER), BorderLayout.CENTER);
    }
}
