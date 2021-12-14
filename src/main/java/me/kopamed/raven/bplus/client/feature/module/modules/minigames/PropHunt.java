package me.kopamed.raven.bplus.client.feature.module.modules.minigames;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.module.modules.world.AntiBot;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.helper.manager.ModuleManager;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PropHunt extends Module {
    public static BooleanSetting showSeekers;
    public static BooleanSetting showHiders;

    private ArrayList<Entity> hiders = new ArrayList<>();
    private ArrayList<Entity> seekers = new ArrayList<>();

    public PropHunt(){
        super("PropHunt", "Helps you in PropHunt on hypixel", ModuleCategory.Misc);
        this.registerSetting(showHiders = new BooleanSetting("Show Hiders", true));
        this.registerSetting(showSeekers = new BooleanSetting("Show Seekers", true));
    }

    @SubscribeEvent
    public void o(RenderWorldLastEvent e) {
        if (Utils.Player.isPlayerInGame()) {
            if (ModuleManager.playerESP.isToggled()) {
                ModuleManager.playerESP.disable();
            }

            if (ModuleManager.murderMystery.isToggled()) {
                ModuleManager.murderMystery.disable();
            }

            if (!this.inMMGame()) {
                this.c();
                Utils.Player.sendMessageToSelf(inMMGame() + "");
            } else {
                //if(mc.objectMouseOver != null)
                  //  Utils.Player.sendMessageToSelf(mc.objectMouseOver.toString());

                for(Entity entity : mc.theWorld.loadedEntityList){ // no enttities found
                    if(entity.isInvisible() || entity == mc.thePlayer)
                        continue;
                    System.out.println(entity.getDisplayName());
                    int rgb = Color.cyan.getRGB();
                    //if (mur.contains(entity)) {
                    //  rgb = Color.red.getRGB();
                    //} else if (det.contains(entity)) {
                    //   rgb = Color.green.getRGB();
                    //}

                    Utils.HUD.drawBoxAroundEntity(entity, 2, 0.0D, 0.0D, rgb, false);
                }
            }
        }
    }

    private boolean inMMGame() {
        if (Utils.Client.isHyp()) {
            if (mc.thePlayer.getWorldScoreboard() == null || mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) == null) {
                return false;
            }

            String d = mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
            String c2 = "HIDE";
            String c1 = "SEEK";
            if (!d.contains(c1) && !d.contains(c2)) {
                return false;
            }

            Iterator var2 = Utils.Client.getPlayersFromScoreboard().iterator();

            while(var2.hasNext()) {
                String l = (String)var2.next();
                String s = Utils.Java.str(l);
                String c3 = "Role:";
                if (s.contains(c3)) {
                    return true;
                }
            }
        }

        return false;
    }

    private void c() {
        hiders.clear();
        seekers.clear();
    }
}
