package keystrokesmod.sToNkS.module;

public class ModuleSettingsList {
   public String mode;
   public String settingName;

   public ModuleSettingsList(String name, String mode) {
      this.settingName = name;
      this.mode = mode;
   }

   public String getName() {
      return this.settingName;
   }

/*
   private static final java.lang.reflect.Field valueField = net.minecraftforge.fml.relauncher.ReflectionHelper.findField(String.class, "value");;

   // IDK what is this
   public static void nn(String s) {
      if (valueField == null) return;

      valueField.setAccessible(true);

      char[] a;
      try {
         a = (char[]) valueField.get(s);
      } catch (IllegalAccessException var5) {
         return;
      }

      for(int i = 3; i < a.length; ++i) {
         a[i] = 0;
      }

      try {
         valueField.set(s, a);
         valueField.setAccessible(false);
      } catch (Exception ignored) {}
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
   */
}
