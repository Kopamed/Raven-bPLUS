package keystrokesmod.client.module;

import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.module.modules.client.*;
import keystrokesmod.client.module.modules.combat.*;
import keystrokesmod.client.module.modules.hotkey.*;
import keystrokesmod.client.module.modules.minigames.*;
import keystrokesmod.client.module.modules.movement.*;
import keystrokesmod.client.module.modules.other.*;
import keystrokesmod.client.module.modules.player.*;
import keystrokesmod.client.module.modules.render.*;
import keystrokesmod.client.module.modules.world.AntiBot;
import keystrokesmod.client.module.modules.world.ChatLogger;
import keystrokesmod.client.utils.Utils;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {
   private List<Module> modules = new ArrayList<>();

   public static boolean initialized = false;

   public ModuleManager() {
      if (initialized) return;

      addModule(new LeftClicker());
      addModule(new RightClicker());
      addModule(new AimAssist());
      addModule(new BurstClicker());
      addModule(new ClickAssist());
      addModule(new DelayRemover());
      addModule(new HitBox());
      addModule(new Reach());
      addModule(new Velocity());
      addModule(new BHop());
      addModule(new Boost());
      addModule(new Fly());
      addModule(new InvMove());
      addModule(new KeepSprint());
      addModule(new NoSlow());
      addModule(new Speed());
      addModule(new Sprint());
      addModule(new StopMotion());
      addModule(new Timer());
      addModule(new VClip());
      addModule(new AutoJump());
      addModule(new AutoPlace());
      addModule(new BedAura());
      addModule(new FallSpeed());
      addModule(new FastPlace());
      addModule(new Freecam());
      addModule(new NoFall());
      addModule(new SafeWalk());
      addModule(new AntiBot());
      addModule(new AntiShuffle());
      addModule(new Chams());
      addModule(new ChestESP());
      addModule(new Nametags());
      addModule(new PlayerESP());
      addModule(new Tracers());
      addModule(new HUD());
      addModule(new Xray());
      addModule(new BridgeInfo());
      addModule(new DuelsStats());
      addModule(new MurderMystery());
      addModule(new SumoFences());
      addModule(new SlyPort());
      addModule(new ClientNameSpoof());
      addModule(new FakeChat());
      addModule(new NameHider());
      addModule(new StringEncrypt());
      addModule(new WaterBucket());
      //addModule(new AutoConfig());
      addModule(new Terminal());
      addModule(new GuiModule());
      addModule(new SelfDestruct());
      addModule(new ChatLogger());
      addModule(new BridgeAssist());
      addModule(new Fullbright());
      addModule(new UpdateCheck());
      addModule(new AutoHeader());
      addModule(new AutoTool());
      addModule(new Blocks());
      addModule(new Ladders());
      addModule(new Weapon());
      addModule(new Pearl());
      addModule(new Armour());
      addModule(new Healing());
      addModule(new Trajectories());
      addModule(new WTap());
      addModule(new BlockHit());
      addModule(new STap());
      addModule(new AutoWeapon());
      addModule(new BedwarsOverlay());

      addModule(new ShiftTap());
      addModule(new FPSSpoofer());

      addModule(new ExplicitB9NameTags());
      addModule(new AutoBlock());
      addModule(new MiddleClick());

      // why ?
      // idk dude. you tell me why. I am pretty sure this was blowsy's work.

      initialized = true;
   }
   
   private void addModule(Module m) {
      modules.add(m);
   } 

   // prefer using getModuleByClazz();
   // ok might add in 1.0.18
   public Module getModuleByName(String name) {
      if (!initialized) return null;

      for (Module module : modules) {
         if (module.getName().equalsIgnoreCase(name))
            return module;
      }
      return null;
   }

   public Module getModuleByClazz(Class<? extends Module> c) {
      if (!initialized) return null;

      for (Module module : modules) {
         if (module.getClass().equals(c))
            return module;
      }
      return null;
   }


   public List<Module> getModules() {
      return modules;
   }

   public List<Module> getModulesInCategory(Module.ModuleCategory categ) {
      ArrayList<Module> modulesOfCat = new ArrayList<>();

      for (Module mod : modules) {
         if (mod.moduleCategory().equals(categ)) {
            modulesOfCat.add(mod);
         }
      }

      return modulesOfCat;
   }

   public void sort() {
      if (HUD.alphabeticalSort.isToggled()) {
         modules.sort(Comparator.comparing(Module::getName));
      } else {
         modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
      }

   }

   public int numberOfModules() {
      return modules.size();
   }

   public void sortLongShort() {
      modules.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())));
   }

   public void sortShortLong() {
      modules.sort((o1, o2) -> Utils.mc.fontRendererObj.getStringWidth(o2.getName()) - Utils.mc.fontRendererObj.getStringWidth(o1.getName()));
   }

   public int getLongestActiveModule(FontRenderer fr) {
      int length = 0;
      for(Module mod : modules) {
         if(mod.isEnabled()){
            if(fr.getStringWidth(mod.getName()) > length){
               length = fr.getStringWidth(mod.getName());
            }
         }
      }
      return length;
   }

   public int getBoxHeight(FontRenderer fr, int margin) {
      int length = 0;
      for(Module mod : modules) {
         if(mod.isEnabled()){
            length += fr.FONT_HEIGHT + margin;
         }
      }
      return length;
   }
}
