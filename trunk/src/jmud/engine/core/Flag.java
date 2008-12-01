package jmud.engine.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Date: 24-Dec-2007 Time: 6:46:03 PM
 * @author Chris Maguire
 */
@java.lang.annotation.Target( { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Flag {
   String[] aliases() default {};

   String name() default "";
}
