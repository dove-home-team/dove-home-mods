package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:49:04
 */
public enum DoveConfiguredFeature implements Supplier<ResourceKey<ConfiguredFeature<?, ?>>> {
    OIL_WELL,
    CACTUS_PATCH,
    DEAD_BUSH_PATCH,
    ;
    private final ResourceKey<ConfiguredFeature<?, ?>> key;

    DoveConfiguredFeature() {
        this.key = ResourceKey.create(Registries.CONFIGURED_FEATURE, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<ConfiguredFeature<?, ?>> get() {
        return key;
    }

    public static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // 油井特征
        context.register(OIL_WELL.key, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.BLACK_CONCRETE))
        ));

        // 仙人掌丛
        context.register(CACTUS_PATCH.key, new ConfiguredFeature<>(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(32, 7, 3,
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.CACTUS))
                        )
                )
        ));

        // 枯木丛
        context.register(DEAD_BUSH_PATCH.key, new ConfiguredFeature<>(
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(20, 6, 3,
                        PlacementUtils.onlyWhenEmpty(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.DEAD_BUSH))
                        )
                )
        ));
    }
}
