package keystrokesmod.sToNkS.lib.fr.jmraich.rax.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class FMLEventHandler {
    private final HashMap<Class<? extends Event>, ArrayList<Method>> EVENTS;
    private final Object INSTANCE;

    /**
     * Default constructor
     * @param INSTANCE listener's instance
     * @param EVENTS event in listener's clazz and methods which implement them
     */
    private FMLEventHandler(Object INSTANCE, HashMap<Class<? extends Event>, ArrayList<Method>> EVENTS) {
        this.EVENTS = EVENTS;
        this.INSTANCE = INSTANCE;
    }

    /**
     * Get instance of current listener
     * @return current listener's instance
     */
    public Object getINSTANCE() {
        return INSTANCE;
    }

    /**
     * Dispatch an FML event into the sub*system
     * @param event event
     * @param <E> event
     */
    public <E extends Event> void call(E event) {
        Class<? extends Event> eventClazz = event.getClass();

        if (!EVENTS.containsKey(eventClazz)) return;

        EVENTS.get(eventClazz).forEach(method -> {
            if (!event.isCanceled()) {
                try {
                    method.invoke(this.getINSTANCE(), event);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Generate a "FMLEventHandler" instance from an Objecy
     * @param INSTANCE listener's instance
     * @return null if there is no listening methods (aka method annotated with "SubscribeEvent" or "FMLEvent")
     */
    public static FMLEventHandler getFMLEventHandlerOfObject(Object INSTANCE) throws NoSuchMethodException {
        HashMap<Class<? extends Event>, ArrayList<Method>> EVENT_METHODS = new HashMap<>();

        for (Method m : INSTANCE.getClass().getDeclaredMethods()) {
            if (
                !m.isAnnotationPresent(SubscribeEvent.class) && // default FML annotation
                !m.isAnnotationPresent(FMLEvent.class) // custom annotation
            ) continue;

            Class<?> eventClazz = m.getParameterTypes()[0];
            if (
                m.getParameterCount() == 1 &&
                Event.class.isAssignableFrom(eventClazz)
            ) {
                if (!m.isAccessible()) m.setAccessible(true);

                ArrayList<Method> methodsOfEvent = EVENT_METHODS.getOrDefault(eventClazz, new ArrayList<>());
                methodsOfEvent.add(m);
                EVENT_METHODS.put((Class<? extends Event>) eventClazz, methodsOfEvent);
            }
        }

        if (EVENT_METHODS.size() > 0) {
            return new FMLEventHandler(INSTANCE, EVENT_METHODS);
        } else {
            throw new NoSuchMethodException("No handling methods");
        }
    }
}
