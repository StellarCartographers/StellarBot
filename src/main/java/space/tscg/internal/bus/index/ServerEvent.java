package space.tscg.internal.bus.index;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Target;

import org.atteo.classindex.IndexAnnotated;

@Target({TYPE})
@IndexAnnotated
public @interface ServerEvent
{
}
