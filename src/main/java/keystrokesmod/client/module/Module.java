package keystrokesmod.client.module;

import com.google.gson.JsonObject;
import keystrokesmod.client.NotificationRenderer;
import keystrokesmod.client.module.modules.HUD;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Module {
   protected ArrayList<Setting> settings;
   private final String moduleName;
   private final Module.category moduleCategory;
   private boolean enabled;
   private int keycode;
   protected static Minecraft mc;
   private boolean isToggled = false;

   private final int defualtKeyCode;

   public Module(String name, Module.category moduleCategory) {
      this.moduleName = name;
      this.moduleCategory = moduleCategory;
      this.keycode = 0;
      this.enabled = false;
      this.settings = new ArrayList<>();
      this.defualtKeyCode = 0;
   }

   public Module(String moduleName, Module.category moduleCategory, int keycode) {
      this.moduleName = moduleName;
      this.moduleCategory = moduleCategory;
      this.keycode = keycode;
      this.defualtKeyCode = keycode;
      this.enabled = false;
      mc = Minecraft.getMinecraft();
      this.settings = new ArrayList<>();
   }

   public JsonObject getConfigAsJson(){
      JsonObject settings = new JsonObject();
      for(Setting setting : this.settings){
         JsonObject settingSettings = new JsonObject();

         settingSettings.addProperty("type", setting.settingType);

         switch(setting.settingType){
            case DescriptionSetting.settingType:
               DescriptionSetting descSetting = (DescriptionSetting) setting;
               settingSettings.addProperty("value", descSetting.getDesc());
               break;
            case DoubleSliderSetting.settingType:
               DoubleSliderSetting doubleSliderSetting = (DoubleSliderSetting) setting;
               settingSettings.addProperty("valueMin", doubleSliderSetting.getInputMin());
               settingSettings.addProperty("valueMax", doubleSliderSetting.getInputMax());
               break;
            case SliderSetting.settingType:
               SliderSetting sliderSetting = (SliderSetting) setting;
               settingSettings.addProperty("value", sliderSetting.getInput());
               break;
            case TickSetting.settingType:
               TickSetting tickSetting = (TickSetting) setting;
               settingSettings.addProperty("value", tickSetting.isToggled());
               break;
         }

         settings.add(setting.settingName, settingSettings);
      }

      JsonObject data = new JsonObject();
      data.addProperty("enabled", enabled);
      data.addProperty("keycode", keycode);
      data.add("settings", settings);

      return data;
   }


   public void keybind() {
      if (this.keycode != 0 && this.canBeEnabled()) {
         if (!this.isToggled && Keyboard.isKeyDown(this.keycode)) {
            this.toggle();
            this.isToggled = true;
         } else if (!Keyboard.isKeyDown(this.keycode)) {
            this.isToggled = false;
         }
      }
   }

   public boolean canBeEnabled() {
      return true;
   }

   public void enable() {
      boolean oldState = this.enabled;
      this.enabled = true;
      if (oldState != this.enabled) {
         NotificationRenderer.moduleStateChanged(this);
      }
      ModuleManager.enModsList.add(this);

      Module hud = ModuleManager.getModuleByClazz(HUD.class);

      if (hud != null && hud.isEnabled()) {
         ModuleManager.sort();
      }

      this.onEnable();
      MinecraftForge.EVENT_BUS.register(this);
   }

   public void disable() {
      boolean oldState = this.enabled;
      this.enabled = false;
      if (oldState != this.enabled) {
         NotificationRenderer.moduleStateChanged(this);
      }
      ModuleManager.enModsList.remove(this);
      this.onDisable();
      MinecraftForge.EVENT_BUS.unregister(this);
   }

   public void setToggled(boolean enabled) {
      if(enabled == this.enabled)
         return;
      if(enabled){
         enable();
      } else{
         disable();
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
      if (this.enabled) {
         this.disable();
      } else {
         this.enable();
      }
   }

   public void update() {
   }

   public void guiUpdate() {
   }

   public void guiButtonToggled(TickSetting b) {
   }

   public int getKeycode() {
      return this.keycode;
   }

   public void setbind(int keybind) {
      this.keycode = keybind;
   }

   public void resetToDefaults() {
      this.keycode = this.defualtKeyCode;
      this.setToggled(false);
      for(Setting setting : this.settings){
         setting.resetToDefaults();
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
