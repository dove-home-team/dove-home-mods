package io.github.dovehometeam.dovepack.common.init;

import com.mojang.serialization.MapCodec;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.DovePack;
import io.github.dovehometeam.dovepack.common.block.DoveCraftingTableBaseBlock;
import io.github.dovehometeam.dovepack.common.block.DovePetalApothecaryBlock;
import io.github.dovehometeam.dovepack.common.block.SandstoneCraftingTableBlock;
import me.desht.pneumaticcraft.common.thirdparty.botania.Botania;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.common.Tags;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.BotaniaRegistries;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.block.PetalApothecaryBlock;
import vazkii.botania.common.item.BotaniaItems;

import static io.github.dovehometeam.dovepack.DovePack.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/8/20 14:30:36
 */
public class ModBlocks {

    public static final BlockEntry<SandstoneCraftingTableBlock> SANDSTONE_CRAFTING_TABLE;
    public static final BlockEntry<DovePetalApothecaryBlock> APOTHECARY_SANDSTONE;
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
        }// crafting table by dove
        REGISTRATE.defaultCreativeTab(BotaniaRegistries.BOTANIA_TAB_KEY);
        {
            APOTHECARY_SANDSTONE = REGISTRATE.block("apothecary_sandstone", DovePetalApothecaryBlock::new)
                    .initialProperties(() -> BotaniaBlocks.defaultAltar)
                    .blockstate((ctx, prov) -> {
                        BlockModelBuilder model = prov
                                .models()
                                .withExistingParent(ctx.getName(), Const.modLoc("block/botania_petal_apothecary"))
                                .texture("side", Const.modLoc("block/botania/" +ctx.getName() + "_side"))
                                .texture("bottom",  Const.modLoc("block/botania/" +ctx.getName() + "_bottom"))
                                .texture("top",  Const.modLoc("block/botania/" +ctx.getName() + "_top"))
                                .renderType(RenderType.cutout().name);
                        prov.simpleBlock(ctx.get(), model);

                    })
                    .properties(p -> p.mapColor(MapColor.SAND).sound(SoundType.STONE))
                    .item()
                    .build()
                    .register();
        }
    }



    public static void init() {

    }

}
