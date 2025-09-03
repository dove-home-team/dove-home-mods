package io.github.dovehometeam.doveact.init;

import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.doveact.Const;
import io.github.dovehometeam.doveact.common.block.IronCraftingTableBlock;
import io.github.dovehometeam.doveact.common.block.SandstoneCraftingTableBlock;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;

import static io.github.dovehometeam.doveact.DoveAct.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/1 10:54:36
 */
public class DoveActBlocks {

    public static final BlockEntry<SandstoneCraftingTableBlock> SANDSTONE_CRAFTING_TABLE;
    public static final BlockEntry<IronCraftingTableBlock> IRON_CRAFTING_TABLE;
    static {
        {
            SANDSTONE_CRAFTING_TABLE = REGISTRATE.block("sandstone_crafting_table", SandstoneCraftingTableBlock::new)
                    .properties(p -> p
                            .mapColor(MapColor.SAND)
                            .strength(2.5F)
                            .sound(SoundType.STONE))
                    .lang("Sandstone Crafting Table")
                    .blockstate((ctx, prov) -> {
                        var it = prov.models().withExistingParent(ctx.getName(), Const.modLoc("block/empty_crafting_table"))
                                .texture("up", ResourceLocation.withDefaultNamespace("block/sandstone_top"))
                                .texture("down", ResourceLocation.withDefaultNamespace("block/sandstone_bottom"))
                                .texture("west", ResourceLocation.withDefaultNamespace("block/sandstone"))
                                .texture("east", ResourceLocation.withDefaultNamespace("block/sandstone"))
                                .texture("south", ResourceLocation.withDefaultNamespace("block/sandstone"))
                                .texture("north", ResourceLocation.withDefaultNamespace("block/sandstone"));

                        prov.simpleBlock(ctx.get(), it);
                    })
                    .item()
                    .recipe((ctx, prov) -> {
                        DataIngredient tag = DataIngredient.tag(Tags.Items.SANDSTONE_BLOCKS);
                        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get()).define('X', tag.toVanilla());
                        builder.pattern("XX").pattern("XX");
                        builder.unlockedBy("has_" + prov.safeName(tag), tag.getCriterion(prov)).group("sandstone").save(prov, prov.safeId(ctx.get()).withSuffix("_sandstone"));

                    })
                    .build()
                    .register();
        }

        {
            IRON_CRAFTING_TABLE = REGISTRATE.block("iron_crafting_table", IronCraftingTableBlock::new)
                    .properties(p -> p
                            .mapColor(MapColor.METAL)
                            .strength(3, 6)
                            .sound(SoundType.METAL))
                    .lang("Iron Crafting Table")
                    .blockstate((ctx, prov) -> {
                        var it = prov.models().withExistingParent(ctx.getName(), Const.modLoc("block/empty_crafting_table"))
                                .texture("up", ResourceLocation.withDefaultNamespace("block/iron_block"))
                                .texture("down", ResourceLocation.withDefaultNamespace("block/iron_block"))
                                .texture("west", ResourceLocation.withDefaultNamespace("block/iron_block"))
                                .texture("east", ResourceLocation.withDefaultNamespace("block/iron_block"))
                                .texture("south", ResourceLocation.withDefaultNamespace("block/iron_block"))
                                .texture("north", ResourceLocation.withDefaultNamespace("block/iron_block"));

                        prov.simpleBlock(ctx.get(), it);
                    })
                    .item()
                    .recipe((ctx, prov) -> {
                        DataIngredient tag = DataIngredient.tag(Tags.Items.INGOTS_IRON);
                        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get()).define('X', tag.toVanilla());
                        builder.pattern("XX").pattern("XX");
                        builder.unlockedBy("has_" + prov.safeName(tag), tag.getCriterion(prov)).group("iron").save(prov, prov.safeId(ctx.get()).withSuffix("_iron"));
                    })
                    .build()
                    .register();
        }
    }

    public static void init() {}
}
