package keystrokesmod.client.module.modules.other;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.util.ChatComponentText;

public class FakeChat extends Module {
    public static DescriptionSetting a;
    public static String msg = "&eThis is a fake chat message.";
    public static final String command = "fakechat";
    public static final String c4 = "&cInvalid message.";

    public FakeChat() {
        super("Fake Chat", ModuleCategory.other);
        this.registerSetting(
                a = new DescriptionSetting(Utils.Java.capitalizeWord("command") + ": " + command + " [msg]"));
    }

    public void onEnable() {
        if (msg.contains("\\n")) {
            String[] split = msg.split("\\\\n");

            for (String s : split) {
                this.sm(s);
            }
        } else {
            this.sm(msg);
        }

        this.disable();
    }

    private void sm(String txt) {
        mc.thePlayer.addChatMessage(new ChatComponentText(Utils.Client.reformat(txt)));
    }
}
