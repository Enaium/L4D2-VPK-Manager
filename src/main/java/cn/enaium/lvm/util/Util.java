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

import cn.enaium.lvm.file.FileTableNode;
import com.connorhaigh.javavpk.core.Archive;
import com.connorhaigh.javavpk.core.Directory;
import com.connorhaigh.javavpk.core.Entry;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Enaium
 */
public class Util {
    public static String getValue(String text, String key) {
        String standard = "\"" + key + "\"";

        int i = text.indexOf(standard);
        String addonInfoRight = text.substring(i + (standard).length());

        //no "
        if (i == -1) {
            i = text.indexOf(key);
            addonInfoRight = text.substring(i + key.length());
        }

        //low case
        if (i == -1) {
            i = text.toLowerCase(Locale.ROOT).indexOf(standard.toLowerCase(Locale.ROOT));
            addonInfoRight = text.substring(i + (standard).toLowerCase(Locale.ROOT).length());
        }

        //no " and low case
        if (i == -1) {
            i = text.toLowerCase(Locale.ROOT).indexOf(key.toLowerCase(Locale.ROOT));
            addonInfoRight = text.substring(i + key.toLowerCase(Locale.ROOT).length());
        }

        int left = -1;
        int right = -1;
        int index = 0;
        for (char c : addonInfoRight.toCharArray()) {
            if (c == '"') {
                if (left == -1) {
                    left = index;
                    continue;
                }

                if (right == -1) {
                    right = index;
                    continue;
                }
            }

            if (left != -1 && right == -1 && c == '\n') {
                right = index;
            }
            index++;
        }

        if (left != -1 && right != -1) {
            try {
                return addonInfoRight.substring(left + 1, right + 1);
            } catch (Exception e) {
                return "NULL";
            }
        }
        return "NULL";
    }


    public static List<FileTableNode> getAll(File file) {
        List<FileTableNode> fileTableNodes = new ArrayList<>();
        for (File archiveFile : Objects.requireNonNull(file.listFiles())) {
            if (!archiveFile.getName().endsWith(".vpk")) {
                continue;
            }
            String s = null;
            try {
                Archive archive = new Archive(archiveFile);
                archive.load();
                for (Directory directory : archive.getDirectories()) {
                    for (Entry entry : directory.getEntries()) {
                        if (entry.getFullName().equals("addoninfo.txt")) {
                            s = new String(entry.readData(), StandardCharsets.UTF_8);
                        }
                    }
                }
            } catch (Exception e) {
                MessageUtil.error(e);
            }
            if (s != null) {
                fileTableNodes.add(new FileTableNode(Util.getValue(s, "addonTitle"), Util.getValue(s, "addonTagline"), Util.getValue(s, "addonAuthor"), archiveFile));
            } else {
                fileTableNodes.add(new FileTableNode("NULL", "NULL", "NULL", archiveFile));
            }
        }
        return fileTableNodes;
    }
}
