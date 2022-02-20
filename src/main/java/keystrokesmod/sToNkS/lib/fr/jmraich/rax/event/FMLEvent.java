package keystrokesmod.sToNkS.lib.fr.jmraich.rax.event;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Used to replace "net.minecraftforge.fml.common.eventhandler.SubscribeEvent"
 * in the event sub-system
 *
 * note : both of "SubscribeEvent" and "FMLEvent" work with this sub system but only "SubscribeEvent" works with FML's one
 */
@Documented
@Retention(value = RUNTIME)
@Target(value = METHOD)
public @interface FMLEvent {

}
