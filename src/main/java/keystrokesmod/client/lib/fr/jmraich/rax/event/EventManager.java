package keystrokesmod.client.lib.fr.jmraich.rax.event;

import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventManager {
    private static final List<FMLEventHandler> fmlEventHandlers;

    static {
        fmlEventHandlers = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Register an object into the event sub-system
     * @param INSTANCE target object to register
     * @return false if no handler method have been found
     */
    public static boolean register(Object INSTANCE) {
        try {
            fmlEventHandlers.add(FMLEventHandler.getFMLEventHandlerOfObject(INSTANCE));
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * Unregister an object from the event sub-system
     * @param INSTANCE target object to unregister
     * @return false if no it wasn't registered
     */
    public static boolean unregister(Object INSTANCE) {
        for (FMLEventHandler fmlEventHandler : fmlEventHandlers) {
            if (fmlEventHandler.getINSTANCE() == INSTANCE) {
                fmlEventHandlers.remove(fmlEventHandler);
                return true;
            }
        }

        return false;
    }


    /**
     * Distribute an event into the event sub-system
     * @param event event to distribute
     * @param <E> event to distribute
     */
    public static <E extends Event> void callEvent(E event) {
        fmlEventHandlers.forEach(fmlEventHandler -> fmlEventHandler.call(event));
    }
}
