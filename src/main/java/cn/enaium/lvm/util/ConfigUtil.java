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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Enaium
 */
public class ConfigUtil {

    private ConfigUtil() {
        throw new IllegalAccessError("Utility");
    }

    public static <T> T read(Class<?> t) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.fromJson(new String(Files.readAllBytes(new File(System.getProperty("user.dir"), "config.json").toPath()), StandardCharsets.UTF_8), (Type) t);
        } catch (IOException ioException) {
            try {
                save(t.newInstance());
            } catch (InstantiationException | IllegalAccessException reflectiveOperationException) {
                MessageUtil.error(reflectiveOperationException);
            }
        }
        return read(t);
    }

    public static <T> void save(T t) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            Files.write(new File(System.getProperty("."), "config.json").toPath(), gson.toJson(t).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            MessageUtil.error(e);
        }
    }
}
