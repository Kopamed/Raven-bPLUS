package keystrokesmod.client.module.modules.other;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.Utils;

public class NameHider extends Module {
    public static DescriptionSetting a;
    public static String n = "ravenb++";
    public static String playerNick = "";

    public NameHider() {
        super("Name Hider", ModuleCategory.other);
        this.registerSetting(a = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": cname [name]"));
    }

    public static String format(String s) {
        if (mc.thePlayer != null) {
            s = playerNick.isEmpty() ? s.replace(mc.thePlayer.getName(), n) : s.replace(playerNick, n);
        }

        return s;
    }
}
