package keystrokesmod.client.module.modules.movement;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.TickSetting;

public class StopMotion extends Module {
   public static TickSetting a;
   public static TickSetting b;
   public static TickSetting c;

   public StopMotion() {
      super("Stop Motion", Module.category.movement, 0);
      this.registerSetting(a = new TickSetting("Stop X", true));
      this.registerSetting(b = new TickSetting("Stop Y", true));
      this.registerSetting(c = new TickSetting("Stop Z", true));
   }

   public void onEnable() {
       //System.out.println("No fuck you");

      this.disable();
   }
}
