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
