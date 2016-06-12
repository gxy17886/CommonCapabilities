package org.cyclops.commoncapabilities.core;

import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Helper methods.
 * @author rubensworks
 */
public class Helpers {

    public static <R> R invokeMethod(Object instance, @Nullable Method method, Object... arguments) {
        if (method != null) {
            try {
                return (R) method.invoke(instance, arguments);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <R> R getFieldValue(Object instance, @Nullable Field field) {
        if (field != null) {
            try {
                return (R) field.get(instance);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <E> Method getMethod(Class<? super E> clazz, String method, Class<?>... methodTypes) {
        try {
            return ReflectionHelper.findMethod(clazz, null, new String[]{method}, methodTypes);
        } catch (ReflectionHelper.UnableToFindMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Field getField(Class<?> clazz, String field) {
        try {
            return ReflectionHelper.findField(clazz, field);
        } catch (ReflectionHelper.UnableToFindFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

}
