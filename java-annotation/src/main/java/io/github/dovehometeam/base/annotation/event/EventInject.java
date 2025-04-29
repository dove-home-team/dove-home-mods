package io.github.dovehometeam.base.annotation.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/28 00:27:52}
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface EventInject {
    boolean mod() default true;
}
