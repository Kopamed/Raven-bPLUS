package keystrokesmod.module;

public class ModuleSetting extends ModuleSettingsList {
   private String name;
   private boolean isEnabled;

   public ModuleSetting(String name, boolean isEnabled) {
      super(name);
      this.name = name;
      this.isEnabled = isEnabled;
   }

   public String get() {
      return this.name;
   }

   public boolean isToggled() {
      return this.isEnabled;
   }

   public void toggle() {
      this.isEnabled = !this.isEnabled;
   }

   public void enable() {
      this.isEnabled = true;
   }

   public void disable() {
      this.isEnabled = false;
   }

   public void setEnabled(boolean b) {
      this.isEnabled = b;
   }
}
