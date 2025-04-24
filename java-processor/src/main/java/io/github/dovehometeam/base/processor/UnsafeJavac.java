package io.github.dovehometeam.base.processor;

import lombok.Getter;
import lombok.val;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 08:53:54}
 */
public class UnsafeJavac {
    @Getter
    private static final sun.misc.Unsafe unsafe;
    @Getter
    private static final MethodHandles.Lookup lookup;

    static {
        MethodHandles.lookup();
        try {
            VarHandle theUnsafe = MethodHandles
                    .privateLookupIn(Unsafe.class, MethodHandles.lookup())
                    .findStaticVarHandle(Unsafe.class, "theUnsafe", Unsafe.class);
            unsafe = (Unsafe) theUnsafe.get();
            VarHandle implLookup = MethodHandles
                    .privateLookupIn(MethodHandles.Lookup.class, MethodHandles.lookup())
                    .findStaticVarHandle(MethodHandles.Lookup.class, "IMPL_LOOKUP", MethodHandles.Lookup.class);
            lookup = (MethodHandles.Lookup) implLookup.get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static void exportJdkModule() {
        try {
            val module = ModuleLayer.boot().findModule("jdk.compiler").orElseThrow();
            val method = Module.class.getDeclaredMethod("implAddExportsToAllUnnamed", String.class);
            val handle = getLookup().unreflect(method).bindTo(module);
            for (String aPackage : module.getPackages()) {
                handle.invoke(aPackage);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
