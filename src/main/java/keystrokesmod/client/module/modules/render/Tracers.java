package keystrokesmod.client.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.TickEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.setting.impl.RGBSetting;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class Tracers extends Module {
    public static TickSetting a;
    public static RGBSetting rgb;
    public static TickSetting e;
    public static TickSetting o;
    public static SliderSetting f, sl;
    private boolean g;
    private int rgb_c;

    public Tracers() {
        super("Tracers", ModuleCategory.render);
        this.registerSetting(a = new TickSetting("Show invis", true));
        this.registerSetting(f = new SliderSetting("Line Width", 1.0D, 1.0D, 5.0D, 1.0D));
        this.registerSetting(sl = new SliderSetting("Distance", 1.0D, 1.0D, 512.0D, 1.0D));
        this.registerSetting(rgb = new RGBSetting("Color:", 0, 255, 0));
        this.registerSetting(e = new TickSetting("Rainbow", false));
        this.registerSetting(o = new TickSetting("Redshift w distance", false));
    }

    @Override
    public void onEnable() {
        this.g = mc.gameSettings.viewBobbing;
        if (this.g)
            mc.gameSettings.viewBobbing = false;

    }

    @Override
    public void onDisable() {
        mc.gameSettings.viewBobbing = this.g;
    }

    @Subscribe
    public void onTick(TickEvent e) {
        if (mc.gameSettings.viewBobbing)
            mc.gameSettings.viewBobbing = false;
    }

    @Override
    public void guiUpdate() {
        this.rgb_c = rgb.getRGB();
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof RenderWorldLastEvent)
            if (Utils.Player.isPlayerInGame()) {
                int rgb = e.isToggled() ? Utils.Client.rainbowDraw(2L, 0L) : this.rgb_c;
                Iterator<EntityPlayer> var3 = mc.theWorld.playerEntities.iterator();

                while (true) {
                    EntityPlayer en;
                    do
                        do
                            do {
                                if (!var3.hasNext())
                                    return;

                                en = (EntityPlayer) var3.next();
                            } while (en == mc.thePlayer);
                        while (en.deathTime != 0);
                    while (!a.isToggled() && en.isInvisible());

                    if (!AntiBot.bot(en))
                        if (o.isToggled() && (mc.thePlayer.getDistanceToEntity(en) < 25)) {
                            // ik i can use a lot of tenary statements but my brain
                            int red = (int) (Math.abs(mc.thePlayer.getDistanceToEntity(en) - 25) * 10);
                            int green = Math.abs(red - 255);
                            int rgbs = new Color(red, green, this.rgb.getBlue()).getRGB();
                            Utils.HUD.dtl(en, rgbs, (float) f.getInput());
                        } else
                            Utils.HUD.dtl(en, rgb, (float) f.getInput());
                }
            }
    }
}
