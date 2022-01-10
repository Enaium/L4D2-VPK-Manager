package cn.enaium.lvm.setting;

import java.util.List;

/**
 * @author Enaium
 */
public class ModeSetting extends Setting<String> {

    private List<String> mode;

    public ModeSetting(String value) {
        super(value);
    }

    public List<String> getMode() {
        return mode;
    }

    public void setMode(List<String> mode) {
        this.mode = mode;
    }
}
