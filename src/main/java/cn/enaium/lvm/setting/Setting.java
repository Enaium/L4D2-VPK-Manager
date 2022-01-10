package cn.enaium.lvm.setting;

import com.google.gson.annotations.Expose;

/**
 * @author Enaium
 */
public class Setting<T> {
    private String name;

    @Expose
    private T value;

    public Setting(T value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
