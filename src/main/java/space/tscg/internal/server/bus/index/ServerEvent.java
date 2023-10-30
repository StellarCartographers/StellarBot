package space.tscg.internal.server.bus.index;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Target;

import org.atteo.classindex.IndexAnnotated;

@Target({TYPE})
@IndexAnnotated
public @interface ServerEvent
{
}
