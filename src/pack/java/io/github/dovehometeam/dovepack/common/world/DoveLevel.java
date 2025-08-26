package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.dovehometeam.dovepack.common.world.DesertDimension.*;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:12:03
 */
public enum DoveLevel implements Supplier<ResourceKey<Level>> {
    DESERT_DIM;
    private final ResourceKey<Level> key;
    public final ResourceKey<LevelStem> stem;

    DoveLevel() {
        this.key = ResourceKey.create(Registries.DIMENSION, Const.modLoc(name().toLowerCase(Locale.ROOT)));
        this.stem = ResourceKey.create(Registries.LEVEL_STEM, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<Level> get() {
        return key;
    }

    public static void bootstrapDimension(BootstrapContext<LevelStem> context) {
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        // 创建 FixedBiomeSource
        FixedBiomeSource biomeSource = new FixedBiomeSource(
                biomes.getOrThrow(DoveBiome.DESERT.get())
        );

        // 创建 NoiseBasedChunkGenerator
        NoiseBasedChunkGenerator chunkGenerator = new NoiseBasedChunkGenerator(
                biomeSource,
                noiseSettings.getOrThrow(DoveNoiseGeneratorSettings.DESERT_NOISE.get())
        );

        context.register(DoveLevel.DESERT_DIM.stem,
                new LevelStem(
                        dimensionTypes.getOrThrow(DoveDimensionType.DESERT_DIM_TYPE.get()),
                        chunkGenerator
                ));
    }
}
