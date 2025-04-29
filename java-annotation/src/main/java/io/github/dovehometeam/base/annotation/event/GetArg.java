package io.github.dovehometeam.base.annotation.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/28 10:33:53}
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE_USE)
public @interface GetArg {
    String value();
}
