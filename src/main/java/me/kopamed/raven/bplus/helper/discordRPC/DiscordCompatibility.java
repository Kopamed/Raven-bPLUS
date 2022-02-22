package me.kopamed.raven.bplus.helper.discordRPC;

public class DiscordCompatibility {
    private final static boolean isCompatible;

    static {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();

        isCompatible = (
                !osArch.contains("arm") &&
                !osArch.contains("aarch64") &&
                !osName.toLowerCase().contains("mac")
        );
    }

    public static boolean isCompatible() {
        return isCompatible;
    }
}
