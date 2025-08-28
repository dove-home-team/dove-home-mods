package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.Locale;
import java.util.function.Supplier;


/**
 * @author baka4n
 * @code @Date 2025/8/26 14:34:59
 */
public enum DoveBiome implements Supplier<ResourceKey<Biome>> {
    DESERT;

    private final ResourceKey<Biome> key;

    DoveBiome() {
        this.key = ResourceKey.create(Registries.BIOME, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    public static void bootstrapBiome(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver<?>> carvers =
                context.lookup(Registries.CONFIGURED_CARVER);

        context.register(DESERT.key,
                new Biome.BiomeBuilder()
                        .temperature(2.0f)
                        .downfall(0.0f)
                        .specialEffects(new BiomeSpecialEffects.Builder()
                                .skyColor(0x6EB1FF)
                                .fogColor(0xC0D8FF)
                                .waterColor(0x3F76E4)
                                .waterFogColor(0x050533)
                                .build())
                        .mobSpawnSettings(new MobSpawnSettings.Builder()
                                .addSpawn(EntityType.RABBIT.getCategory(),
                                        new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 2, 4))
                                .build())
                        .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers)
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DovePlacedFeature.CACTUS_PATCH.get())
                                .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DovePlacedFeature.DEAD_BUSH_PATCH.get())
                                .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, DovePlacedFeature.OIL_WELL.get())
                                .build())
                        .build());
    }

    @Override
    public ResourceKey<Biome> get() {
        return key;
    }
}
