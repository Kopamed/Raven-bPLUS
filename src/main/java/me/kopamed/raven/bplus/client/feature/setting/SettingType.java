package me.kopamed.raven.bplus.client.feature.setting;

import me.kopamed.raven.bplus.client.feature.module.BindMode;

public enum SettingType {
    BOOLEAN,
    COMBO,
    DESCRIPTION,
    NUMBER,
    RANGE;

    public static SettingType getOrDefault(String s, SettingType defaultVal) {
        try {
            return SettingType.valueOf(s);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
