package io.github.dovehometeam.dovepack.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.recipe.FormationRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static io.github.dovehometeam.dovepack.Dovepack.REGISTRATE;

public class PackRecipes {

    public static final  RegistryEntry<RecipeType<?>, RecipeType<FormationRecipe>> FORMATION_RECIPE_TYPE =
            REGISTRATE.simple("formation_type", Registries.RECIPE_TYPE, () -> RecipeType.simple(Const.modLoc("formation")));
    public static final RegistryEntry<RecipeSerializer<?>, FormationRecipe.Serializer> FORMATION_RECIPE_SERIALIZER =
            REGISTRATE.simple("formation_serial", Registries.RECIPE_SERIALIZER, FormationRecipe.Serializer::new);

    public static void init() {}
}
