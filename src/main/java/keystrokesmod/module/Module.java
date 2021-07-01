//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package keystrokesmod.module;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.input.Keyboard;

public class Module {
   protected ArrayList<ModuleSettingsList> settings;
   private String moduleName;
   private Module.category moduleCategory;
   private boolean enabled;
   private int keycode;
   protected static Minecraft mc;
   private boolean isToggled = false;

   public Module(String moduleName, Module.category moduleCategory, int keycode) {
      this.moduleName = moduleName;
      this.moduleCategory = moduleCategory;
      this.keycode = keycode;
      this.enabled = false;
      mc = Minecraft.getMinecraft();
      this.settings = new ArrayList();
   }

   public static Module getModule(Class<? extends Module> a) {
      Iterator var1 = ModuleManager.modsList.iterator();

      Module module;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         module = (Module)var1.next();
      } while(module.getClass() != a);

      return module;
   }

   public Module(String name, Module.category moduleCategory) {
      this.moduleName = name;
      this.moduleCategory = moduleCategory;
      this.keycode = 0;
      this.enabled = false;
   }

   public void keybind() {
      if (this.keycode != 0) {
         if (!this.isToggled && Keyboard.isKeyDown(this.keycode)) {
            this.toggle();
            this.isToggled = true;
         } else if (!Keyboard.isKeyDown(this.keycode)) {
            this.isToggled = false;
         }

      }
   }

   public void enable() {
      this.setEnabled(true);
      ModuleManager.enModsList.add(this);
      if (ModuleManager.hud.isEnabled()) {
         ModuleManager.sort();
      }

      MinecraftForge.EVENT_BUS.register(this);
      FMLCommonHandler.instance().bus().register(this);
      this.onEnable();
   }

   public void disable() {
      this.setEnabled(false);
      ModuleManager.enModsList.remove(this);
      MinecraftForge.EVENT_BUS.unregister(this);
      FMLCommonHandler.instance().bus().unregister(this);
      this.onDisable();
   }

   public void setEnabled(boolean enabled) {
      this.enabled = enabled;
   }

   public String getName() {
      return this.moduleName;
   }

   public ArrayList<ModuleSettingsList> getSettings() {
      return this.settings;
   }

   public void registerSetting(ModuleSettingsList Setting) {
      this.settings.add(Setting);
   }

   public Module.category moduleCategory() {
      return this.moduleCategory;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void toggle() {
      if (this.isEnabled()) {
         this.disable();
      } else {
         this.enable();
      }

   }

   public void update() {
   }

   public void guiUpdate() {
   }

   public void guiButtonToggled(ModuleSetting b) {
   }

   public int getKeycode() {
      return this.keycode;
   }

   public void setbind(int keybind) {
      this.keycode = keybind;
   }

   public static enum category {
      combat,
      movement,
      player,
      world,
      render,
      minigames,
      fun,
      other,
      client;
   }
}
