package cn.enaium.lvm;

import cn.enaium.lvm.util.LangUtil;
import cn.enaium.lvm.util.MessageUtil;
import com.connorhaigh.javavpk.core.Archive;
import com.connorhaigh.javavpk.core.Directory;
import com.connorhaigh.javavpk.core.Entry;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Enaium
 */
public class FileDropTarget implements DropTargetListener {

    private boolean drop = false;
    private final List<File> files = new ArrayList<>();

    private final String suffix;
    private final Consumer<List<File>> consumer;

    public FileDropTarget(String suffix, Consumer<List<File>> consumer) {
        this.suffix = suffix;
        this.consumer = consumer;
    }

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
                            if (!name.endsWith(suffix)) {
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
    public void dragExit(DropTargetEvent dte) {
        drop = false;
        files.clear();
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        consumer.accept(files);
        drop = false;
        files.clear();
    }
}
