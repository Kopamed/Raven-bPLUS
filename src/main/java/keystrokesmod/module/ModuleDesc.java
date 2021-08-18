package keystrokesmod.module;

import keystrokesmod.main.Ravenbplus;

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
      //if(Ravenbplus.config != null)
      //   Ravenbplus.config.updateConfigFile();
   }
}
