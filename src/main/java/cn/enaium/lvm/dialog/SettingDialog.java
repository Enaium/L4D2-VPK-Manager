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

package cn.enaium.lvm.dialog;

import cn.enaium.lvm.config.Config;
import cn.enaium.lvm.setting.EnableSetting;
import cn.enaium.lvm.setting.ModeSetting;
import cn.enaium.lvm.setting.Setting;
import cn.enaium.lvm.setting.StringSetting;
import cn.enaium.lvm.util.ConfigUtil;
import cn.enaium.lvm.util.MessageUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.Field;

import static cn.enaium.lvm.LVM.CONFIG;

/**
 * @author Enaium
 */
@SuppressWarnings("ALL")
public class SettingDialog extends Dialog {
    public SettingDialog() {
        super("Setting");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 1));
        Config config = CONFIG;

        for (Field field : config.getClass().getFields()) {
            try {
                Setting o = ((Setting) field.get(config));
                if (o instanceof EnableSetting) {
                    JCheckBox jCheckBox = new JCheckBox();

                    jCheckBox.setSelected(((EnableSetting) o).getValue());

                    jCheckBox.addActionListener(e -> {
                        o.setValue(jCheckBox.isSelected());
                    });

                    jPanel.add(new JPanel() {
                        {
                            add(new JLabel(o.getName()));
                            add(jCheckBox);
                        }
                    });
                } else if (o instanceof ModeSetting) {
                    JComboBox<String> jComboBox = new JComboBox<>();
                    ((ModeSetting) o).getMode().forEach(jComboBox::addItem);
                    jComboBox.setSelectedItem(((ModeSetting) o).getValue());
                    jComboBox.addActionListener(e -> {
                        o.setValue(jComboBox.getSelectedItem());
                    });
                    jPanel.add(new JPanel() {
                        {
                            add(new JLabel(o.getName()));
                            add(jComboBox);
                        }
                    });
                } else if (o instanceof StringSetting) {
                    JTextField jTextField = new JTextField(((StringSetting) o).getValue());
                    jTextField.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            o.setValue(jTextField.getText());
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            o.setValue(jTextField.getText());
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            o.setValue(jTextField.getText());
                        }
                    });

                    jPanel.add(new JPanel() {
                        {
                            add(new JLabel(o.getName()));
                            add(jTextField);
                        }
                    });
                }
            } catch (IllegalAccessException e) {
                MessageUtil.error(e);
            }
        }

        JScrollPane jScrollBar = new JScrollPane(jPanel);
        add(jScrollBar, BorderLayout.CENTER);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                ConfigUtil.save(CONFIG);
                dispose();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
}
