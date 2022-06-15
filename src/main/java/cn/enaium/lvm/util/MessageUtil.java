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
    }

    public static void confirm(String message, String title, Runnable yes, Runnable no) {
        int i = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.YES_OPTION) {
            yes.run();
        } else {
            no.run();
        }
    }

    public static void info(String message) {
        JOptionPane.showMessageDialog(null, message, LangUtil.i18n("info"), JOptionPane.INFORMATION_MESSAGE);
    }

    public static void warning(String message) {
        JOptionPane.showMessageDialog(null, message, LangUtil.i18n("warning"), JOptionPane.WARNING_MESSAGE);
    }
}
