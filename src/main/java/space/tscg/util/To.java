package space.tscg.util;

import java.lang.reflect.Field;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import space.tscg.util.function.IncludeIf;

public class To
{

    public static String Json(final Object object)
    {
        return new ReflectionToStringBuilder(object, ToStringStyle.JSON_STYLE).toString();
    }
    
    public static String String(final Object o)
    {
        var builder = new ReflectionString(o);
        builder.setExcludeNullValues(true);
        return builder.build();
    }

    public static final ToStringStyle JsonStyle = new GsonToStringStyle();

    private static class ReflectionString extends ReflectionToStringBuilder
    {

        public ReflectionString(Object o)
        {
            super(o, To.JsonStyle);
        }

        @Override
        protected boolean accept(final Field field)
        {
            if (field.isAnnotationPresent(IncludeIf.class))
            {
                var incif = field.getAnnotation(IncludeIf.class);
                try
                {
                    return incif.value().check(this.getValue(field));
                } catch (IllegalArgumentException | IllegalAccessException e)
                {
                }
            }
            return super.accept(field);
        }
    }

    private static class GsonToStringStyle extends ToStringStyle
    {

        private static final long serialVersionUID = 1L;

        GsonToStringStyle()
        {
            this.setUseShortClassName(true);
            this.setUseIdentityHashCode(false);
            this.setContentStart("[");
            this.setFieldSeparator(System.lineSeparator() + "  ");
            this.setFieldSeparatorAtStart(true);
            this.setContentEnd(System.lineSeparator() + "]");
        }

        /**
         * <p>
         * Ensure {@code Singleton} after serialization.
         * </p>
         *
         * @return the singleton
         */
        private Object readResolve()
        {
            return MULTI_LINE_STYLE;
        }
    }
}
