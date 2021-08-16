//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;

import keystrokesmod.module.ModuleManager;
import keystrokesmod.module.ModuleSettingSlider;
import keystrokesmod.module.modules.combat.AutoClicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
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

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

public class ay {
   private static final Random rand = new Random();
   public static final Minecraft mc = Minecraft.getMinecraft();
   public static final String md = "Mode: ";
   private static Field timerField = null;
   private static Field mouseButton = null;
   private static Field mouseButtonState = null;
   private static Field mouseButtons = null;

   public static void su() {
      try {
         timerField = Minecraft.class.getDeclaredField("field_71428_T");
      } catch (Exception var4) {
         try {
            timerField = Minecraft.class.getDeclaredField("timer");
         } catch (Exception ignored) {}
      }

      if (timerField != null) {
         timerField.setAccessible(true);
      }

      try {
         mouseButton = MouseEvent.class.getDeclaredField("button");
         mouseButtonState = MouseEvent.class.getDeclaredField("buttonstate");
         mouseButtons = Mouse.class.getDeclaredField("buttons");
      } catch (Exception var2) {
      }

   }

   public static void setMouseButtonState(int mouseButton, boolean held) {
      if (ay.mouseButton != null && mouseButtonState != null && mouseButtons != null) {
         MouseEvent m = new MouseEvent();

         try {
            ay.mouseButton.setAccessible(true);
            ay.mouseButton.set(m, mouseButton);
            mouseButtonState.setAccessible(true);
            mouseButtonState.set(m, held);
            MinecraftForge.EVENT_BUS.post(m);
            mouseButtons.setAccessible(true);
            ByteBuffer bf = (ByteBuffer) mouseButtons.get(null);
            mouseButtons.setAccessible(false);
            bf.put(mouseButton, (byte)(held ? 1 : 0));
         } catch (IllegalAccessException var4) {
         }

      }
   }

   public static void sendMessageToSelf(String txt) {
      if (isPlayerInGame()) {
         String m = r("&7[&dR&7]&r " + txt);
         mc.thePlayer.addChatMessage(new ChatComponentText(m));
      }
   }

   public static String r(String txt) {
      return txt.replaceAll("&", "§");
   }

   public static void correctSliders(ModuleSettingSlider c, ModuleSettingSlider d) {
      if (c.getInput() > d.getInput()) {
         double p = c.getInput();
         c.setValue(d.getInput());
         d.setValue(p);
      }

   }

   public static double ranModuleVal(ModuleSettingSlider a, ModuleSettingSlider b, Random r) {
      return a.getInput() == b.getInput() ? a.getInput() : a.getInput() + r.nextDouble() * (b.getInput() - a.getInput());
   }

   public static boolean isPlayerInGame() {
      return mc.thePlayer != null && mc.theWorld != null;
   }

   public static boolean isHyp() {
      if(!ay.isPlayerInGame()) return false;
      try {
         return !mc.isSingleplayer() && mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
      } catch (Exception welpBruh) {
         welpBruh.printStackTrace();
         return false;
      }
   }

   public static int f() {
      return mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID()).getResponseTime();
   }

   public static net.minecraft.util.Timer getTimer() {
      try {
         return (net.minecraft.util.Timer) timerField.get(mc);
      } catch (IndexOutOfBoundsException | IllegalAccessException var1) {
         return null;
      }
   }

   public static void resetTimer() {
      try {
         getTimer().timerSpeed = 1.0F;
      } catch (NullPointerException ignored) {}

   }

   public static Random rand() {
      return rand;
   }

   public static boolean isMoving() {
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
         double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
         float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
         float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
         return new float[]{mc.thePlayer.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw), mc.thePlayer.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch)};
      }
   }

   public static double fovFromEntity(Entity en) {
      return ((double)(mc.thePlayer.rotationYaw - fovToEntity(en)) % 360.0D + 540.0D) % 360.0D - 180.0D;
   }

   public static float fovToEntity(Entity ent) {
      double x = ent.posX - mc.thePlayer.posX;
      double z = ent.posZ - mc.thePlayer.posZ;
      double yaw = Math.atan2(x, z) * 57.2957795D;
      return (float)(yaw * -1.0D);
   }

   public static boolean fov(Entity entity, float fov) {
      fov = (float)((double)fov * 0.5D);
      double v = ((double)(mc.thePlayer.rotationYaw - fovToEntity(entity)) % 360.0D + 540.0D) % 360.0D - 180.0D;
      return v > 0.0D && v < (double)fov || (double)(-fov) < v && v < 0.0D;
   }

   public static void ss(double s, boolean m) {
      if (!m || isMoving()) {
         mc.thePlayer.motionX = -Math.sin(gd()) * s;
         mc.thePlayer.motionZ = Math.cos(gd()) * s;
      }
   }

   public static void ss2(double s) {
      double forward = mc.thePlayer.movementInput.moveForward;
      double strafe = mc.thePlayer.movementInput.moveStrafe;
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

         double rad = Math.toRadians(yaw + 90.0F);
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

   public static double getPlayerBPS(Entity en, int d) {
      double x = en.posX - en.prevPosX;
      double z = en.posZ - en.prevPosZ;
      double sp = Math.sqrt(x * x + z * z) * 20.0D;
      return round(sp, d);
   }

   public static boolean autoClickerClicking() {
      if (ModuleManager.autoClicker.isEnabled()) {
         return AutoClicker.leftClick.isToggled() && Mouse.isButtonDown(0);
      } //else return mouseManager.getLeftClickCounter() > 1 && System.currentTimeMillis() - mouseManager.leftClickTimer < 300L;
      return false;
   }

   public static int rainbowDraw(long speed, long... delay) {
      long time = System.currentTimeMillis() + (delay.length > 0 ? delay[0] : 0L);
      return Color.getHSBColor((float)(time % (15000L / speed)) / (15000.0F / (float)speed), 1.0F, 1.0F).getRGB();
   }

   public static int astolfoColorsDraw(int yOffset, int yTotal, float speed) {
      float hue = (float) (System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) * 9);
      while (hue > speed) {
         hue -= speed;
      }
      hue /= speed;
      if (hue > 0.5) {
         hue = 0.5F - (hue - 0.5f);
      }
      hue += 0.5F;
      return Color.HSBtoRGB(hue, 0.5f, 1F);
   }
   public static int astolfoColorsDraw(int yOffset, int yTotal) {
      return astolfoColorsDraw(yOffset, yTotal, 2900F);
   }

   public static int kopamedColoursDraw(int yOffset, int yTotal){
      float speed = 6428;
      float hue;
      try {
         hue = (float)(System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) / (yOffset / yTotal));
      } catch (ArithmeticException divisionByZero) {
         hue = (float)(System.currentTimeMillis() % (int)speed) + ((yTotal - yOffset) / ((yOffset / yTotal + 1) + 1));
      }

      while (hue > speed) {
         hue -= speed;
      }
      hue /= speed;
      if (hue > 2) {
         hue = 2F - (hue - 2f);
      }
      hue += 2F;

      float current = (System.currentTimeMillis()% speed) + ((yOffset + yTotal) * 9);

      while (current > speed) {
         current -= speed;
      }
      current /= speed;
      if (current > 2) {
         current = 2F - (current - 2f);
      }
      current += 2F;

      return Color.HSBtoRGB((current / (current - yTotal)) + current, 1f, 1F);
   }

   public static double round(double n, int d) {
      if (d == 0) {
         return (double)Math.round(n);
      } else {
         double p = Math.pow(10.0D, d);
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
            if (objective != null) {
               Collection<Score> scores = scoreboard.getSortedScores(objective);
               List<Score> list = new ArrayList();
               Iterator<Score> var5 = scores.iterator();

               Score score;
               while (var5.hasNext()) {
                  score = var5.next();
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

               while (var5.hasNext()) {
                  score = var5.next();
                  ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
                  lines.add(ScorePlayerTeam.formatPlayerName(team, score.getPlayerName()));
               }

            }
            return lines;
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

   public static boolean playerUnderBlock() {
      double x = mc.thePlayer.posX;
      double y = mc.thePlayer.posY + 2.0D;
      double z = mc.thePlayer.posZ;
      BlockPos p = new BlockPos(MathHelper.floor_double(x), MathHelper.floor_double(y), MathHelper.floor_double(z));
      return mc.theWorld.isBlockFullCube(p) || mc.theWorld.isBlockNormalCube(p, false);
   }

   public static void hotkeyToSlot(int slot) {
      if(!ay.isPlayerInGame())
         return;

      mc.thePlayer.inventory.currentItem = slot;
   }

   public static int getCurrentPlayerSlot() {
      return mc.thePlayer.inventory.currentItem;
   }

   public static boolean isPlayerHoldingWeapon() {
      if (mc.thePlayer.getCurrentEquippedItem() == null) {
         return false;
      } else {
         Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
         return item instanceof ItemSword || item instanceof ItemAxe;
      }
   }

   public static boolean openWebpage(String url) {
      try {
         URL linkURL = null;
         linkURL = new URL(url);

         return openWebpage(linkURL.toURI());
      } catch (URISyntaxException | MalformedURLException e) {
         e.printStackTrace();
      }
      return false;
   }

   public static boolean openWebpage(URL url) {
      try {
         return openWebpage(url.toURI());
      } catch (URISyntaxException e) {
         e.printStackTrace();
      }
      return false;
   }

   public static boolean openWebpage(URI uri) {
      Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
      if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
         try {
            desktop.browse(uri);
            return true;
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
      return false;
   }



   public static String getDate() {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
      LocalDateTime now = LocalDateTime.now();
      return dtf.format(now);
   }

   public static boolean copyToClipboard(String content) {
      try {
         StringSelection selection = new StringSelection(content);
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         clipboard.setContents(selection, selection);
         return true;
      } catch (Exception fuck){
         fuck.printStackTrace();
         return false;
      }
   }

   public static String joinStringList(String[] wtf, String okwaht){
      if (wtf == null)
         return "";
      if(wtf.length <= 1)
         return "";

      StringBuilder finalString = new StringBuilder(wtf[0]);

      for (int i = 1; i < wtf.length; i++){
         finalString.append(okwaht).append(wtf[i]);
      }

      return finalString.toString();
   }

   public static ArrayList<Integer> playerWearingArmor() {
      ArrayList<Integer> wearingArmor = new ArrayList<Integer>();
      for(int armorPiece = 0; armorPiece < 4; armorPiece++){
         if(mc.thePlayer.getCurrentArmor(armorPiece) != null){
            if (armorPiece == 0) {
               wearingArmor.add(3);
            } else if (armorPiece == 1) {
               wearingArmor.add(2);
            } else if (armorPiece == 2) {
               wearingArmor.add(1);
            } else if (armorPiece == 3) {
               wearingArmor.add(0);
            }
         }
      }

      return wearingArmor;
   }

   public static ArrayList<String> toArrayList(String[] fakeList){
      ArrayList<String> e = new ArrayList<String>(Arrays.asList(fakeList));

      return e;
   }

   public static boolean currentScreenMinecraft() {
      return mc.currentScreen == null;
   }

   public static int getBlockAmountInCurrentStack(int currentItem) {
      if (mc.thePlayer.inventory.getStackInSlot(currentItem) == null) {
         return 0;
      } else {
         ItemStack itemStack = mc.thePlayer.inventory.getStackInSlot(currentItem);
         if(itemStack.getItem() instanceof ItemBlock) {
            return itemStack.stackSize;
         } else {
            return 0;
         }
      }
   }

   public static boolean tryingToCombo() {
      if(Mouse.isButtonDown(0) && Mouse.isButtonDown(1)) return true;
      return false;
   }

   public enum ClickEvents {
      RENDER,
      TICK
   }

   public enum BridgeMode {
      GODBRIDGE,
      MOONWALK,
      BREEZILY,
      NORMAL
   }

   public enum LookMode {
      SNAP,
      GLIDE
   }

   public enum ClickTimings {
      RAVEN,
      SKID
   }
}
