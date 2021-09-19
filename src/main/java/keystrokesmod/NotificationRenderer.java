package keystrokesmod;

import keystrokesmod.module.Module;
import keystrokesmod.module.modules.HUD;
import keystrokesmod.module.modules.client.Gui;
import me.superblaubeere27.client.notifications.Notification;
import me.superblaubeere27.client.notifications.NotificationManager;
import me.superblaubeere27.client.notifications.NotificationType;

public class NotificationRenderer {
    /**
     * @see keystrokesmod.tweaker.transformers.TransformerGuiIngame_optForge
     */
    public static void render() {
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
