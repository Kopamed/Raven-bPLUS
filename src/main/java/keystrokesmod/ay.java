//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.ModuleSetting2;
import keystrokesmod.module.modules.combat.AutoClicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Mouse;

public class ay {
   private static final Random rand = new Random();
   public static final Minecraft mc = Minecraft.getMinecraft();
   public static final String md = new String(new char[]{'M', 'o', 'd', 'e', ':', ' '});
   private static Field t = null;
   private static Field g = null;
   private static Field f = null;
   private static Field h = null;

   public static void su() {
      try {
         t = Minecraft.class.getDeclaredField("field_71428_T");
      } catch (Exception var4) {
         try {
            t = Minecraft.class.getDeclaredField("timer");
         } catch (Exception var3) {
         }
      }

      if (t != null) {
         t.setAccessible(true);
      }

      try {
         g = MouseEvent.class.getDeclaredField("button");
         f = MouseEvent.class.getDeclaredField("buttonstate");
         h = Mouse.class.getDeclaredField("buttons");
      } catch (Exception var2) {
      }

   }

   public static void sc(int t, boolean s) {
      if (g != null && f != null && h != null) {
         MouseEvent m = new MouseEvent();

         try {
            g.setAccessible(true);
            g.set(m, t);
            f.setAccessible(true);
            f.set(m, s);
            MinecraftForge.EVENT_BUS.post(m);
            h.setAccessible(true);
            ByteBuffer bf = (ByteBuffer)h.get((Object)null);
            h.setAccessible(false);
            bf.put(t, (byte)(s ? 1 : 0));
         } catch (IllegalAccessException var4) {
         }

      }
   }

   public static void sm(String txt) {
      if (isPlayerInGame()) {
         String m = r("&7[&dR&7]&r " + txt);
         mc.thePlayer.addChatMessage(new ChatComponentText(m));
      }
   }

   public static String r(String txt) {
      return txt.replaceAll("&", "§");
   }

   public static void b(ModuleSetting2 c, ModuleSetting2 d) {
      if (c.getInput() > d.getInput()) {
         double p = c.getInput();
         c.setValue(d.getInput());
         d.setValue(p);
      }

   }

   public static double ranModuleVal(ModuleSetting2 a, ModuleSetting2 b, Random r) {
      return a.getInput() == b.getInput() ? a.getInput() : a.getInput() + r.nextDouble() * (b.getInput() - a.getInput());
   }

   public static boolean isPlayerInGame() {
      return mc.thePlayer != null && mc.theWorld != null;
   }

   public static boolean isHyp() {
      return !mc.isSingleplayer() && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
   }

   public static int f() {
      return mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
   }

   public static net.minecraft.util.Timer gt() {
      try {
         return (net.minecraft.util.Timer)t.get(mc);
      } catch (IndexOutOfBoundsException var1) {
         return null;
      } catch ( IllegalAccessException var112) {
         return null;
      }
   }

   public static void rt() {
      try {
         gt().timerSpeed = 1.0F;
      } catch (NullPointerException var1) {
      }

   }

   public static Random rand() {
      return rand;
   }

   public static boolean im() {
      return mc.thePlayer.moveForward != 0.0F || mc.thePlayer.moveStrafing != 0.0F;
   }

   public static void aim(Entity en, float ps, boolean pc) {
      if (en != null) {
         float[] t = gr(en);
         if (t != null) {
            float y = t[0];
            float p = t[1] + 4.0F + ps;
            if (pc) {
               mc.getNetHandler().addToSendQueue(new C05PacketPlayerLook(y, p, mc.thePlayer.onGround));
            } else {
               mc.thePlayer.rotationYaw = y;
               mc.thePlayer.rotationPitch = p;
            }
         }

      }
   }

   public static float[] gr(Entity q) {
      if (q == null) {
         return null;
      } else {
         double diffX = q.posX - mc.thePlayer.posX;
         double diffY;
         if (q instanceof EntityLivingBase) {
            EntityLivingBase en = (EntityLivingBase)q;
            diffY = en.posY + (double)en.getEyeHeight() * 0.9D - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
         } else {
            diffY = (q.getEntityBoundingBox().minY + q.getEntityBoundingBox().maxY) / 2.0D - (mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight());
         }

         double diffZ = q.posZ - mc.thePlayer.posZ;
         double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
         float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
         float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
         return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
      }
   }

   public static double n(Entity en) {
      return ((double)(mc.thePlayer.rotationYaw - m(en)) % 360.0D + 540.0D) % 360.0D - 180.0D;
   }

   public static float m(Entity ent) {
      double x = ent.posX - mc.thePlayer.posX;
      double z = ent.posZ - mc.thePlayer.posZ;
      double yaw = Math.atan2(x, z) * 57.2957795D;
      return (float)(yaw * -1.0D);
   }

   public static boolean fov(Entity entity, float fov) {
      fov = (float)((double)fov * 0.5D);
      double v = ((double)(mc.thePlayer.rotationYaw - m(entity)) % 360.0D + 540.0D) % 360.0D - 180.0D;
      return v > 0.0D && v < (double)fov || (double)(-fov) < v && v < 0.0D;
   }

   public static void ss(double s, boolean m) {
      if (!m || im()) {
         mc.thePlayer.motionX = -Math.sin((double)gd()) * s;
         mc.thePlayer.motionZ = Math.cos((double)gd()) * s;
      }
   }

   public static void ss2(double s) {
      double forward = (double)mc.thePlayer.movementInput.moveForward;
      double strafe = (double)mc.thePlayer.movementInput.moveStrafe;
      float yaw = mc.thePlayer.rotationYaw;
      if (forward == 0.0D && strafe == 0.0D) {
         mc.thePlayer.motionX = 0.0D;
         mc.thePlayer.motionZ = 0.0D;
      } else {
         if (forward != 0.0D) {
            if (strafe > 0.0D) {
               yaw += (float)(forward > 0.0D ? -45 : 45);
            } else if (strafe < 0.0D) {
               yaw += (float)(forward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (forward > 0.0D) {
               forward = 1.0D;
            } else if (forward < 0.0D) {
               forward = -1.0D;
            }
         }

         double rad = Math.toRadians((double)(yaw + 90.0F));
         double sin = Math.sin(rad);
         double cos = Math.cos(rad);
         mc.thePlayer.motionX = forward * s * cos + strafe * s * sin;
         mc.thePlayer.motionZ = forward * s * sin - strafe * s * cos;
      }

   }

   public static float gd() {
      float yw = mc.thePlayer.rotationYaw;
      if (mc.thePlayer.moveForward < 0.0F) {
         yw += 180.0F;
      }

      float f;
      if (mc.thePlayer.moveForward < 0.0F) {
         f = -0.5F;
      } else if (mc.thePlayer.moveForward > 0.0F) {
         f = 0.5F;
      } else {
         f = 1.0F;
      }

      if (mc.thePlayer.moveStrafing > 0.0F) {
         yw -= 90.0F * f;
      }

      if (mc.thePlayer.moveStrafing < 0.0F) {
         yw += 90.0F * f;
      }

      yw *= 0.017453292F;
      return yw;
   }

   public static double gs() {
      return Math.sqrt(mc.thePlayer.motionX * mc.thePlayer.motionX + mc.thePlayer.motionZ * mc.thePlayer.motionZ);
   }

   public static double gbps(Entity en, int d) {
      double x = en.posX - en.prevPosX;
      double z = en.posZ - en.prevPosZ;
      double sp = Math.sqrt(x * x + z * z) * 20.0D;
      return rnd(sp, d);
   }

   public static boolean ilc() {
      if (ModuleManager.autoClicker.isEnabled()) {
         return AutoClicker.leftClick.isToggled() && Mouse.isButtonDown(0);
      } else return cl.getLeftClickCounter() > 1 && System.currentTimeMillis() - cl.leftClickTimer < 300L;
   }

   public static int gc(long speed, long... delay) {
      long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
      return Color.getHSBColor((float)(time % (15000L / speed)) / (15000.0F / (float)speed), 1.0F, 1.0F).getRGB();
   }

   public static double rnd(double n, int d) {
      if (d == 0) {
         return (double)Math.round(n);
      } else {
         double p = Math.pow(10.0D, (double)d);
         return (double)Math.round(n * p) / p;
      }
   }

   public static String str(String s) {
      char[] n = StringUtils.stripControlCodes(s).toCharArray();
      StringBuilder v = new StringBuilder();

      for (char c : n) {
         if (c < 127 && c > 20) {
            v.append(c);
         }
      }

      return v.toString();
   }

   public static List<String> gsl() {
      List<String> lines = new ArrayList();
      if (mc.theWorld == null) {
         return lines;
      } else {
         Scoreboard scoreboard = mc.theWorld.getScoreboard();
         if (scoreboard == null) {
            return lines;
         } else {
            ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
            if (objective == null) {
               return lines;
            } else {
               Collection<Score> scores = scoreboard.getSortedScores(objective);
               List<Score> list = new ArrayList();
               Iterator var5 = scores.iterator();

               Score score;
               while(var5.hasNext()) {
                  score = (Score)var5.next();
                  if (score != null && score.getPlayerName() != null && !score.getPlayerName().startsWith("#")) {
                     list.add(score);
                  }
               }

               if (list.size() > 15) {
                  scores = Lists.newArrayList(Iterables.skip(list, scores.size() - 15));
               } else {
                  scores = list;
               }

               var5 = scores.iterator();

               while(var5.hasNext()) {
                  score = (Score)var5.next();
                  ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
                  lines.add(ScorePlayerTeam.formatPlayerName(team, score.getPlayerName()));
               }

               return lines;
            }
         }
      }
   }

   public static void rsa() {
      EntityPlayerSP p = mc.thePlayer;
      int armSwingEnd = p.isPotionActive(Potion.digSpeed) ? 6 - (1 + p.getActivePotionEffect(Potion.digSpeed).getAmplifier()) : (p.isPotionActive(Potion.digSlowdown) ? 6 + (1 + p.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) * 2 : 6);
      if (!p.isSwingInProgress || p.swingProgressInt >= armSwingEnd / 2 || p.swingProgressInt < 0) {
         p.swingProgressInt = -1;
         p.isSwingInProgress = true;
      }

   }

   public static String uf(String s) {
      return s.substring(0, 1).toUpperCase() + s.substring(1);
   }

   public static boolean playerOverAir() {
      double x = mc.thePlayer.posX;
      double y = mc.thePlayer.posY - 1.0D;
      double z = mc.thePlayer.posZ;
      BlockPos p = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
      return mc.theWorld.isAirBlock(p);
   }

   public static boolean wpn() {
      if (mc.thePlayer.getCurrentEquippedItem() == null) {
         return false;
      } else {
         Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
         return item instanceof ItemSword || item instanceof ItemAxe;
      }
   }

   public static enum ClickMode {
      RAVEN,
      LEGIT
   }
}
