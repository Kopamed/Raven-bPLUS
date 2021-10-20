package me.kopamed.lunarkeystrokes.module.setting.settings;

import me.kopamed.lunarkeystrokes.module.setting.Setting;

public class Description extends Setting {
   static String settingType = "desc";
   private String desc;

   public Description(String t) {
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
