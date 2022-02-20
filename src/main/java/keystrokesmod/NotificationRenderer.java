package keystrokesmod;

import keystrokesmod.module.Module;
import keystrokesmod.module.modules.client.GuiModule;
import me.superblaubeere27.client.notifications.Notification;
import me.superblaubeere27.client.notifications.NotificationManager;
import me.superblaubeere27.client.notifications.NotificationType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NotificationRenderer {
    @SubscribeEvent(priority = EventPriority.HIGH)
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
