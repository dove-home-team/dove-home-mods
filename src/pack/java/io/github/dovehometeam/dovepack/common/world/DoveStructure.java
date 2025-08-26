package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:07:35
 */
public enum DoveStructure implements Supplier<ResourceKey<Structure>> {
    ;

    private final ResourceKey<Structure> key;

    DoveStructure() {
        key = ResourceKey.create(Registries.STRUCTURE, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<Structure> get() {
        return key;
    }


    public static void bootstrapStructures(BootstrapContext<Structure> context) {
        // 空实现，但需要注册原版结构引用
        // 原版结构已经在全局注册表中，这里不需要额外注册
    }
}
