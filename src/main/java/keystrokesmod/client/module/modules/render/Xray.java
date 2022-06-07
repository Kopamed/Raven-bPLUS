package keystrokesmod.client.module.modules.render;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.SliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class Xray extends Module {
   public static SliderSetting r;
   public static TickSetting a;
   public static TickSetting b;
   public static TickSetting c;
   public static TickSetting d;
   public static TickSetting e;
   public static TickSetting f;
   public static TickSetting g;
   public static TickSetting h;
   private java.util.Timer t;
   private List<BlockPos> ren;
   private final long per = 200L;

   public Xray() {
      super("Xray", ModuleCategory.render);
      this.registerSetting(r = new SliderSetting("Range", 20.0D, 5.0D, 50.0D, 1.0D));
      this.registerSetting(a = new TickSetting("Iron", true));
      this.registerSetting(b = new TickSetting("Gold", true));
      this.registerSetting(c = new TickSetting("Diamond", true));
      this.registerSetting(d = new TickSetting("Emerald", true));
      this.registerSetting(e = new TickSetting("Lapis", true));
      this.registerSetting(f = new TickSetting("Redstone", true));
      this.registerSetting(g = new TickSetting("Coal", true));
      this.registerSetting(h = new TickSetting("Spawner", true));
   }

   public void onEnable() {
      this.ren = new ArrayList<>();
      (this.t = new java.util.Timer()).scheduleAtFixedRate(this.t(), 0L, 200L);
   }

   public void onDisable() {
      if (this.t != null) {
         this.t.cancel();
         this.t.purge();
         this.t = null;
      }

   }

   private TimerTask t() {
      return new TimerTask() {
         public void run() {
            Xray.this.ren.clear();
            int ra = (int)Xray.r.getInput();

            for(int y = ra; y >= -ra; --y) {
               for(int x = -ra; x <= ra; ++x) {
                  for(int z = -ra; z <= ra; ++z) {
                     if (Utils.Player.isPlayerInGame()) {
                        BlockPos p = new BlockPos(Module.mc.thePlayer.posX + (double)x, Module.mc.thePlayer.posY + (double)y, Module.mc.thePlayer.posZ + (double)z);
                        Block bl = Module.mc.theWorld.getBlockState(p).getBlock();
                        if (Xray.a.isToggled() && bl.equals(Blocks.iron_ore) || Xray.b.isToggled() && bl.equals(Blocks.gold_ore) || Xray.c.isToggled() && bl.equals(Blocks.diamond_ore) || Xray.d.isToggled() && bl.equals(Blocks.emerald_ore) || Xray.e.isToggled() && bl.equals(Blocks.lapis_ore) || Xray.f.isToggled() && bl.equals(Blocks.redstone_ore) || Xray.g.isToggled() && bl.equals(Blocks.coal_ore) || Xray.h.isToggled() && bl.equals(Blocks.mob_spawner)) {
                           Xray.this.ren.add(p);
                        }
                     }
                  }
               }
            }

         }
      };
   }

   @SubscribeEvent
   public void orl(RenderWorldLastEvent ev) {
      if (Utils.Player.isPlayerInGame() && !this.ren.isEmpty()) {
         List<BlockPos> tRen = new ArrayList<>(this.ren);

         for (BlockPos p : tRen) {
            this.dr(p);
         }
      }

   }

   private void dr(BlockPos p) {
      int[] rgb = this.c(mc.theWorld.getBlockState(p).getBlock());
      if (rgb[0] + rgb[1] + rgb[2] != 0) {
         Utils.HUD.re(p, (new Color(rgb[0], rgb[1], rgb[2])).getRGB(), true);
      }

   }

   private int[] c(Block b) {
      int red = 0;
      int green = 0;
      int blue = 0;
      if (b.equals(Blocks.iron_ore)) {
         red = 255;
         green = 255;
         blue = 255;
      } else if (b.equals(Blocks.gold_ore)) {
         red = 255;
         green = 255;
      } else if (b.equals(Blocks.diamond_ore)) {
         green = 220;
         blue = 255;
      } else if (b.equals(Blocks.emerald_ore)) {
         red = 35;
         green = 255;
      } else if (b.equals(Blocks.lapis_ore)) {
         green = 50;
         blue = 255;
      } else if (b.equals(Blocks.redstone_ore)) {
         red = 255;
      } else if (b.equals(Blocks.mob_spawner)) {
         red = 30;
         blue = 135;
      }

      return new int[]{red, green, blue};
   }
}
