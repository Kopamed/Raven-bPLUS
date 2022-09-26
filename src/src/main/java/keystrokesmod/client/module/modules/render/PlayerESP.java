package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;
import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.DescriptionSetting;
import keystrokesmod.client.module.setting.impl.RGBSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.Iterator;

public class PlayerESP extends Module {
    public static DescriptionSetting g;
    public static SliderSetting i;
    public static SliderSetting j;
    public static TickSetting d;
    public static TickSetting f;
    public static TickSetting h;
    public static TickSetting t1;
    public static TickSetting t2;
    public static TickSetting t3;
    public static TickSetting t4;
    public static TickSetting t5;
    public static TickSetting t6;
    public static TickSetting t7;
    public static RGBSetting rgb;
    private int rgb_c;

    public PlayerESP() {
        super("PlayerESP", ModuleCategory.render);
        this.registerSetting(rgb = new RGBSetting("RGB", 0, 255, 0));
        this.registerSetting(d = new TickSetting("Rainbow", false));
        this.registerSetting(g = new DescriptionSetting("ESP Types"));
        this.registerSetting(t3 = new TickSetting("2D", false));
        this.registerSetting(t5 = new TickSetting("Arrow", false));
        this.registerSetting(t1 = new TickSetting("Box", false));
        this.registerSetting(t4 = new TickSetting("Health", true));
        this.registerSetting(t6 = new TickSetting("Ring", false));
        this.registerSetting(t2 = new TickSetting("Shaded", false));
        this.registerSetting(i = new SliderSetting("Expand", 0.0D, -0.3D, 2.0D, 0.1D));
        this.registerSetting(j = new SliderSetting("X-Shift", 0.0D, -35.0D, 10.0D, 1.0D));
        this.registerSetting(f = new TickSetting("Show invis", true));
        this.registerSetting(h = new TickSetting("Red on damage", true));
        this.registerSetting(t7 = new TickSetting("Match Chestplate", false));
    }

    public void onDisable() {
        Utils.HUD.ring_c = false;
    }

    public void guiUpdate() {
        this.rgb_c = (new Color(rgb.getRed(), rgb.getGreen(), rgb.getBlue()).getRGB());
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof RenderWorldLastEvent) {
            if (Utils.Player.isPlayerInGame()) {
                int rgb = d.isToggled() ? 0 : this.rgb_c;
                Iterator var3;
                if (Raven.debugger) {
                    var3 = mc.theWorld.loadedEntityList.iterator();

                    while (var3.hasNext()) {
                        Entity en = (Entity) var3.next();
                        if (en instanceof EntityLivingBase && en != mc.thePlayer) {
                            this.r(en, rgb);
                        }
                    }

                } else {
                    var3 = mc.theWorld.playerEntities.iterator();

                    while (true) {
                        EntityPlayer en;
                        do {
                            do {
                                do {
                                    if (!var3.hasNext()) {
                                        return;
                                    }

                                    en = (EntityPlayer) var3.next();
                                } while (en == mc.thePlayer);
                            } while (en.deathTime != 0);
                        } while (!f.isToggled() && en.isInvisible());

                        if (!AntiBot.bot(en)) {
                            if (t7.isToggled() && getColor(en.getCurrentArmor(2)) > 0) {
                                int E = new Color(getColor(en.getCurrentArmor(2))).getRGB();
                                this.r(en, E);
                            } else {
                                this.r(en, rgb);
                            }
                        }
                    }
                }
            }
        }
    }

    public int getColor(ItemStack stack) {
        if (stack == null)
            return -1; // not wearing a chestplate
        NBTTagCompound nbttagcompound = stack.getTagCompound();
        if (nbttagcompound != null) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
            if (nbttagcompound1 != null && nbttagcompound1.hasKey("color", 3)) {
                return nbttagcompound1.getInteger("color");
            }
        }

        return -2; // chestplate has no colour
    }

    private void r(Entity en, int rgb) {
        if (t1.isToggled()) {
            Utils.HUD.drawBoxAroundEntity(en, 1, i.getInput(), j.getInput(), rgb, h.isToggled());
        }

        if (t2.isToggled()) {
            Utils.HUD.drawBoxAroundEntity(en, 2, i.getInput(), j.getInput(), rgb, h.isToggled());
        }

        if (t3.isToggled()) {
            Utils.HUD.drawBoxAroundEntity(en, 3, i.getInput(), j.getInput(), rgb, h.isToggled());
        }

        if (t4.isToggled()) {
            Utils.HUD.drawBoxAroundEntity(en, 4, i.getInput(), j.getInput(), rgb, h.isToggled());
        }

        if (t5.isToggled()) {
            Utils.HUD.drawBoxAroundEntity(en, 5, i.getInput(), j.getInput(), rgb, h.isToggled());
        }

        if (t6.isToggled()) {
            Utils.HUD.drawBoxAroundEntity(en, 6, i.getInput(), j.getInput(), rgb, h.isToggled());
        }

    }
}
