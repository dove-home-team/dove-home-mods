package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

import static io.github.dovehometeam.dovepack.common.world.DesertDimension.*;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:39:16
 */
public enum DoveNoiseGeneratorSettings implements Supplier<ResourceKey<NoiseGeneratorSettings>> {
    DESERT_NOISE;
    private final ResourceKey<NoiseGeneratorSettings> key;

    DoveNoiseGeneratorSettings() {
        this.key = ResourceKey.create(Registries.NOISE_SETTINGS, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<NoiseGeneratorSettings> get() {
        return key;
    }

    public static void bootstrapNoiseSettings(BootstrapContext<NoiseGeneratorSettings> context) {

        NoiseGeneratorSettings desertNoiseSettings = new NoiseGeneratorSettings(
                NoiseSettings.create(-64, 384, 1, 2),
                Blocks.STONE.defaultBlockState(),
                Blocks.WATER.defaultBlockState(),
                createMountainNoiseRouter(context.lookup(Registries.NOISE)),
                createSurfaceRule(),
                List.of(),
                64,
                false,
                true,
                true,
                false
        );

        context.register(DoveNoiseGeneratorSettings.DESERT_NOISE.key, desertNoiseSettings);
    }

    private static NoiseRouter createMountainNoiseRouter(HolderGetter<NormalNoise.NoiseParameters> noises) {
        Holder.Reference<NormalNoise.NoiseParameters> mountainNoise = noises.getOrThrow(DoveNoise.MOUNTAIN_NOISE.get());
        Holder.Reference<NormalNoise.NoiseParameters> erosionNoise = noises.getOrThrow(DoveNoise.EROSION_NOISE.get());
        Holder.Reference<NormalNoise.NoiseParameters> continentNoise = noises.getOrThrow(DoveNoise.CONTINENT_NOISE.get());

        DensityFunction add = DensityFunctions.add(
                DensityFunctions.noise(continentNoise, 100.0, 1.0),
                DensityFunctions.mul(
                        DensityFunctions.constant(0.6),
                        DensityFunctions.noise(erosionNoise, 50.0, 1.0)
                )
        );
        return new NoiseRouter(
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                DensityFunctions.constant(2.0),
                DensityFunctions.constant(-1.0),
                DensityFunctions.noise(continentNoise, 100.0, 1.0),  // continents
                DensityFunctions.mul(
                        DensityFunctions.constant(0.6),
                        DensityFunctions.noise(erosionNoise, 50.0, 1.0)  // erosion
                ),
                DensityFunctions.mul(
                        DensityFunctions.constant(0.4),
                        DensityFunctions.noise(mountainNoise, 80.0, 1.0) // depth
                ),
                DensityFunctions.constant(0),
                // initial_density - 山脉基础形状
                DensityFunctions.add(
                        DensityFunctions.constant(-0.3),
                        DensityFunctions.mul(
                                DensityFunctions.constant(1.5),
                                DensityFunctions.add(
                                        add,
                                        DensityFunctions.mul(
                                                DensityFunctions.constant(0.4),
                                                DensityFunctions.noise(mountainNoise, 80.0, 1.0)
                                        )
                                )
                        )
                ),
                DensityFunctions.zero(),
                DensityFunctions.constant(0),
                DensityFunctions.constant(0),
                DensityFunctions.constant(0)
        );
    }

    private static SurfaceRules.RuleSource createSurfaceRule() {
        return SurfaceRules.sequence(
                // 基岩层
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor",
                                VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
                        SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())),
                // 深板岩层 (-64 到 0)
                SurfaceRules.ifTrue(SurfaceRules
                                .verticalGradient("deepslate_layer",
                                        VerticalAnchor.absolute(-64), VerticalAnchor.absolute(0)),
                        SurfaceRules.state(Blocks.DEEPSLATE.defaultBlockState())),



                // 石头层 (0 到 50)
                SurfaceRules.ifTrue(SurfaceRules.
                                verticalGradient("stone_layer",
                                        VerticalAnchor.absolute(0), VerticalAnchor.absolute(50)),
                        SurfaceRules.state(Blocks.STONE.defaultBlockState())),
                // 下界合金等级方块 (51 到 53) - 添加缺失的层次

                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(51), 3),
                        SurfaceRules.state(Blocks.ANCIENT_DEBRIS.defaultBlockState())),

                // 砂岩层 (54 到 60)
                SurfaceRules.ifTrue(SurfaceRules
                                .verticalGradient("sandstone_layer",
                                        VerticalAnchor.absolute(54), VerticalAnchor.absolute(60)),
                        SurfaceRules.state(Blocks.SANDSTONE.defaultBlockState())),

                // 沙层 (60 到 64)
                SurfaceRules.ifTrue(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(60), 5),
                        SurfaceRules.state(Blocks.SAND.defaultBlockState())),

                // 地表规则
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.state(Blocks.SAND.defaultBlockState())),

                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR,
                        SurfaceRules.state(Blocks.SANDSTONE.defaultBlockState()))
        );
    }
}


