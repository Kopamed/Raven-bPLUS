//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module;

import keystrokesmod.ay;
import keystrokesmod.module.modules.*;
import keystrokesmod.module.modules.client.CommandLine;
import keystrokesmod.module.modules.client.Gui;
import keystrokesmod.module.modules.client.SelfDestruct;
import keystrokesmod.module.modules.client.UpdateCheck;
import keystrokesmod.module.modules.combat.*;
import keystrokesmod.module.modules.debug.Click;
import keystrokesmod.module.modules.debug.MouseClick;
import keystrokesmod.module.modules.fun.Fun;
import keystrokesmod.module.modules.hotkey.*;
import keystrokesmod.module.modules.minigames.BridgeInfo;
import keystrokesmod.module.modules.minigames.DuelsStats;
import keystrokesmod.module.modules.minigames.MurderMystery;
import keystrokesmod.module.modules.minigames.SumoFences;
import keystrokesmod.module.modules.movement.*;
import keystrokesmod.module.modules.other.*;
import keystrokesmod.module.modules.player.*;
import keystrokesmod.module.modules.render.*;
import keystrokesmod.module.modules.world.AntiBot;
import keystrokesmod.module.modules.world.ChatLogger;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {
   public static List<Module> modsList = new ArrayList<>();
   public static List<Module> enModsList = new ArrayList<>();
   public static Module nameHider;
   public static Module fastPlace;
   public static Module antiShuffle;
   public static Module antiBot;
   public static Module noSlow;
   public static Module autoClicker;
   public static Module hitBox;
   public static Module reach;
   public static Module hud;
   public static Module timer;
   public static Module fly;
   public static Module noFall;
   public static Module stringEncrypt;
   public static Module playerESP;
   public static Module safeWalk;
   public static Module keepSprint;

   public void r3g1st3r() {
      this.addModule(autoClicker = new AutoClicker());
      this.addModule(new AimAssist());
      this.addModule(new BurstClicker());
      this.addModule(new ClickAssist());
      this.addModule(new DelayRemover());
      this.addModule(hitBox = new HitBox());
      this.addModule(reach = new Reach());
      this.addModule(new RodAimbot());
      this.addModule(new Velocity());
      this.addModule(new BHop());
      this.addModule(new Boost());
      this.addModule(fly = new Fly());
      this.addModule(new InvMove());
      this.addModule(keepSprint = new KeepSprint());
      this.addModule(noSlow = new NoSlow());
      this.addModule(new Speed());
      this.addModule(new Sprint());
      this.addModule(new StopMotion());
      this.addModule(timer = new Timer());
      this.addModule(new VClip());
      this.addModule(new AutoJump());
      this.addModule(new AutoPlace());
      this.addModule(new BedAura());
      this.addModule(new FallSpeed());
      this.addModule(fastPlace = new FastPlace());
      this.addModule(new Freecam());
      this.addModule(noFall = new NoFall());
      this.addModule(safeWalk = new SafeWalk());
      this.addModule(antiBot = new AntiBot());
      this.addModule(antiShuffle = new AntiShuffle());
      this.addModule(new Chams());
      this.addModule(new ChestESP());
      this.addModule(new Nametags());
      this.addModule(playerESP = new PlayerESP());
      this.addModule(new Tracers());
      this.addModule(hud = new HUD());
      this.addModule(new Xray());
      this.addModule(new BridgeInfo());
      this.addModule(new DuelsStats());
      this.addModule(new MurderMystery());
      this.addModule(new SumoFences());
      this.addModule(new Fun.ExtraBobbing());
      this.addModule(new Fun.FlameTrail());
      this.addModule(new Fun.SlyPort());
      this.addModule(new Fun.Spin());
      this.addModule(new FakeChat());
      this.addModule(nameHider = new NameHider());
      this.addModule(stringEncrypt = new StringEncrypt());
      this.addModule(new WaterBucket());
      //this.addModule(new AutoConfig());
      this.addModule(new CommandLine());
      this.addModule(new Gui());
      this.addModule(new SelfDestruct());
      this.addModule(new ChatLogger());
      this.addModule(new BridgeAssist());
      this.addModule(new Fullbright());
      this.addModule(new UpdateCheck());
      this.addModule(new AutoHeader());
      this.addModule(new Click());
      this.addModule(new MiddleClick());
      this.addModule(new MouseClick());
      this.addModule(new AutoTool());
      this.addModule(new Blocks());
      this.addModule(new Ladders());
      this.addModule(new Weapon());
      this.addModule(new Pearl());
      this.addModule(new Armour());
      this.addModule(new Healing());
      this.addModule(new Trajectories());
      this.addModule(new WTap());
      this.addModule(new BlockHit());
      this.addModule(new STap());
      //this.addModule(new TargetHUD());
      this.defEn();
   }

   private void defEn() {
      antiBot.enable();
   }

   public Module getModuleByName(String name) {
      for (Module module : modsList) {
         if (module.getName().equalsIgnoreCase(name))
            return module;
      }
      return null;
   }

   private void addModule(Module m) {
      modsList.add(m);
   }

   public List<Module> listofmods() {
      return this.modsList;
   }

   public List<Module> inCateg(Module.category categ) {
      ArrayList<Module> categML = new ArrayList<>();

      for (Module mod : this.listofmods()) {
         if (mod.moduleCategory().equals(categ)) {
            categML.add(mod);
         }
      }

      return categML;
   }

   public static void sort() {
      if (HUD.alphabeticalSort.isToggled()) {
         modsList.sort(Comparator.comparing(Module::getName));
      } else {
         modsList.sort((o1, o2) -> ay.mc.fontRendererObj.getStringWidth(o2.getName()) - ay.mc.fontRendererObj.getStringWidth(o1.getName()));
      }

   }

   public static void sortLongShort() {
      modsList.sort((o2, o1) -> ay.mc.fontRendererObj.getStringWidth(o2.getName()) - ay.mc.fontRendererObj.getStringWidth(o1.getName()));
   }

   public static void sortShortLong() {
      modsList.sort((o1, o2) -> ay.mc.fontRendererObj.getStringWidth(o2.getName()) - ay.mc.fontRendererObj.getStringWidth(o1.getName()));
   }

   public static int getLongestActiveModule(FontRenderer fr) {
      int length = 0;
      for(Module mod : modsList) {
         if(mod.isEnabled()){
            if(fr.getStringWidth(mod.getName()) > length){
               length = fr.getStringWidth(mod.getName());
            }
         }
      }
      return length;
   }

   public static int getBoxHeight(FontRenderer fr, int margin) {
      int length = 0;
      for(Module mod : modsList) {
         if(mod.isEnabled()){
            length += fr.FONT_HEIGHT + margin;
         }
      }
      return length;
   }
}
