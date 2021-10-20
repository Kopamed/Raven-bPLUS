package me.kopamed.lunarkeystrokes.module.modules.fun;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Description;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.module.modules.world.AntiBot;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

import java.util.Iterator;

public class SlyPort extends Module {
    public static Description f;
    public static Slider r;
    public static Tick b;
    public static Tick d;
    public static Tick e;
    private final boolean s = false;

    public SlyPort() {
        super("SlyPort", Module.category.fun, 0);
        this.registerSetting(f = new Description("Teleport behind enemies."));
        this.registerSetting(r = new Slider("Range", 6.0D, 2.0D, 15.0D, 1.0D));
        this.registerSetting(e = new Tick("Aim", true));
        this.registerSetting(b = new Tick("Play sound", true));
        this.registerSetting(d = new Tick("Players only", true));
    }

    public void onEnable() {
        Entity en = this.ge();
        if (en != null) {
            this.tp(en);
        }

        this.disable();
    }

    private void tp(Entity en) {
        if (b.isToggled()) {
            mc.thePlayer.playSound("mob.endermen.portal", 1.0F, 1.0F);
        }

        Vec3 vec = en.getLookVec();
        double x = en.posX - vec.xCoord * 2.5D;
        double z = en.posZ - vec.zCoord * 2.5D;
        mc.thePlayer.setPosition(x, mc.thePlayer.posY, z);
        if (e.isToggled()) {
            Utils.Player.aim(en, 0.0F, false);
        }

    }

    private Entity ge() {
        Entity en = null;
        double r = Math.pow(SlyPort.r.getInput(), 2.0D);
        double dist = r + 1.0D;
        Iterator var6 = mc.theWorld.loadedEntityList.iterator();

        while(true) {
            Entity ent;
            do {
                do {
                    do {
                        do {
                            if (!var6.hasNext()) {
                                return en;
                            }

                            ent = (Entity)var6.next();
                        } while(ent == mc.thePlayer);
                    } while(!(ent instanceof EntityLivingBase));
                } while(((EntityLivingBase)ent).deathTime != 0);
            } while(SlyPort.d.isToggled() && !(ent instanceof EntityPlayer));

            if (!AntiBot.bot(ent)) {
                double d = mc.thePlayer.getDistanceSqToEntity(ent);
                if (!(d > r) && !(dist < d)) {
                    dist = d;
                    en = ent;
                }
            }
        }
    }
}
