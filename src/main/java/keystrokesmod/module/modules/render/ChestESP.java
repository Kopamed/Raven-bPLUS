//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSettingTick;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.modules.client.SelfDestruct;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestESP extends Module {
   public static ModuleSettingSlider a;
   public static ModuleSettingSlider b;
   public static ModuleSettingSlider c;
   public static ModuleSettingTick d;

   public ChestESP() {
      super("ChestESP", Module.category.render, 0);
      a = new ModuleSettingSlider("Red", 0.0D, 0.0D, 255.0D, 1.0D);
      b = new ModuleSettingSlider("Green", 0.0D, 0.0D, 255.0D, 1.0D);
      c = new ModuleSettingSlider("Blue", 255.0D, 0.0D, 255.0D, 1.0D);
      d = new ModuleSettingTick("Rainbow", false);
      this.registerSetting(a);
      this.registerSetting(b);
      this.registerSetting(c);
      this.registerSetting(d);
   }

   @SubscribeEvent
   public void o(RenderWorldLastEvent ev) {
      if (ay.isPlayerInGame() && !SelfDestruct.destructed) {
         int rgb = d.isToggled() ? ay.rainbowDraw(2L, 0L) : (new Color((int)a.getInput(), (int)b.getInput(), (int)c.getInput())).getRGB();
         Iterator var3 = mc.theWorld.loadedTileEntityList.iterator();

         while(true) {
            TileEntity te;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               te = (TileEntity)var3.next();
            } while(!(te instanceof TileEntityChest) && !(te instanceof TileEntityEnderChest));

            HUDUtils.re(te.getPos(), rgb, true);
         }
      }
   }
}
