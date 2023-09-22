package space.tscg.util;

import java.io.Serializable;

import com.google.common.base.Predicate;

public class Conditional
{
    public static <T extends Object> Predicate<T> instanceOf(Class<?> clazz) {
        return new InstanceOfPredicate<>(clazz);
      }
    
    private static class InstanceOfPredicate<T extends Object> implements Predicate<T>, Serializable
    {
        private final Class<?> clazz;

        private InstanceOfPredicate(Class<?> clazz)
        {
            this.clazz = checkNotNull(clazz);
        }

        @Override
        public boolean apply(T o)
        {
            return clazz.isInstance(o);
        }

        @Override
        public int hashCode()
        {
            return clazz.hashCode();
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof InstanceOfPredicate)
            {
                InstanceOfPredicate<?> that = (InstanceOfPredicate<?>) obj;
                return clazz == that.clazz;
            }
            return false;
        }

        @Override
        public String toString()
        {
            return "Predicates.instanceOf(" + clazz.getName() + ")";
        }

        private static final long serialVersionUID = 0;
    }
    
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
          throw new NullPointerException();
        }
        return reference;
      }
}
