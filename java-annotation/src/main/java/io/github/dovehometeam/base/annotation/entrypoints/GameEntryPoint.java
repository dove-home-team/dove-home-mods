package io.github.dovehometeam.base.annotation.entrypoints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 08:53:14}
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface GameEntryPoint {
}
