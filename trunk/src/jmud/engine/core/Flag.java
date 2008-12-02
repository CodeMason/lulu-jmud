package jmud.engine.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Chris Maguire
 */
@java.lang.annotation.Target( { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Flag {
   String[] aliases() default {};

   String name() default "";
}
