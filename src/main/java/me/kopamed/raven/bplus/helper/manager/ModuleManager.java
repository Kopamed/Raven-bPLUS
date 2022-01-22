package me.kopamed.raven.bplus.helper.manager;

import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.module.modules.blatant.*;
import me.kopamed.raven.bplus.client.feature.module.modules.client.*;
import me.kopamed.raven.bplus.client.feature.module.modules.combat.*;
import me.kopamed.raven.bplus.client.feature.module.modules.hotkey.*;
import me.kopamed.raven.bplus.client.feature.module.modules.minigames.*;
import me.kopamed.raven.bplus.client.feature.module.modules.other.*;
import me.kopamed.raven.bplus.client.feature.module.modules.world.PingSpoof;
import me.kopamed.raven.bplus.helper.utils.Utils;
import me.kopamed.raven.bplus.client.feature.module.modules.world.AntiBot;
import me.kopamed.raven.bplus.client.feature.module.modules.world.ChatLogger;
import me.kopamed.raven.bplus.client.feature.module.modules.HUD;
import me.kopamed.raven.bplus.client.feature.module.modules.fun.*;
import me.kopamed.raven.bplus.client.feature.module.modules.movement.*;
import me.kopamed.raven.bplus.client.feature.module.modules.player.*;
import me.kopamed.raven.bplus.client.feature.module.modules.render.*;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
   public static ArrayList<Module> modsList = new ArrayList<>();
   public static boolean isInitialized = false;
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
   public static Module gui;
   public static Module propHunt;
   public static Module murderMystery;

   public ModuleManager() {
      this.initMod(autoClicker = new AutoClicker());
      this.initMod(new AimAssist());
      this.initMod(new BurstClicker());
      this.initMod(new ClickAssist());
      this.initMod(new DelayRemover());
      this.initMod(hitBox = new HitBox());
      this.initMod(reach = new Reach());
      this.initMod(new RodAimbot());
      this.initMod(new Velocity());
      this.initMod(new BHop());
      this.initMod(new Boost());
      this.initMod(fly = new Fly());
      this.initMod(new InvMove());
      this.initMod(keepSprint = new KeepSprint());
      this.initMod(noSlow = new NoSlow());
      this.initMod(new Speed());
      this.initMod(new Sprint());
      this.initMod(new StopMotion());
      this.initMod(timer = new Timer());
      this.initMod(new VClip());
      this.initMod(new AutoJump());
      this.initMod(new AutoPlace());
      this.initMod(new BedAura());
      this.initMod(new FallSpeed());
      this.initMod(fastPlace = new FastPlace());
      this.initMod(new Freecam());
      this.initMod(noFall = new NoFall());
      this.initMod(safeWalk = new SafeWalk());
      this.initMod(antiBot = new AntiBot());
      this.initMod(antiShuffle = new AntiShuffle());
      this.initMod(new Chams());
      this.initMod(new ChestESP());
      this.initMod(new Nametags());
      this.initMod(playerESP = new PlayerESP());
      this.initMod(new Tracers());
      this.initMod(hud = new HUD());
      this.initMod(new Xray());
      this.initMod(new BridgeInfo());
      this.initMod(new DuelsStats());
      this.initMod(murderMystery = new MurderMystery());
      this.initMod(new SumoFences());
      this.initMod(new ExtraBobbing());
      this.initMod(new Twerk());
      this.initMod(new ParticleTrail());
      this.initMod(new SlyPort());
      this.initMod(new Spin());
      this.initMod(new FovLSD());
      this.initMod(new ClientNameSpoof());
      this.initMod(new FakeChat());
      this.initMod(nameHider = new NameHider());
      this.initMod(stringEncrypt = new StringEncrypt());
      this.initMod(new WaterBucket());
      //this.addModule(new AutoConfig());
      this.initMod(new CommandLine());
      this.initMod(gui = new Gui());
      this.initMod(new SelfDestruct());
      this.initMod(new ChatLogger());
      this.initMod(new BridgeAssist());
      this.initMod(new Fullbright());
      this.initMod(new UpdateCheck());
      this.initMod(new AutoHeader());
      this.initMod(new MiddleClick());
      this.initMod(new AutoTool());
      this.initMod(new Blocks());
      this.initMod(new Ladders());
      this.initMod(new Weapon());
      this.initMod(new Pearl());
      this.initMod(new Armour());
      this.initMod(new Healing());
      this.initMod(new Trajectories());
      this.initMod(new WTap());
      this.initMod(new BlockHit());
      this.initMod(new STap());
      //this.addModule(new TargetHUD());
      this.initMod(new AutoWeapon());
      this.initMod(new BedwarsOverlay());
      this.initMod(new DiscordRPCModule());
      this.initMod(new ShiftTap());
      this.initMod(new FPSSpoofer());
      this.initMod(new CustomFOV());
      this.initMod(new ExplicitB9NameTags());
      this.initMod(new AutoBlock());
      this.initMod(new RandomPack());
      this.initMod(new SuperAutoClicker());
      this.initMod(new PingSpoof());
      this.initMod(new GhostBlocks());
      this.initMod(new LeftClicker());
      //this.initMod(propHunt = new PropHunt());
      //this.initMod(new TPAura());
      //this.addModule(new KillAura());

      isInitialized = true;
   }

   public Module getModuleByName(String name) {
      for (Module module : modsList) {
         if (module.getName().equalsIgnoreCase(name))
            return module;
      }
      return null;
   }

   public <M extends Module> M getModuleByClass(M m){
      for(Module module : modsList){
         if(m == module)
            return (M) module;
      }
      return null;
   }

   private void initMod(Module m) {
      modsList.add(m);
   }

   private void initMod(Module m, boolean e) {
      m.setToggled(e);
      modsList.add(m);
   }

   public List<Module> getModules() {
      return modsList;
   }

   public List<Module> getModulesInCategory(ModuleCategory categ) {
      ArrayList<Module> categML = new ArrayList<>();

      for (Module mod : this.getModules()) {
         if (mod.moduleCategory().equals(categ)) {
            categML.add(mod);
         }
      }

      return categML;
   }

   public static void sort() {
      // todo
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
         if(mod.isToggled()){
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
         if(mod.isToggled()){
            length += fr.FONT_HEIGHT + margin;
         }
      }
      return length;
   }

    public ArrayList<Module> getEnabledModules() {
       ArrayList<Module> lilAsianGirl = new ArrayList<>();
       for(Module mod : modsList){
          if(mod.isToggled())
             lilAsianGirl.add(mod);
       }
       return lilAsianGirl;
    }
}
