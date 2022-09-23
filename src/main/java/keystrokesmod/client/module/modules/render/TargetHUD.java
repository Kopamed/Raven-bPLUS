package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.ForgeEvent;
import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.TickSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;

public class TargetHUD extends Module {
    public TickSetting editPosition;
    public int height, width;
    public FontRenderer fr;
    private AbstractClientPlayer target;
    ScaledResolution sr;

    public TargetHUD() {
        super("Target HUD", ModuleCategory.render);
        sr = new ScaledResolution(Minecraft.getMinecraft());
        height = sr.getScaledHeight();
        width = sr.getScaledWidth();
        fr = mc.fontRendererObj;
    }

    @Subscribe
    public void onForgeEvent(ForgeEvent fe) {
        if (fe.getEvent() instanceof AttackEntityEvent) {
            AttackEntityEvent e = (AttackEntityEvent) fe.getEvent();
            System.out.println(e.target instanceof AbstractClientPlayer);
            System.out.println(e.target);
            EntityPlayer ep = (EntityPlayer) e.target;
            
            //e.target = AbstractClientPlayer.getLocationSkin();
        }
    }

    @Subscribe
    public void onRender2d(Render2DEvent e) {
        /*try {
            ResourceLocation skin = AbstractClientPlayer.getLocationSkin();
            Minecraft.getMinecraft().getTextureManager().bindTexture(skin);
            GL11.glEnable(GL11.GL_BLEND);
            Gui.drawScaledCustomSizeModalRect(0, 0, 50, 50, 50, 50, 50, 50, 50, 50);
            GL11.glDisable(GL11.GL_BLEND);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } */
    }
}
