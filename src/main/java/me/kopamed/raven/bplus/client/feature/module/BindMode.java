package me.kopamed.raven.bplus.client.feature.module;

public enum BindMode {
    HOLD,
    TOGGLE;

    public static BindMode getOrDefault(String s, BindMode defaultVal) {
        try {
            return BindMode.valueOf(s);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
