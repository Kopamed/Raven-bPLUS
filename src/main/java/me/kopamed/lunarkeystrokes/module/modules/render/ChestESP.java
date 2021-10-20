//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import me.kopamed.lunarkeystrokes.module.Module;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import me.kopamed.lunarkeystrokes.module.setting.settings.Slider;
import me.kopamed.lunarkeystrokes.module.modules.client.SelfDestruct;
import me.kopamed.lunarkeystrokes.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestESP extends Module {
   public static Slider a;
   public static Slider b;
   public static Slider c;
   public static Tick d;

   public ChestESP() {
      super("ChestESP", Module.category.render, 0);
      a = new Slider("Red", 0.0D, 0.0D, 255.0D, 1.0D);
      b = new Slider("Green", 0.0D, 0.0D, 255.0D, 1.0D);
      c = new Slider("Blue", 255.0D, 0.0D, 255.0D, 1.0D);
      d = new Tick("Rainbow", false);
      this.registerSetting(a);
      this.registerSetting(b);
      this.registerSetting(c);
      this.registerSetting(d);
   }

   @SubscribeEvent
   public void o(RenderWorldLastEvent ev) {
      if (Utils.Player.isPlayerInGame() && !SelfDestruct.destructed) {
         int rgb = d.isToggled() ? Utils.Client.rainbowDraw(2L, 0L) : (new Color((int)a.getInput(), (int)b.getInput(), (int)c.getInput())).getRGB();
         Iterator var3 = mc.theWorld.loadedTileEntityList.iterator();

         while(true) {
            TileEntity te;
            do {
               if (!var3.hasNext()) {
                  return;
               }

               te = (TileEntity)var3.next();
            } while(!(te instanceof TileEntityChest) && !(te instanceof TileEntityEnderChest));

            Utils.HUD.re(te.getPos(), rgb, true);
         }
      }
   }
}
