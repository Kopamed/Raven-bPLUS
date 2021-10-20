package me.kopamed.lunarkeystrokes.utils;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.modules.client.Gui;
import me.superblaubeere27.client.notifications.Notification;
import me.superblaubeere27.client.notifications.NotificationManager;
import me.superblaubeere27.client.notifications.NotificationType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class NotificationRenderer {
    public static final NotificationRenderer notificationRenderer = new NotificationRenderer();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRender(TickEvent.RenderTickEvent event) {
        if (Gui.toggleNotification.isToggled()) NotificationManager.render();
    }

    public static void moduleStateChanged(Module m) {
        if (!Gui.toggleNotification.isToggled()) return;
        if (!m.getClass().equals(Gui.class)) {
            String s = m.isEnabled() ? "enabled" : "disabled";
            NotificationManager.show(new Notification(NotificationType.INFO, "Module " + s, m.getName() + " has been " + s, 1));
        }
    }
}
