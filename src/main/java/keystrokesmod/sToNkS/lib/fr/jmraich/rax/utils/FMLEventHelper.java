package keystrokesmod.sToNkS.lib.fr.jmraich.rax.utils;

import com.google.common.reflect.TypeToken;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MinecraftDummyContainer;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import net.minecraftforge.fml.common.eventhandler.IEventListener;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An utility clazz to make FML event injection easier
 */
public class FMLEventHelper {
    private static final MinecraftDummyContainer modContainer;

    // pre defined to optimize performances
    static {
        modContainer = Loader.instance().getMinecraftModContainer();
    }

    /*
        Register a clazz into FML listeners without ModContainer :)
     */
    public static void fmlRegister(EventBus bus, Object target) throws ReflectiveOperationException {
        EReflection Reflection_EventBus = new EReflection(bus);

        ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners = Reflection_EventBus.getValue("listeners");
        Map<Object, ModContainer> listenerOwners = Reflection_EventBus.getValue("listenerOwners");

        if (!listeners.containsKey(target)) {
            listenerOwners.put(target, modContainer);
            Reflection_EventBus.setValue("listenerOwners", listenerOwners);
            Set<? extends Class<?>> supers = TypeToken.of(target.getClass()).getTypes().rawTypes();

            for (Method method : target.getClass().getMethods()) {
                for (Class<?> clazz : supers) {
                    try {
                        Method real = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
                        if (real.isAnnotationPresent(SubscribeEvent.class)) {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            if (parameterTypes.length != 1) {
                                throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but requires " + parameterTypes.length + " arguments.  Event handler methods must require a single argument.");
                            }

                            Class<?> eventClazz = parameterTypes[0];
                            if (!Event.class.isAssignableFrom(eventClazz)) {
                                throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, but takes a argument that is not an Event " + eventClazz);
                            }

                            Reflection_EventBus.callMethod("register", bus, eventClazz, target, method, modContainer);
                            break;
                        }
                    } catch (NoSuchMethodException ex) {
                        ;
                    }
                }
            }

        }
    }
}
