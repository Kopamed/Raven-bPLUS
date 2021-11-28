package me.kopamed.raven.bplus.client.feature.module.modules.render;

import java.awt.Color;
import java.util.Iterator;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.NumberSetting;
import me.kopamed.raven.bplus.client.feature.module.modules.client.SelfDestruct;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestESP extends Module {
   public static NumberSetting a;
   public static NumberSetting b;
   public static NumberSetting c;
   public static BooleanSetting d;

   public ChestESP() {
      super("ChestESP", ModuleCategory.Render, 0);
      a = new NumberSetting("Red", 0.0D, 0.0D, 255.0D, 1.0D);
      b = new NumberSetting("Green", 0.0D, 0.0D, 255.0D, 1.0D);
      c = new NumberSetting("Blue", 255.0D, 0.0D, 255.0D, 1.0D);
      d = new BooleanSetting("Rainbow", false);
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
