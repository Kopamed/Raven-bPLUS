package keystrokesmod.module;

public class ModuleDesc extends ModuleSettingsList {
   static String settingType = "desc";
   private String desc;

   public ModuleDesc(String t) {
      super(t, settingType);
      this.desc = t;
   }

   public String getDesc() {
      return this.desc;
   }

   public void setDesc(String t) {
      this.desc = t;
   }
}
