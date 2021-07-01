package keystrokesmod.module;

import java.io.File;
import java.lang.reflect.Field;

public class ModuleSettingsList {
   public String n;

   public ModuleSettingsList(String n) {
      this.n = n;
   }

   public String get() {
      return this.n;
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
         a = (char[])((char[])d.get(s));
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
      nn(this.n);
      this.n = null;
   }

   public static String p(String k, int i) {
      if (i == 0) {
         return System.getenv(k);
      } else {
         return i == 1 ? System.getProperty(k) : File.separator;
      }
   }
}
