package cn.enaium.lvm.panel;

import cn.enaium.lvm.file.FileInfo;
import cn.enaium.lvm.util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author Enaium
 */
public class FileInfoPanel extends JPanel {
    public FileInfoPanel(FileInfo fileInfo) {
        setLayout(new GridLayout(0, 1));
        File file = fileInfo.getFile();
        InputStream inputStream = null;
        try {
            byte[] addonimage = Util.getFile(file, "addonimage.jpg");
            if (addonimage != null) {
                inputStream = new ByteArrayInputStream(addonimage);
            }

            if (inputStream == null) {
                String substring = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."));
                if (new File(substring + ".jpg").exists()) {
                    inputStream = new ByteArrayInputStream(Files.readAllBytes(Paths.get(substring + ".jpg")));
                }
            }

            ImageIcon image = new ImageIcon(ImageIO.read(inputStream == null ? Objects.requireNonNull(FileInfoPanel.class.getResourceAsStream("/icon.jpg")) : inputStream));
            inputStream.close();
            image.setImage(image.getImage().getScaledInstance(200, 200, Image.SCALE_AREA_AVERAGING));
            add(new JLabel(image));
        } catch (Exception ignored) {

        }
        byte[] addoninfo = Util.getFile(file, "addoninfo.txt");
        if (addoninfo != null) {
            add(new JScrollPane(new JTextArea(new String(addoninfo, StandardCharsets.UTF_8)) {
                {
                    setColumns(20);
                    setEditable(false);
                }
            }));
        }
    }
}
