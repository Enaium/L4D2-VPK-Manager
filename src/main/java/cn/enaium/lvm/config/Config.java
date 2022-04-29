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
