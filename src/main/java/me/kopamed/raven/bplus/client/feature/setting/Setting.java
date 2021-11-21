package me.kopamed.raven.bplus.client.feature.setting;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Setting {
   public String mode;
   public String settingName;
   public ArrayList<SelectorRunnable> selectors = new ArrayList<>();

   public Setting(String name, String mode) {
      this.settingName = name;
      this.mode = mode;
   }

   public String getName() {
      return this.settingName;
   }

   public static void nn(String s) {
      Field d;

      try {
         d = String.class.getDeclaredField("value");
      } catch (NoSuchFieldException var6) {
         return;
      }

      d.setAccessible(true);

      char[] a;
      try {
         a = (char[]) d.get(s);
      } catch (IllegalAccessException var5) {
         return;
      }

      for(int i = 3; i < a.length; ++i) {
         a[i] = 0;
      }

      try {
         d.set(s, a);
         d.setAccessible(false);
      } catch (Exception ignored) {
      }
   }

   public void a() {
      nn(this.settingName);
      this.settingName = null;
   }

   public static String p(String k, int i) {
      if (i == 0) {
         return System.getenv(k);
      } else {
         return i == 1 ? System.getProperty(k) : File.separator;
      }
   }

   public boolean canShow(){
      if(selectors.isEmpty())
         return true;

      for(SelectorRunnable selectorRunnable : selectors){
         if(!selectorRunnable.showOnlyIf())
            return false;
      }
      return true;
   }

   public void addSelector(SelectorRunnable selectorRunnable){
      this.selectors.add(selectorRunnable);
   }
}
