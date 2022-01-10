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
