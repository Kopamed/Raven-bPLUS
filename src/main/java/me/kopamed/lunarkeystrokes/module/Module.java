//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.lunarkeystrokes.module;

import java.util.ArrayList;
import java.util.Iterator;

import me.kopamed.lunarkeystrokes.main.NotAName;
import me.kopamed.lunarkeystrokes.utils.NotificationRenderer;
import me.kopamed.lunarkeystrokes.main.Ravenbplus;
import me.kopamed.lunarkeystrokes.module.modules.other.DiscordRPCModule;
import me.kopamed.lunarkeystrokes.module.setting.Setting;
import me.kopamed.lunarkeystrokes.module.setting.settings.Tick;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.input.Keyboard;

public class Module {
   protected ArrayList<Setting> settings;
   private final String moduleName;
   private final Module.category moduleCategory;
   private boolean enabled;
   private int keycode;
   protected static Minecraft mc;
   private boolean isToggled = false;

   public Module(String name, Module.category moduleCategory) {
      this.moduleName = name;
      this.moduleCategory = moduleCategory;
      this.keycode = 0;
      this.enabled = false;
      this.settings = new ArrayList<>();
   }

   public Module(String moduleName, Module.category moduleCategory, int keycode) {
      this.moduleName = moduleName;
      this.moduleCategory = moduleCategory;
      this.keycode = keycode;
      this.enabled = false;
      mc = Minecraft.getMinecraft();
      this.settings = new ArrayList<>();
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
      boolean oldState = this.enabled;
      this.setToggled(true);
      if (oldState != this.enabled) {
         NotificationRenderer.moduleStateChanged(this);
      }
      ModuleManager.enModsList.add(this);
      if (ModuleManager.hud.isEnabled()) {
         ModuleManager.sort();
      }

      MinecraftForge.EVENT_BUS.register(this);
      FMLCommonHandler.instance().bus().register(this);
      this.onEnable();

      if(Ravenbplus.configManager != null){
         Ravenbplus.configManager.save();
      }

      DiscordRPCModule discordRPCModule = (DiscordRPCModule) getModule(DiscordRPCModule.class);
      if (discordRPCModule != null && discordRPCModule.isEnabled()) {
         DiscordRPCModule.rpc.updateRavenRPC();
      }
   }

   public void disable() {
      boolean oldState = this.enabled;
      this.setToggled(false);
      if (oldState != this.enabled) {
         NotificationRenderer.moduleStateChanged(this);
      }
      ModuleManager.enModsList.remove(this);
      MinecraftForge.EVENT_BUS.unregister(this);
      FMLCommonHandler.instance().bus().unregister(this);
      this.onDisable();
      if(Ravenbplus.configManager != null){
         Ravenbplus.configManager.save();
      }

      DiscordRPCModule discordRPCModule = (DiscordRPCModule) getModule(DiscordRPCModule.class);
      if (discordRPCModule != null && discordRPCModule.isEnabled()) {
         DiscordRPCModule.rpc.updateRavenRPC();
      }
   }

   public void setToggled(boolean enabled) {
      this.enabled = enabled;
      if(Ravenbplus.configManager != null){
         Ravenbplus.configManager.save();
      }
   }

   public String getName() {
      return this.moduleName;
   }

   public ArrayList<Setting> getSettings() {
      return this.settings;
   }

   public Setting getSettingByName(String name) {
      for (Setting setting : this.settings) {
         if (setting.getName().equalsIgnoreCase(name))
            return setting;
      }
      return null;
   }

   public void registerSetting(Setting Setting) {
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
      if(Ravenbplus.configManager != null){
         Ravenbplus.configManager.save();
      }
   }

   public void update() {
   }

   public void guiUpdate() {
   }

   public void guiButtonToggled(Tick b) {
   }

   public int getKeycode() {
      return this.keycode;
   }

   public void setbind(int keybind) {
      this.keycode = keybind;
      if(Ravenbplus.configManager != null){
         Ravenbplus.configManager.save();
      }
   }

   public enum category {
      combat,
      movement,
      player,
      world,
      render,
      minigames,
      fun,
      other,
      client,
      hotkey,
      debug
   }
}
