package cn.enaium.lvm.panel;

import cn.enaium.lvm.file.FileInfo;
import com.connorhaigh.javavpk.core.Archive;
import com.connorhaigh.javavpk.core.Directory;
import com.connorhaigh.javavpk.core.Entry;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * @author Enaium
 */
public class FileInfoPanel extends JPanel {
    public FileInfoPanel(FileInfo fileInfo) {
        super(new GridLayout(0, 1));
        File file = fileInfo.getFile();
        InputStream inputStream = null;
        try {
            Archive archive = new Archive(file);
            archive.load();
            for (Directory directory : archive.getDirectories()) {
                for (Entry entry : directory.getEntries()) {
                    if (entry.getFullName().equals("addonimage.jpg")) {
                        inputStream = new ByteArrayInputStream(entry.readData());
                    }
                }
            }

            if (inputStream == null) {
                inputStream = FileInfoPanel.class.getResourceAsStream("/icon.jpg");
            }
            add(new JLabel(new ImageIcon(ImageIO.read(inputStream))));
        } catch (Exception ignored) {

        }



//        add(new JPanel() {
//            {
//                add(new JLabel("Title"));
//                add(new JLabel(fileInfo.getTitle()));
//            }
//        });
//
//        add(new JPanel() {
//            {
//                add(new JLabel("Author"));
//                add(new JLabel(fileInfo.getAuthor()));
//            }
//        });
    }
}
