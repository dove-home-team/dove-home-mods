package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:49:04
 */
public enum DovePlacedFeature implements Supplier<ResourceKey<PlacedFeature>> {
    OIL_WELL,
    CACTUS_PATCH,
    DEAD_BUSH_PATCH,
    ;
    private final ResourceKey<PlacedFeature> key;

    DovePlacedFeature() {
        this.key = ResourceKey.create(Registries.PLACED_FEATURE, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<PlacedFeature> get() {
        return key;
    }

    public static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // 油井特征 - 正确引用 ConfiguredFeature
        context.register(OIL_WELL.key, new PlacedFeature(
                configuredFeatures.getOrThrow(DoveConfiguredFeature.OIL_WELL.get()),
                List.of(
                        RarityFilter.onAverageOnceEvery(50),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.absolute(64), VerticalAnchor.absolute(70))),
                        BiomeFilter.biome()
                )
        ));

        // 仙人掌丛
        context.register(CACTUS_PATCH.key, new PlacedFeature(
                configuredFeatures.getOrThrow(DoveConfiguredFeature.CACTUS_PATCH.get()),
                List.of(
                        RarityFilter.onAverageOnceEvery(2),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        ));

        // 枯木丛
        context.register(DEAD_BUSH_PATCH.key, new PlacedFeature(
                configuredFeatures.getOrThrow(DoveConfiguredFeature.DEAD_BUSH_PATCH.get()),
                List.of(
                        RarityFilter.onAverageOnceEvery(3),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        ));
    }

}
