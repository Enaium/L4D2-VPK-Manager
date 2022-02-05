package cn.enaium.lvm.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import static cn.enaium.lvm.LVM.CONFIG;

/**
 * @author Enaium
 */
public class LangUtil {

    private LangUtil() {
        throw new IllegalAccessError("Utility");
    }

    public static String i18n(String key) {
        Locale locale = Locale.getDefault();
        String lang = locale.getLanguage() + "_" + locale.getCountry();
        if (!CONFIG.language.getValue().equals("System")) {
            lang = CONFIG.language.getValue();
        }
        try {
            URL url = LangUtil.class.getResource("/lang/" + lang + ".json");
            if (url == null) {
                url = LangUtil.class.getResource("/lang/en_US.json");
            }

            if (url == null) {
                RuntimeException runtimeException = new RuntimeException("Lang not Found!");
                MessageUtil.error(runtimeException);
                throw runtimeException;
            }
            InputStream inputStream = url.openStream();
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            String text = stringBuilder.toString();
            inputStream.close();
            JsonObject jsonObject = new Gson().fromJson(text, JsonObject.class);
            try {
                return jsonObject.get(key).getAsString();
            } catch (NullPointerException e) {
                MessageUtil.error(new NullPointerException(String.format("Lang not found \" %s \"", key)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
