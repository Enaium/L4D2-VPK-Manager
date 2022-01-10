package cn.enaium.lvm.config;

import cn.enaium.lvm.setting.ModeSetting;
import cn.enaium.lvm.setting.StringSetting;
import com.google.gson.annotations.Expose;

import java.util.Arrays;

/**
 * @author Enaium
 */
public class Config {
    @Expose
    public StringSetting l4d2Dir = new StringSetting("C:/Program Files (x86)/Steam/steamapps/common/Left 4 Dead 2");
    @Expose
    public ModeSetting language = new ModeSetting("System");
    @Expose
    public ModeSetting theme = new ModeSetting("Light");

    public void init() {
        l4d2Dir.setName("L4D2 Dir");

        language.setName("Language");
        language.setMode(Arrays.asList("System", "en_US", "zh_CN"));

        theme.setName("Theme");
        theme.setMode(Arrays.asList("Light", "Dark"));
    }
}
