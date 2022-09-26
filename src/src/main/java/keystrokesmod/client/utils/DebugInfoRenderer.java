package keystrokesmod.client.utils;

import keystrokesmod.client.main.Raven;
import keystrokesmod.client.module.modules.player.Freecam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;

import java.awt.*;

public class DebugInfoRenderer extends net.minecraft.client.gui.Gui {
    private static final Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onRenderTick(RenderTickEvent ev) {
        if (Raven.debugger && ev.phase == Phase.END && Utils.Player.isPlayerInGame()) {
            if (mc.currentScreen == null) {
                ScaledResolution res = new ScaledResolution(mc);
                double bps = Utils.Player.getPlayerBPS(Freecam.en == null ? mc.thePlayer : Freecam.en, 2);
                int rgb;
                if (bps < 10.0D) {
                    rgb = Color.green.getRGB();
                } else if (bps < 30.0D) {
                    rgb = Color.yellow.getRGB();
                } else if (bps < 60.0D) {
                    rgb = Color.orange.getRGB();
                } else if (bps < 160.0D) {
                    rgb = Color.red.getRGB();
                } else {
                    rgb = Color.black.getRGB();
                }

                String t = bps + "bps";
                int x = res.getScaledWidth() / 2 - mc.fontRendererObj.getStringWidth(t) / 2;
                int y = res.getScaledHeight() / 2 + 15;
                mc.fontRendererObj.drawString(t, (float) x, (float) y, rgb, false);
            }
        }
    }
}
