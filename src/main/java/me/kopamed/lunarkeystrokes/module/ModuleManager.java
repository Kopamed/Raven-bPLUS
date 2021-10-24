//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module;

import me.kopamed.lunarkeystrokes.module.modules.client.*;
import me.kopamed.lunarkeystrokes.module.modules.fun.*;
import me.kopamed.lunarkeystrokes.module.modules.world.PingSpoof;
import me.kopamed.lunarkeystrokes.utils.Utils;
import me.kopamed.lunarkeystrokes.module.modules.*;
import me.kopamed.lunarkeystrokes.module.modules.combat.*;
import me.kopamed.lunarkeystrokes.module.modules.debug.Click;
import me.kopamed.lunarkeystrokes.module.modules.debug.MessageInfo;
import me.kopamed.lunarkeystrokes.module.modules.debug.MouseClick;
import me.kopamed.lunarkeystrokes.module.modules.hotkey.*;
import me.kopamed.lunarkeystrokes.module.modules.minigames.*;
import me.kopamed.lunarkeystrokes.module.modules.movement.*;
import me.kopamed.lunarkeystrokes.module.modules.other.*;
import me.kopamed.lunarkeystrokes.module.modules.player.*;
import me.kopamed.lunarkeystrokes.module.modules.render.*;
import me.kopamed.lunarkeystrokes.module.modules.world.AntiBot;
import me.kopamed.lunarkeystrokes.module.modules.world.ChatLogger;
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
   public static int arrayLength = 0;

   public static boolean initialized = false;

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
      this.addModule(new ExtraBobbing());
      this.addModule(new Twerk());
      this.addModule(new ParticleTrail());
      this.addModule(new SlyPort());
      this.addModule(new Spin());
      this.addModule(new FovLSD());
      this.addModule(new ClientNameSpoof());
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
      this.addModule(new MessageInfo());
      //this.addModule(new TargetHUD());
      this.addModule(new AutoWeapon());
      this.addModule(new BedwarsOverlay());
      arrayLength = this.getModules().size();
      this.addModule(new DiscordRPCModule());
      this.addModule(new ShiftTap());
      this.addModule(new FPSSpoofer());
      this.addModule(new CustomFOV());

      this.addModule(new ExplicitB9NameTags());
      this.addModule(new AutoBlock());
      this.addModule(new RandomPack());
      this.addModule(new SuperAutoClicker());
      this.addModule(new PingSpoof());
      this.addModule(new GhostBlocks());
      this.defEn();

      initialized = true;
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

   public List<Module> getModules() {
      return this.modsList;
   }

   public List<Module> inCateg(Module.category categ) {
      ArrayList<Module> categML = new ArrayList<>();

      for (Module mod : this.getModules()) {
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
         modsList.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
      }

   }

   public static int modListSize() {
      return modsList.size();
   }

   public static void sortLongShort() {
      modsList.sort((o2, o1) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
   }

   public static void sortShortLong() {
      modsList.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
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
