package me.kopamed.raven.bplus.client.feature.module.modules.client;

import me.kopamed.raven.bplus.client.Raven;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.helper.manager.version.Version;
import me.kopamed.raven.bplus.helper.manager.version.VersionManager;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.setting.settings.DescriptionSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.net.MalformedURLException;
import java.net.URL;

public class UpdateCheck extends Module {
    public static DescriptionSetting howToUse;
    public static BooleanSetting copyToClipboard;
    public static BooleanSetting openLink;
    public UpdateCheck() {
        super("Update", ModuleCategory.Misc, 0);
        this.registerSetting(howToUse = new DescriptionSetting(Utils.Java.uf("command") + ": update"));
        this.registerSetting(copyToClipboard = new BooleanSetting("Copy to clipboard", true));
        this.registerSetting(openLink = new BooleanSetting("Open dl in browser", true));
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        VersionManager versionManager = Raven.client.getVersionManager();
        Version now = versionManager.getClientVersion();
        Version nvw = versionManager.getLatestVersion();

        if (nvw.isNewerThan(now)) {
            Utils.Player.sendMessageToSelf("The current version or Raven B+ is outdated. Visit https://github.com/Kopamed/Raven-bPLUS to download the latest version.");
            Utils.Player.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
        }
        if (now.isNewerThan(nvw)) {
            Utils.Player.sendMessageToSelf("Man is on beta and asking for stable. You mad bruv?"); // todo change this weird gay shit
            Utils.Player.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
        }
        else {
            Utils.Player.sendMessageToSelf("You are on the latest public version!");
        }

        if (copyToClipboard.isToggled()) {
            if (Utils.Client.copyToClipboard(VersionManager.sourceURL))
                Utils.Player.sendMessageToSelf("Successfully copied download link to clipboard!");
        }
        if(openLink.isToggled()) {
            URL url = null;
            try {
                url = new URL(VersionManager.sourceURL);
                Utils.Client.openWebpage(url);
            } catch (MalformedURLException bruh) {
                bruh.printStackTrace();
                Utils.Player.sendMessageToSelf("&cFailed to open page! Please report this bug in Raven b+'s discord");
            }
        }
        this.disable();
    }
}
