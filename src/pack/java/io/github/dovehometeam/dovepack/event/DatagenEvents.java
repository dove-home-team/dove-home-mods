package io.github.dovehometeam.dovepack.event;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.world.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Const.MODID)
public class DatagenEvents {
    @SubscribeEvent
    public static void gatherEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
                output, lookupProvider, new RegistrySetBuilder()
                .add(Registries.NOISE, DoveNoise::bootstrapNoiseParameters)
                .add(Registries.DIMENSION_TYPE, DoveDimensionType::bootstrapDimensionType)
                .add(Registries.BIOME, DoveBiome::bootstrapBiome)
                .add(Registries.NOISE_SETTINGS, DoveNoiseGeneratorSettings::bootstrapNoiseSettings)
                .add(Registries.CONFIGURED_FEATURE, DoveConfiguredFeature::bootstrapConfiguredFeatures)
                .add(Registries.PLACED_FEATURE, DovePlacedFeature::bootstrapPlacedFeatures)
                .add(Registries.STRUCTURE, DoveStructure::bootstrapStructures)
                .add(Registries.STRUCTURE_SET, DoveStructureSet::bootstrapStructureSets)
                .add(Registries.LEVEL_STEM, DoveLevel::bootstrapDimension),
                Set.of(Const.MODID, "minecraft")
        ));
    }
}