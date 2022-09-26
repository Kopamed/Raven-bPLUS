package keystrokesmod.client.module.modules.minigames;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.render.PlayerESP;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class MurderMystery extends Module {
    public static TickSetting alertMurderers;
    public static TickSetting searchDetectives;
    public static TickSetting announceMurder;
    private static final List<EntityPlayer> mur = new ArrayList();
    private static final List<EntityPlayer> det = new ArrayList();

    public MurderMystery() {
        super("Murder Mystery", ModuleCategory.minigames);
        this.registerSetting(alertMurderers = new TickSetting("Alert", true));
        this.registerSetting(searchDetectives = new TickSetting("Search detectives", true));
        this.registerSetting(announceMurder = new TickSetting("Announce murderer", false));
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof RenderWorldLastEvent) {
            if (Utils.Player.isPlayerInGame()) {
                PlayerESP p = (PlayerESP) Raven.moduleManager.getModuleByName("PlayerESP");
                assert p != null;
                if (p.isEnabled()) {
                    p.disable();
                }

                if (!this.inMMGame()) {
                    this.c();
                } else {
                    Iterator<EntityPlayer> entityPlayerIterator = mc.theWorld.playerEntities.iterator();

                    while (true) {
                        EntityPlayer entity;
                        do {
                            do {
                                do {
                                    if (!entityPlayerIterator.hasNext()) {
                                        return;
                                    }

                                    entity = entityPlayerIterator.next();
                                } while (entity == mc.thePlayer);
                            } while (entity.isInvisible());
                        } while (AntiBot.bot(entity));
                        String c4 = "&7[&cALERT&7]";
                        if (entity.getHeldItem() != null && entity.getHeldItem().hasDisplayName()) {
                            Item i = entity.getHeldItem().getItem();
                            if (i instanceof ItemSword || i instanceof ItemAxe || i instanceof ItemEnderPearl
                                    || i instanceof ItemHoe || i instanceof ItemPickaxe || i instanceof ItemFishingRod
                                    || entity.getHeldItem().getDisplayName().contains("Knife")) {

                                if (!mur.contains(entity)) {
                                    mur.add(entity);
                                    String c6 = "is a murderer!";
                                    if (alertMurderers.isToggled()) {
                                        String c5 = "note.pling";
                                        mc.thePlayer.playSound(c5, 1.0F, 1.0F);
                                        Utils.Player.sendMessageToSelf(c4 + " &e" + entity.getName() + " &3" + c6);
                                    }

                                    if (announceMurder.isToggled()) {
                                        String msg = Utils.Java.randomChoice(
                                                new String[] { entity.getName() + " " + c6, entity.getName() });
                                        mc.thePlayer.sendChatMessage(msg);
                                    }
                                }
                            } else if (i instanceof ItemBow && searchDetectives.isToggled() && !det.contains(entity)) {
                                det.add(entity);
                                String c7 = "has a bow!";
                                if (alertMurderers.isToggled()) {
                                    Utils.Player.sendMessageToSelf(c4 + " &e" + entity.getName() + " &3" + c7);
                                }

                                if (announceMurder.isToggled()) {
                                    mc.thePlayer.sendChatMessage(entity.getName() + " " + c7);
                                }

                            }
                        }

                        int rgb = Color.cyan.getRGB();
                        if (mur.contains(entity)) {
                            rgb = Color.red.getRGB();
                        } else if (det.contains(entity)) {
                            rgb = Color.green.getRGB();
                        }

                        Utils.HUD.drawBoxAroundEntity(entity, 2, 0.0D, 0.0D, rgb, false);
                    }
                }
            }
        }
    }

    private boolean inMMGame() {
        if (Utils.Client.isHyp()) {
            if (mc.thePlayer.getWorldScoreboard() == null
                    || mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) == null) {
                return false;
            }

            String d = mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1).getDisplayName();
            String c2 = "MYSTERY";
            String c1 = "MURDER";
            if (!d.contains(c1) && !d.contains(c2)) {
                return false;
            }

            Iterator var2 = Utils.Client.getPlayersFromScoreboard().iterator();

            while (var2.hasNext()) {
                String l = (String) var2.next();
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
        mur.clear();
        det.clear();
    }
}
