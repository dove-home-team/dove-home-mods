package io.github.dovehometeam.dovemultiritual.common.init;

import io.github.dovehometeam.dovemultiritual.common.recipe.FormationRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import static io.github.dovehometeam.dovemultiritual.MultiRitual.REGISTRATE;

public class MRRegistries {
    public static final ResourceKey<Registry<FormationRecipe>> FORMATION_DATAPACK_REGISTRY_KEY =
            REGISTRATE.makeDatapackRegistry("formation", FormationRecipe.MAP_CODEC.codec());
    public static void init() {}
}
