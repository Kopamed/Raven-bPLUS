package keystrokesmod.client.module.modules.world;

import java.util.HashMap;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.player.Freecam;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

public class AntiBot extends Module {
    private static final HashMap<EntityPlayer, Long> newEnt = new HashMap<>();
    private final long ms = 4000L;
    public static TickSetting a, dead;

    public AntiBot() {
        super("AntiBot", ModuleCategory.world);
        withEnabled(true);

        this.registerSetting(a = new TickSetting("Wait 80 ticks", false));
        this.registerSetting(dead = new TickSetting("Remove dead", true));
    }

    @Override
    public void onDisable() {
        newEnt.clear();
    }

    @Subscribe
    public void onEntityJoinWorld(ForgeEvent fe) {
        if (fe.getEvent() instanceof EntityJoinWorldEvent) {
            EntityJoinWorldEvent event = ((EntityJoinWorldEvent) fe.getEvent());

            if (!Utils.Player.isPlayerInGame())
                return;

            if (a.isToggled() && event.entity instanceof EntityPlayer && event.entity != mc.thePlayer) {
                newEnt.put((EntityPlayer) event.entity, System.currentTimeMillis());
            }
        }
    }

    @Subscribe
    public void onTick(TickEvent ev) {
        if (a.isToggled() && !newEnt.isEmpty()) {
            long now = System.currentTimeMillis();
            newEnt.values().removeIf(e -> e < now - 4000L);
        }

    }

    public static boolean bot(Entity en) {
        if (!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return false;
        if (Freecam.en != null && Freecam.en == en) {
            return true;
        }
        Module antiBot = Raven.moduleManager.getModuleByClazz(AntiBot.class);
        if ((antiBot != null && !antiBot.isEnabled()) || !Utils.Client.isHyp()) {
        } else if ((a.isToggled() && !newEnt.isEmpty() && newEnt.containsKey(en)) || en.getName().startsWith("§c")) {
            return true;
        } else if(en.isDead && dead.isToggled()) {
            return true;
        } else {
            String n = en.getDisplayName().getUnformattedText();
            if (n.contains("§")) {
                return n.contains("[NPC] ");
            }
            if (n.isEmpty() && en.getName().isEmpty()) {
                return true;
            }

            if (n.length() == 10) {
                int num = 0;
                int let = 0;
                char[] var4 = n.toCharArray();

                for (char c : var4) {
                    if (Character.isLetter(c)) {
                        if (Character.isUpperCase(c)) {
                            return false;
                        }

                        ++let;
                    } else {
                        if (!Character.isDigit(c)) {
                            return false;
                        }

                        ++num;
                    }
                }

                return num >= 2 && let >= 2;
            }
        }
        return false;
    }
}
