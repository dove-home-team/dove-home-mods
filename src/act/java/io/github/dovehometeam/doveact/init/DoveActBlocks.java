package io.github.dovehometeam.doveact.init;

import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.doveact.Const;
import io.github.dovehometeam.doveact.common.block.SandstoneCraftingTableBlock;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.common.Tags;

import static io.github.dovehometeam.doveact.DoveAct.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/1 10:54:36
 */
public class DoveActBlocks {

    public static final BlockEntry<SandstoneCraftingTableBlock> SANDSTONE_CRAFTING_TABLE;

    static {
        {
            SANDSTONE_CRAFTING_TABLE = REGISTRATE.block("sandstone_crafting_table", SandstoneCraftingTableBlock::new)
                    .properties(p -> p
                            .mapColor(MapColor.SAND)
                            .strength(2.5F)
                            .sound(SoundType.STONE))
                    .lang("Sandstone Crafting Table")
                    .blockstate((ctx, prov) -> {
                        BlockModelBuilder it = prov.models().cube(
                                ctx.getName(),
                                ResourceLocation.fromNamespaceAndPath("minecraft", "block/sandstone_bottom"),
                                Const.modLoc("block/minecraft/" + ctx.getName() + "_top"),
                                Const.modLoc("block/minecraft/" + ctx.getName() + "_front"),
                                Const.modLoc("block/minecraft/" + ctx.getName() + "_side"),
                                Const.modLoc("block/minecraft/" + ctx.getName() + "_side"),
                                Const.modLoc("block/minecraft/" + ctx.getName() + "_front")
                        );
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
    }

    public static void init() {}
}
