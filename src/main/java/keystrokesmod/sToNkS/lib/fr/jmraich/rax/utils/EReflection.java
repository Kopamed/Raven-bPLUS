package keystrokesmod.sToNkS.lib.fr.jmraich.rax.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * An utility clazz to make reflection easier
 */
public class EReflection {
    private final Object INSTANCE;

    public EReflection(Object o) {
        this.INSTANCE = o;
    }

    public <T> T callMethod(String name, Object... params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Class<?>> paramTypes = new ArrayList<>();
        for (Object o : params) {
            paramTypes.add(o.getClass());
        }

        Method m = this.INSTANCE.getClass().getDeclaredMethod(name, paramTypes.toArray(new Class[]{}));
        m.setAccessible(true);
        return (T) m.invoke(this.INSTANCE, params);
    }

    public void setValue(String name, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field f = this.INSTANCE.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(this.INSTANCE, value);
    }

    public <T> T getValue(String name) throws NoSuchFieldException, IllegalAccessException {
        Field f = this.INSTANCE.getClass().getDeclaredField(name);
        f.setAccessible(true);
        return (T) f.get(this.INSTANCE);
    }
}
