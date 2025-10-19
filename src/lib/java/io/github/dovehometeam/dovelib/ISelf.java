package io.github.dovehometeam.dovelib;

public interface ISelf<T> {
    default T dove_home_mods$self() {
        //noinspection unchecked
        return (T) this;
    }
}
