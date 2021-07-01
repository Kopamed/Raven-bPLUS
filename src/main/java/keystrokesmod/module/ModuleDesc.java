package keystrokesmod.module;

public class ModuleDesc extends ModuleSettingsList {
   private String desc;

   public ModuleDesc(String t) {
      super(t);
      this.desc = t;
   }

   public String getDesc() {
      return this.desc;
   }

   public void setDesc(String t) {
      this.desc = t;
   }
}
