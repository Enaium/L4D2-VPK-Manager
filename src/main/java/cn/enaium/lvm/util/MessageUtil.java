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

    public static void confirm(String message, String title, Action yes, Action no) {
        int i = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            yes.action();
        } else {
            no.action();
        }
    }

    public interface Action {
        void action();
    }
}
