package keystrokesmod.client.module.modules.combat;

import keystrokesmod.client.module.Module;
import keystrokesmod.client.module.setting.impl.DoubleSliderSetting;
import keystrokesmod.client.module.setting.impl.TickSetting;
import keystrokesmod.client.utils.Utils;

public class Reach extends Module {
    public static DoubleSliderSetting reach;
    public static TickSetting weapon_only;
    public static TickSetting moving_only;
    public static TickSetting sprint_only;
   // public static LegitAura2 la;

    public Reach() {
        super("Reach", ModuleCategory.combat);
        this.registerSetting(reach = new DoubleSliderSetting("Reach (Blocks)", 3.1, 3.3, 3, 6, 0.05));
        this.registerSetting(weapon_only = new TickSetting("Weapon only", false));
        this.registerSetting(moving_only = new TickSetting("Moving only", false));
        this.registerSetting(sprint_only = new TickSetting("Sprint only", false));
    }
    
    @Override
    public void postApplyConfig() {
       // la = (LegitAura2) Raven.moduleManager.getModuleByClazz(LegitAura2.class);
    }

    public static double getReach() {
        //if(la.isEnabled()) 
        //    return Utils.Client.ranModuleVal(la.getReach(), Utils.Java.rand());
        
        if (!Utils.Player.isPlayerInGame())
            return 0;

        if (weapon_only.isToggled() && !Utils.Player.isPlayerHoldingWeapon())
            return 0;

        if (moving_only.isToggled() && (double) mc.thePlayer.moveForward == 0.0D
                && (double) mc.thePlayer.moveStrafing == 0.0D)
            return 0;

        if (sprint_only.isToggled() && !mc.thePlayer.isSprinting())
            return 0;
        
        return Utils.Client.ranModuleVal(reach, Utils.Java.rand());
    }
}
