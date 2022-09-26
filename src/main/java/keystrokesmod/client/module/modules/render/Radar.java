package keystrokesmod.client.module.modules.render;

import com.google.common.eventbus.Subscribe;

import keystrokesmod.client.event.impl.Render2DEvent;
import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.utils.RenderUtils;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;

public class Radar extends Module {

    private int x = 50, y = 50, width = 50, height = 50;
    private SliderSetting distance;
    
    public Radar() {
        super("Radar", ModuleCategory.render);
        this.registerSetting(distance = new SliderSetting("distance", 25, 5, 100, 1));
    }

    @Subscribe
    public void render2D(Render2DEvent e) {
        if(!Utils.Player.isPlayerInGame() || mc.currentScreen != null)
            return;
        int centreX = x + width/2, centreY = y + height/2;
        RenderUtils.drawBorderedRoundedRect(x, y, x + width, y + height, 3, 3, 0xFFFFFFFF, 0xFF00FF00);
        Gui.drawRect(centreX - 1, centreY - 1, centreX + 1, centreY + 1, 0xFFFF00FF);
        for(Entity en : mc.theWorld.playerEntities) {
            int radius = (int) mc.thePlayer.getDistanceToEntity(en);
            if(radius > distance.getInput()) continue;
            int theta = (int) Utils.Player.fovFromEntity(en) -180; //why do i need to put the 180 here huh
            int enX = (int) (radius * Math.sin(Math.toRadians(theta))), enY =(int) (radius * Math.cos(Math.toRadians(theta)));
            Gui.drawRect(centreX + enX, centreY + enY, centreX + enX + 1, centreY + enY + 1, 0xFFFF0000);
        }
    }
}
