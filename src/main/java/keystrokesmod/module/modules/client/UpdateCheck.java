package keystrokesmod.module.modules.client;

import keystrokesmod.utils.Utils;
import keystrokesmod.main.Ravenbplus;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.version;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.net.MalformedURLException;
import java.net.URL;

public class UpdateCheck extends Module {
    public static ModuleDesc howToUse;
    public static ModuleSettingTick copyToClipboard;
    public static ModuleSettingTick openLink;
    public UpdateCheck() {
        super("Update", category.client, 0);
        this.registerSetting(howToUse = new ModuleDesc(Utils.Java.uf("command") + ": update"));
        this.registerSetting(copyToClipboard = new ModuleSettingTick("Copy to clipboard", true));
        this.registerSetting(openLink = new ModuleSettingTick("Open dl in browser", true));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (version.outdated()) {
            Ravenbplus.outdated = true;
            Utils.Player.sendMessageToSelf("The current version or Raven B+ is outdated. Visit https://github.com/Kopamed/Raven-bPLUS to download the latest version.");
            Utils.Player.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
        }
        if (version.isBeta()) {
            Ravenbplus.beta = true;
            Utils.Player.sendMessageToSelf("Man is on beta and asking for stable. You mad bruv?");
            Utils.Player.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
        }
        else {
            Utils.Player.sendMessageToSelf("You are on the latest public version!");
        }
        if (copyToClipboard.isToggled()) {
            if (Utils.Client.copyToClipboard(Ravenbplus.sourceLocation))
                Utils.Player.sendMessageToSelf("Successfully copied download link to clipboard!");
        }
        if(openLink.isToggled()) {
            URL url = null;
            try {
                url = new URL(Ravenbplus.sourceLocation);
                Utils.Client.openWebpage(url);
            } catch (MalformedURLException bruh) {
                bruh.printStackTrace();
                Utils.Player.sendMessageToSelf("&cFailed to open page! Please report this bug in Raven b+'s discord");
            }
        }
        this.disable();
    }
}
