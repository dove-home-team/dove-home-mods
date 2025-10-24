package io.github.dovehometeam.dovemultiritual.common.recipe;

import io.github.dovehometeam.dovemultiritual.common.init.MRRecipe;
import io.github.dovehometeam.dovemultiritual.common.init.MRRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.stream.Stream;

public class FormationRecipeManager {
    public static Optional<Holder<FormationRecipe>> getRecipeHolder(Level world, ResourceLocation id) {
        return world.registryAccess().registry(MRRegistries.FORMATION_DATAPACK_REGISTRY_KEY)
                .flatMap(formations -> formations.getHolder(ResourceKey.create(
                        MRRegistries.FORMATION_DATAPACK_REGISTRY_KEY, id
                )));
    }

    public static Stream<FormationRecipe> getAllRecipes(Level world) {
        return world.registryAccess()
                .registry(MRRegistries.FORMATION_DATAPACK_REGISTRY_KEY)
                .map(Registry::stream)
                .orElse(Stream.empty());
    }
    public static Stream<ResourceLocation> getRecipeIds(RegistryAccess registryAccess) {
        return registryAccess.registry(MRRegistries.FORMATION_DATAPACK_REGISTRY_KEY)
                .map(registry -> registry.keySet().stream())
                .orElse(Stream.empty());
    }

}
