package space.tscg.util;

import java.lang.reflect.Field;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;

public class ASM
{
    @SuppressWarnings("unchecked")
    public static <E> boolean areFieldValuesEqual(E instance1, E instance2, String fieldName)
    {
        TypeToken<E> type = (TypeToken<E>) TypeToken.of(instance1.getClass());
        return getPrivateValue(type.getRawType(), instance1, fieldName).equals(getPrivateValue(type.getRawType(), instance2, fieldName));
        //f1.get(sc1).equals(f2.get(sc2)
    }
    
    @SuppressWarnings("unchecked")
    public static <T, E> T getPrivateValue(Class<? super E> classToAccess, @Nullable E instance, String fieldName) {
        try {
            return (T) findField(classToAccess, fieldName, null).get(instance);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
    
    public static Field findField(@Nonnull Class<?> clazz, @Nonnull String fieldName, @Nullable String fieldObfName) {
        Preconditions.checkNotNull(clazz);
        try {
            Field f = clazz.getDeclaredField(fieldName);
            f.setAccessible(true);
            return f;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }
}
