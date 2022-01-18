package cn.enaium.lvm.file;

import java.io.File;

/**
 * @author Enaium
 */
public class FileTableNode {
    private final String title;
    private final String tagline;
    private final String author;
    private final File file;

    public FileTableNode(String name, String tagline, String author, File file) {
        this.title = name;
        this.tagline = tagline;
        this.author = author;
        this.file = file;
    }

    public String getTitle() {
        return title;
    }

    public String getTagline() {
        return tagline;
    }

    public String getAuthor() {
        return author;
    }

    public File getFile() {
        return file;
    }

    @Override
    public String toString() {
        return file.getName();
    }
}
