package keystrokesmod.sToNkS.module.modules.client;

import keystrokesmod.sToNkS.main.Ravenbplus;
import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.ModuleDesc;
import keystrokesmod.sToNkS.module.ModuleSettingTick;
import keystrokesmod.sToNkS.utils.Utils;
import keystrokesmod.sToNkS.utils.Version;
import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class UpdateCheck extends Module {
    public static ModuleDesc howToUse;
    public static ModuleSettingTick copyToClipboard;
    public static ModuleSettingTick openLink;

    private Future<?> f;
    private final ExecutorService executor;
    private final Runnable task;

    public UpdateCheck() {
        super("Update", category.client, 0);

        this.registerSetting(howToUse = new ModuleDesc(Utils.Java.uf("command") + ": update"));
        this.registerSetting(copyToClipboard = new ModuleSettingTick("Copy to clipboard", true));
        this.registerSetting(openLink = new ModuleSettingTick("Open dl in browser", true));

        executor = Executors.newFixedThreadPool(1);
        task = () -> {
            if (Version.outdated()) {
                Ravenbplus.outdated = true;
                Utils.Player.sendMessageToSelf("The current version or Raven B+ is outdated. Visit https://github.com/Kopamed/Raven-bPLUS to download the latest version.");
                Utils.Player.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
            }

            if (Version.isBeta()) {
                Ravenbplus.beta = true;
                Utils.Player.sendMessageToSelf("Man is on beta and asking for stable. You mad bruv?");
                Utils.Player.sendMessageToSelf("https://github.com/Kopamed/Raven-bPLUS");
            } else {
                Utils.Player.sendMessageToSelf("You are on the latest public version!");
            }

            if (copyToClipboard.isToggled())
                if (Utils.Client.copyToClipboard(Ravenbplus.sourceLocation))
                    Utils.Player.sendMessageToSelf("Successfully copied download link to clipboard!");

            if (openLink.isToggled()) {
                try {
                    URL url = new URL(Ravenbplus.sourceLocation);
                    Utils.Client.openWebpage(url);
                } catch (MalformedURLException bruh) {
                    bruh.printStackTrace();
                    Utils.Player.sendMessageToSelf("&cFailed to open page! Please report this bug in Raven b+'s discord");
                }
            }

            this.disable();
        };
    }

    @FMLEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (f == null) {
            f = executor.submit(task);
            Utils.Player.sendMessageToSelf("Update check started !");
        } else if (f.isDone()) {
            f = executor.submit(task);
            Utils.Player.sendMessageToSelf("Update check started !");
        }
    }
}
