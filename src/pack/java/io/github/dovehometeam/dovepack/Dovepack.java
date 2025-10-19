package io.github.dovehometeam.dovepack;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.DataIngredient;
import io.github.dovehometeam.dovepack.config.DovePackConfig;
import io.github.dovehometeam.dovepack.datagen.builder.FormationRecipeBuilder;
import io.github.dovehometeam.dovepack.init.*;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


@Mod(Const.MODID)
public class Dovepack {
    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);
    public Dovepack(IEventBus modBus, ModContainer container) {
        AutoConfig.register(DovePackConfig.class, Toml4jConfigSerializer::new);
        PackAttachments.init();
        PackComponents.init();
        PackRecipes.init();
//        PackBlockEntities.init();
        PackItems.init();
        REGISTRATE.addDataGenerator(ProviderType.RECIPE, (prov) -> {
            Map<DataIngredient, Pair<RecipeCategory, Supplier<ItemLike>>> squareSmall = Map.of(
                    DataIngredient.items(Items.SAND), Pair.of(RecipeCategory.MISC, () -> Items.SANDSTONE),
                    DataIngredient.items(Items.RED_SAND), Pair.of(RecipeCategory.MISC, () -> Items.RED_SANDSTONE)
            );
            for (var e : squareSmall.entrySet()) {
                var value = e.getValue();
                prov.square(e.getKey(), value.getFirst(), value.getSecond(), true);
            }
            ItemStack stack = Items.ACACIA_SLAB.getDefaultInstance();
            stack.set(DataComponents.BLOCK_STATE, BlockItemStateProperties.EMPTY
                    .with(SlabBlock.WATERLOGGED, true)
                    .with(SlabBlock.TYPE, SlabType.BOTTOM));
            FormationRecipeBuilder
                    .formation(Items.SANDSTONE)
                    .pattern("###########")
                    .pattern("# A A A A #")
                    .pattern("# A A A A #")
                    .pattern("# A A A A #")
                    .pattern("# A A A A #")
                    .pattern("# A A A A #")
                    .pattern("# A A A A #")
                    .pattern("# A A A A #")
                    .pattern("# A A A A #")
                    .pattern("# A A A A #")
                    .pattern("###########")
                    .define('#', Ingredient.of(stack))
                    .define('A', Items.SAND)
                    .save(prov, prov.safeId(Items.SANDSTONE).withPrefix("at/"));
        });
    }
}