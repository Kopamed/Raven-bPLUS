package keystrokesmod.module;

import keystrokesmod.module.modules.HUD;
import keystrokesmod.module.modules.client.*;
import keystrokesmod.module.modules.combat.*;
import keystrokesmod.module.modules.debug.Click;
import keystrokesmod.module.modules.debug.MessageInfo;
import keystrokesmod.module.modules.debug.MouseClick;
import keystrokesmod.module.modules.fun.*;
import keystrokesmod.module.modules.hotkey.*;
import keystrokesmod.module.modules.minigames.*;
import keystrokesmod.module.modules.movement.*;
import keystrokesmod.module.modules.other.*;
import keystrokesmod.module.modules.player.*;
import keystrokesmod.module.modules.render.*;
import keystrokesmod.module.modules.world.AntiBot;
import keystrokesmod.module.modules.world.ChatLogger;
import keystrokesmod.utils.Utils;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModuleManager {
   public static List<Module> modsList = new ArrayList<>();
   public static List<Module> enModsList = new ArrayList<>();

   public static boolean initialized = false;

   public synchronized void r3g1st3r() {
      if (initialized) return;

      addModule(new AutoClicker());
      addModule(new AimAssist());
      addModule(new BurstClicker());
      addModule(new ClickAssist());
      addModule(new DelayRemover());
      addModule(new HitBox());
      addModule(new Reach());
      addModule(new RodAimbot());
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
      addModule(new ExtraBobbing());
      addModule(new Twerk());
      addModule(new ParticleTrail());
      addModule(new SlyPort());
      addModule(new Spin());
      addModule(new FovLSD());
      addModule(new ClientNameSpoof());
      addModule(new FakeChat());
      addModule(new NameHider());
      addModule(new StringEncrypt());
      addModule(new WaterBucket());
      //addModule(new AutoConfig());
      addModule(new CommandLine());
      addModule(new GuiModule());
      addModule(new SelfDestruct());
      addModule(new ChatLogger());
      addModule(new BridgeAssist());
      addModule(new Fullbright());
      addModule(new UpdateCheck());
      addModule(new AutoHeader());
      addModule(new Click());
      addModule(new MiddleClick());
      addModule(new MouseClick());
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
      addModule(new MessageInfo());
      //addModule(new TargetHUD());
      addModule(new AutoWeapon());
      addModule(new BedwarsOverlay());

      addModule(new DiscordRPCModule());
      addModule(new ShiftTap());
      addModule(new FPSSpoofer());
      addModule(new CustomFOV());

      addModule(new ExplicitB9NameTags());
      addModule(new AutoBlock());

      // why ?
      getModuleByClazz(AntiBot.class).enable();

      initialized = true;
   }
   
   private static void addModule(Module m) {
      modsList.add(m);
   } 

   // prefer using getModuleByClazz();
   public static Module getModuleByName(String name) {
      for (Module module : modsList) {
         if (module.getName().equalsIgnoreCase(name))
            return module;
      }
      return null;
   }

   public static Module getModuleByClazz(Class<? extends Module> c) {
      for (Module module : modsList) {
         if (module.getClass().equals(c))
            return module;
      }
      return null;
   }


   public static List<Module> listofmods() {
      return modsList;
   }

   public static List<Module> getModulesInCategory(Module.category categ) {
      ArrayList<Module> modulesOfCat = new ArrayList<>();

      for (Module mod : listofmods()) {
         if (mod.moduleCategory().equals(categ)) {
            modulesOfCat.add(mod);
         }
      }

      return modulesOfCat;
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
      modsList.sort(Comparator.comparingInt(o2 -> Utils.mc.fontRendererObj.getStringWidth(o2.getName())));
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
