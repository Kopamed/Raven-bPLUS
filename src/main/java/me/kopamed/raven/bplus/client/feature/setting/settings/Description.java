package me.kopamed.raven.bplus.client.feature.setting.settings;

import me.kopamed.raven.bplus.client.feature.setting.Setting;

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
