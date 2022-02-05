package cn.enaium.lvm.dialog;

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
public class VPKExtract extends Dialog implements DropTargetListener {

    private boolean drop = false;
    private final List<File> files = new ArrayList<>();

    public VPKExtract() {
        super(LangUtil.i18n("menu.tool.vpkExtract"));
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this, true);
        add(new JLabel(LangUtil.i18n("menu.tool.vpkExtract.drop"), JLabel.CENTER), BorderLayout.CENTER);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        drop = false;
        files.clear();
        Transferable t = dtde.getTransferable();
        if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            try {
                Object td = t.getTransferData(DataFlavor.javaFileListFlavor);
                if (td instanceof List) {
                    drop = true;
                    for (Object value : ((List) td)) {
                        if (value instanceof File) {
                            File file = (File) value;
                            String name = file.getName().toLowerCase();
                            if (!name.endsWith(".vpk")) {
                                drop = false;
                                break;
                            } else {
                                files.add(file);
                            }
                        }
                    }
                }
            } catch (UnsupportedFlavorException | IOException ex) {
                ex.printStackTrace();
            }
        }
        if (drop) {
            dtde.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            dtde.rejectDrag();
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {

    }

    @Override
    public void dragExit(DropTargetEvent dtde) {
        drop = false;
        files.clear();
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
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
        drop = false;
        files.clear();
    }
}
