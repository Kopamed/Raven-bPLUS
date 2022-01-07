package me.kopamed.raven.bplus.client.feature.module.modules.combat;


import me.kopamed.raven.bplus.client.feature.module.Module;
import me.kopamed.raven.bplus.client.feature.module.ModuleCategory;
import me.kopamed.raven.bplus.client.feature.module.modules.blatant.Timer;
import me.kopamed.raven.bplus.client.feature.setting.SelectorRunnable;
import me.kopamed.raven.bplus.client.feature.setting.settings.BooleanSetting;
import me.kopamed.raven.bplus.client.feature.setting.settings.RangeSetting;
import me.kopamed.raven.bplus.helper.utils.CoolDown;
import me.kopamed.raven.bplus.helper.utils.Transition;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

public class AutoClicker extends Module {
   public static final BooleanSetting leftClick = new BooleanSetting("Left", true);
   public static final RangeSetting leftCPS = new RangeSetting("Left CPS", 16, 20, 1, 20, 0.5);
   public static final BooleanSetting leftBlatant = new BooleanSetting("Blatant", false);

   private boolean leftButtonUp = true;
   private boolean clickingLeft = false;

   public AutoClicker(){
      super("Autoclicker", "Autoclicks for you", ModuleCategory.Combat);
      leftClick.addOnChange(new SelectorRunnable() {
         @Override
         public boolean showOnlyIf() {
            return false;
         }

         @Override
         public void onChange() {
            AutoClicker.leftCPS.setVisible(AutoClicker.leftClick.isToggled());
         }
      });
      leftBlatant.addOnChange(new SelectorRunnable() {
         @Override
         public boolean showOnlyIf() {
            return AutoClicker.leftClick.isToggled();
         }

         @Override
         public void onChange() {
            if(AutoClicker.leftBlatant.isToggled()){
               AutoClicker.leftCPS.setMax(100);
               AutoClicker.leftCPS.setInterval(1);
            } else {
               AutoClicker.leftCPS.setMax(20);
               AutoClicker.leftCPS.setInterval(0.5);
            }
         }
      });

      this.registerSetting(leftClick);
      this.registerSetting(leftCPS);
      this.registerSetting(leftBlatant);
   }

   @SubscribeEvent
   public void renderTickEvent(TickEvent.RenderTickEvent e){
      if(e.phase == TickEvent.Phase.START){
         // detecting when the user wants to start autoclicking and when to stop
         if(Mouse.isButtonDown(0) && !clickingLeft){
            clickingLeft = leftClick.isToggled();

            if(clickingLeft){
               // gen clicktimings
            }
         } else if(!Mouse.isButtonDown(0) && clickingLeft){
            clickingLeft = false;
         }


         // executing clicks
         if(mc.gameSettings.keyBindAttack.isKeyDown() && leftClick.isToggled()){
            /*if(System.currentTimeMillis() >= nextDown && leftButtonUp){
               leftButtonUp = false;
               pressLeft();
            } else if(System.currentTimeMillis() >= nextUp && !leftButtonUp){
               leftButtonUp = true;
               releaseLeft();
            }*/
         }
      }
   }
}