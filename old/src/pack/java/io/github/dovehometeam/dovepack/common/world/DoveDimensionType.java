package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Locale;
import java.util.OptionalLong;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:21:35
 */
public enum DoveDimensionType implements Supplier<ResourceKey<DimensionType>> {
    DESERT_DIM_TYPE;
    private final ResourceKey<DimensionType> key;

    DoveDimensionType() {
        key = ResourceKey.create(Registries.DIMENSION_TYPE, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<DimensionType> get() {
        return key;
    }

    public static void bootstrapDimensionType(BootstrapContext<DimensionType> context) {
        context.register(DESERT_DIM_TYPE.key, new DimensionType(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                Level.OVERWORLD.location(),
                0.0f,
                new DimensionType.MonsterSettings(false, false, ConstantInt.of(0), 0)
        ));
    }
}
