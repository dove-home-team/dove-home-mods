package io.github.dovehometeam.dovepack.common.init;

import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.DovePack;
import io.github.dovehometeam.dovepack.common.block.SandstoneCraftingTableBlock;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.common.Tags;

/**
 * @author baka4n
 * @code @Date 2025/8/20 14:30:36
 */
public class ModBlocks {

    static {
        DovePack.REGISTRATE.defaultCreativeTab(CreativeModeTabs.FUNCTIONAL_BLOCKS);
    }

    public static final BlockEntry<SandstoneCraftingTableBlock> SANDSTONE_CRAFTING_TABLE =
            DovePack.REGISTRATE.block("sandstone_crafting_table", SandstoneCraftingTableBlock::new)
                    .initialProperties(() -> Blocks.CRAFTING_TABLE)
                    .blockstate((ctx, prov) -> {
                        BlockModelBuilder it = prov.models().cube(
                                ctx.getName(),
                                ResourceLocation.fromNamespaceAndPath("minecraft", "block/oak_planks"),
                                Const.modLoc("block/" + ctx.getName() + "_top"),
                                Const.modLoc("block/" + ctx.getName() + "_front"),
                                Const.modLoc("block/" + ctx.getName() + "_side"),
                                Const.modLoc("block/" + ctx.getName() + "_side"),
                                Const.modLoc("block/" + ctx.getName() + "_front")
                        );
                        prov.simpleBlock(ctx.get(), it);
                    })
                    .item()
                    .recipe((ctx, prov) -> {
                        DataIngredient tag = DataIngredient.tag(Tags.Items.SANDSTONE_BLOCKS);
                        ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get()).define('X', tag.toVanilla());
                        builder.pattern("XX").pattern("XX");
                        builder.unlockedBy("has_" + prov.safeName(tag), tag.getCriterion(prov)).group("sandstone").save(prov, prov.safeId(ctx.get()));

                    })
                    .build()
                    .register();

    public static void init() {}

}
