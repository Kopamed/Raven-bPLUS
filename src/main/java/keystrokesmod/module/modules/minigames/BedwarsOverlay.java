//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.minigames;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleDesc;
import keystrokesmod.module.ModuleSettingSlider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class BedwarsOverlay extends Module {

    public BedwarsOverlay() {
        super("BedwarsOverlay", Module.category.minigames, 0);
    }

    @SubscribeEvent
    public void killme(TickEvent.RenderTickEvent ev){
        if(ev.phase != TickEvent.Phase.END)return;

        Gui.drawRect(0, 0, 600, 350, 0x093c3f41);
    }
}
