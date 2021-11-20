//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.8.9"!

package me.kopamed.raven.bplus.client.feature.module;

import java.util.ArrayList;
import java.util.Iterator;

import me.kopamed.raven.bplus.client.feature.setting.Setting;
import me.kopamed.raven.bplus.client.visual.clickgui.plus.PlusGui;
import me.kopamed.raven.bplus.helper.manager.ModuleManager;
import me.kopamed.raven.bplus.client.feature.module.modules.other.DiscordRPCModule;
import me.kopamed.raven.bplus.client.feature.setting.settings.Tick;
import me.kopamed.raven.bplus.helper.utils.NotificationRenderer;
import me.kopamed.raven.bplus.helper.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import org.lwjgl.input.Keyboard;

public abstract class Module {
   protected ArrayList<Setting> settings;
   private final String moduleName;
   private final ModuleCategory moduleCategory;
   private boolean enabled;
   private int keycode;
   protected static Minecraft mc;
   private boolean isToggled = false;
   private BindMode bindMode = BindMode.TOGGLE;

   public Module(String name, ModuleCategory moduleCategory) {
      this.moduleName = name;
      this.moduleCategory = moduleCategory;
      this.keycode = -1;
      this.enabled = false;
      this.settings = new ArrayList<>();
   }

   public Module(String moduleName, ModuleCategory moduleCategory, int keycode) {
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
      if (this.keycode != -1) {
         if(Keyboard.isKeyDown(this.keycode)){
            if(!isToggled){
               if (bindMode == BindMode.HOLD){
                  enable();
               } else if( bindMode == BindMode.TOGGLE){
                  toggle();
               }
               this.isToggled = true;
            }
         } else{
            if(isToggled){
               if (bindMode == BindMode.HOLD){
                  disable();
               }
               this.isToggled = false;
            }
         }
      }
   }

   public void enable() {
      boolean oldState = this.enabled;
      this.setToggled(true);
      if (oldState != this.enabled) {
         NotificationRenderer.moduleStateChanged(this); //todo
      }
      ModuleManager.enModsList.add(this);
      if (ModuleManager.hud.isEnabled()) {
         ModuleManager.sort();
      }

      MinecraftForge.EVENT_BUS.register(this);
      FMLCommonHandler.instance().bus().register(this);
      this.onEnable();

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

      DiscordRPCModule discordRPCModule = (DiscordRPCModule) getModule(DiscordRPCModule.class);
      if (discordRPCModule != null && discordRPCModule.isEnabled()) {
         DiscordRPCModule.rpc.updateRavenRPC();
      }
   }

   public void setToggled(boolean enabled) {
      this.enabled = enabled;
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

   public ModuleCategory moduleCategory() {
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

   public void guiButtonToggled(Tick b) {
   }

   public int getKeycode() {
      return this.keycode;
   }

   public void setbind(int keybind) {
      this.keycode = keybind;
   }

   public BindMode getBindMode() {
      return bindMode;
   }

   public void setBindMode(BindMode bindMode) {
      this.bindMode = bindMode;
   }

   public boolean hasKeybind() {
      return keycode != 1;
   }

   public boolean canToggle(){
      return Utils.Player.isPlayerInGame() && !(mc.currentScreen instanceof PlusGui);
   }
}
