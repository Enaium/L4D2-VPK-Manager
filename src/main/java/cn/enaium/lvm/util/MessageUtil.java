package cn.enaium.lvm.util;

import javax.swing.*;

/**
 * @author Enaium
 */
public class MessageUtil {

    private MessageUtil() {
        throw new IllegalAccessError("Utility");
    }

    public static void error(Throwable e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
    }
}
