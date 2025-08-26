package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/26 13:51:32
 */
public enum DoveStructureSet implements Supplier<ResourceKey<StructureSet>> {
    DESERT_VILLAGES;
    private final ResourceKey<StructureSet> key;

    DoveStructureSet() {
        key = ResourceKey.create(Registries.STRUCTURE_SET, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<StructureSet> get() {
        return key;
    }

    public static void bootstrapStructureSets(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

        // 创建沙漠村庄结构集
        context.register(DoveStructureSet.DESERT_VILLAGES.key, new StructureSet(
                List.of(
                        StructureSet.entry(structures.getOrThrow(BuiltinStructures.VILLAGE_DESERT))
                ),
                new RandomSpreadStructurePlacement(
                        34, // 间距
                        8,  // 分离
                        RandomSpreadType.LINEAR,
                        10387312 // 种子
                )
        ));
    }
}
