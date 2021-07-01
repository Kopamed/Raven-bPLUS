//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import keystrokesmod.*;
import keystrokesmod.module.Module;
import keystrokesmod.module.ModuleSetting;
import keystrokesmod.module.ModuleSetting2;
import keystrokesmod.module.modules.client.SelfDestruct;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestESP extends Module {
   public static ModuleSetting2 a;
   public static ModuleSetting2 b;
   public static ModuleSetting2 c;
   public static ModuleSetting d;

   public ChestESP() {
      super("ChestESP", Module.category.render, 0);
      a = new ModuleSetting2("Red", 0.0D, 0.0D, 255.0D, 1.0D);
      b = new ModuleSetting2("Green", 0.0D, 0.0D, 255.0D, 1.0D);
      c = new ModuleSetting2("Blue", 255.0D, 0.0D, 255.0D, 1.0D);
      d = new ModuleSetting("Rainbow", false);
      this.registerSetting(a);
      this.registerSetting(b);
      this.registerSetting(c);
      this.registerSetting(d);
   }

   @SubscribeEvent
   public void o(RenderWorldLastEvent ev) {
      if (ay.isPlayerInGame() && !SelfDestruct.destructed) {
         int rgb = d.isToggled() ? ay.gc(2L, 0L) : (new Color((int)a.getInput(), (int)b.getInput(), (int)c.getInput())).getRGB();
         Iterator var3 = mc.theWorld.loadedTileEntityList.iterator();

         while(true) {
            TileEntity te;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               te = (TileEntity)var3.next();
            } while(!(te instanceof TileEntityChest) && !(te instanceof TileEntityEnderChest));

            ru.re(te.getPos(), rgb, true);
         }
      }
   }
}
