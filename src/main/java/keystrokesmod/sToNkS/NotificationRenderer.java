package keystrokesmod.sToNkS;

import keystrokesmod.sToNkS.lib.fr.jmraich.rax.event.FMLEvent;
import keystrokesmod.sToNkS.module.Module;
import keystrokesmod.sToNkS.module.modules.client.GuiModule;
import keystrokesmod.sToNkS.lib.me.superblaubeere27.client.notifications.Notification;
import keystrokesmod.sToNkS.lib.me.superblaubeere27.client.notifications.NotificationManager;
import keystrokesmod.sToNkS.lib.me.superblaubeere27.client.notifications.NotificationType;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NotificationRenderer {
    @FMLEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (GuiModule.toggleNotification.isToggled()) NotificationManager.render();
    }

    public static void moduleStateChanged(Module m) {
        if (!GuiModule.toggleNotification.isToggled()) return;
        if (!m.getClass().equals(GuiModule.class)) {
            String s = m.isEnabled() ? "enabled" : "disabled";
            NotificationManager.show(new Notification(NotificationType.INFO, "Module " + s, m.getName() + " has been " + s, 1));
        }
    }
}
