package keystrokesmod.client.module;

import com.google.gson.JsonObject;
import keystrokesmod.client.NotificationRenderer;
import keystrokesmod.client.module.modules.HUD;
import keystrokesmod.client.module.setting.Setting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class Module {
   protected ArrayList<Setting> settings;
   private final String moduleName;
   private final ModuleCategory moduleCategory;
   private boolean enabled;
   private int keycode;
   protected static Minecraft mc;
   private boolean isToggled = false;

   private final int defualtKeyCode;

   public Module(String name, ModuleCategory moduleCategory) {
      this.moduleName = name;
      this.moduleCategory = moduleCategory;
      this.keycode = 0;
      this.enabled = false;
      this.settings = new ArrayList<>();
      this.defualtKeyCode = 0;
   }

   public Module(String moduleName, ModuleCategory moduleCategory, int keycode) {
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
         JsonObject settingData = setting.getConfigAsJson();
         settings.add(setting.settingName, settingData);
      }

      JsonObject data = new JsonObject();
      data.addProperty("enabled", enabled);
      data.addProperty("keycode", keycode);
      data.add("settings", settings);

      return data;
   }

   public void applyConfigFromJson(JsonObject data){
      try {
         this.keycode = data.get("keycode").getAsInt();
         setToggled(data.get("enabled").getAsBoolean());
         JsonObject settingsData = data.get("settings").getAsJsonObject();
         for (Setting setting : getSettings()) {
            if (settingsData.has(setting.getName())) {
               setting.applyConfigFromJson(
                       settingsData.get(setting.getName()).getAsJsonObject()
               );
            }
         }
      } catch (NullPointerException ignored){

      }
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
      if(enabled)
         enable();
      else
         disable();
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

   public void onGuiClose() {

   }

   public String getBindAsString(){
      return keycode == 0 ? "None" : Keyboard.getKeyName(keycode);
   }

   public void clearBinds() {
      this.keycode = 0;
   }

   public enum ModuleCategory {
      combat,
      movement,
      player,
      world,
      render,
      minigames,
      other,
      client,
      hotkey
   }
}
