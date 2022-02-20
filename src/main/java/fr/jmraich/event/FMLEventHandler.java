package fr.jmraich.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class FMLEventHandler {
    private final HashMap<Class<? extends Event>, ArrayList<Method>> EVENTS;
    private final Object INSTANCE;

    private FMLEventHandler(Object INSTANCE, HashMap<Class<? extends Event>, ArrayList<Method>> EVENTS) {
        this.EVENTS = EVENTS;
        this.INSTANCE = INSTANCE;
    }

    public Object getINSTANCE() {
        return INSTANCE;
    }

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

    public static FMLEventHandler getFMLEventHandlerOfObject(Object INSTANCE) throws NoSuchMethodException {
        HashMap<Class<? extends Event>, ArrayList<Method>> EVENT_METHODS = new HashMap<>();

        for (Method m : INSTANCE.getClass().getDeclaredMethods()) {
            if (!m.isAnnotationPresent(SubscribeEvent.class)) continue;

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
