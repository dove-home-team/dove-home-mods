package io.github.dovehometeam.doveact.init;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import io.github.dovehometeam.doveact.Const;
import io.github.dovehometeam.doveact.client.menu.ActCraftingBaseMenu;
import io.github.dovehometeam.doveact.common.block.*;
import lombok.val;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.common.Tags;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.TriFunction;

import java.util.function.Supplier;

import static io.github.dovehometeam.doveact.Doveact.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/1 10:54:36
 */
public class DoveActBlocks {

    public static final BlockEntry<ActCraftingTableBaseBlock>
            SANDSTONE_CRAFTING_TABLE,
            IRON_CRAFTING_TABLE,
            GOLD_CRAFTING_TABLE,
            DIAMOND_CRAFTING_TABLE;

    static {
        {
            val sandstone = ResourceLocation.withDefaultNamespace("block/sandstone");
            SANDSTONE_CRAFTING_TABLE = registerAct(
                    "sandstone",
                    DoveActMenus.SANDSTONE_CRAFTING,
                    p -> p.mapColor(MapColor.SAND).strength(2.5F).sound(SoundType.STONE),
                    sandstone.withSuffix("_top"),
                    sandstone.withSuffix("_bottom"),
                    sandstone, sandstone, sandstone, sandstone,
                    () -> DataIngredient.tag(Tags.Items.SANDSTONE_BLOCKS)
            );
        }
        {
            val iron = ResourceLocation.withDefaultNamespace("block/iron_block");
            IRON_CRAFTING_TABLE = registerAct(
                    "iron",
                    DoveActMenus.IRON_CRAFTING,
                    p -> p.mapColor(MapColor.METAL).strength(3, 6).sound(SoundType.METAL),
                    iron, iron, iron, iron, iron, iron,
                    () -> DataIngredient.tag(Tags.Items.INGOTS_IRON)
            );
        }
        {
            val gold = ResourceLocation.withDefaultNamespace("block/gold_block");
            GOLD_CRAFTING_TABLE = registerAct(
                    "gold",
                    DoveActMenus.GOLD_CRAFTING,
                    p -> p.mapColor(MapColor.GOLD).strength(3, 6).sound(SoundType.METAL),
                    gold, gold, gold, gold, gold, gold,
                    () -> DataIngredient.tag(Tags.Items.INGOTS_GOLD)
            );
        }
        {
            val diamond = ResourceLocation.withDefaultNamespace("block/diamond_block");
            DIAMOND_CRAFTING_TABLE = registerAct(
                    "diamond",
                    DoveActMenus.DIAMOND_CRAFTING,
                    p -> p.mapColor(MapColor.DIAMOND).strength(3, 6).sound(SoundType.METAL),
                    diamond, diamond, diamond, diamond, diamond, diamond,
                    () -> DataIngredient.tag(Tags.Items.GEMS_DIAMOND)
            );
        }

    }

    public static BlockEntry<ActCraftingTableBaseBlock> registerAct(
            String group,
            MenuEntry<ActCraftingBaseMenu> menu,
            NonNullUnaryOperator<BlockBehaviour.Properties> props,
            ResourceLocation up, ResourceLocation down, ResourceLocation east, ResourceLocation west, ResourceLocation north, ResourceLocation south,
            Supplier<DataIngredient> ingredient
    ) {
        String name = group + "_crafting_table";
        return REGISTRATE
                .block(
                        name,
                        properties -> new ActCraftingTableBaseBlock(properties, menu, group)
                )
                .properties(props)
                .lang(RegistrateLangProvider.toEnglishName(name))
                .blockstate((ctx, prov) -> {
                    var it = prov.models().withExistingParent(ctx.getName(), Const.modLoc("block/empty_crafting_table"))
                            .texture("up", up)
                            .texture("down", down)
                            .texture("west", west)
                            .texture("east", east)
                            .texture("south", south)
                            .texture("north", north);

                    prov.simpleBlock(ctx.get(), it);
                })
                .item()
                .recipe((ctx, prov) -> {

                    DataIngredient tag = ingredient.get();
                    ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ctx.get()).define('X', tag.toVanilla());
                    builder.pattern("XX").pattern("XX");
                    builder.unlockedBy("has_" + prov.safeName(tag), tag.getCriterion(prov)).group(group).save(prov, prov.safeId(ctx.get()).withSuffix("_" + group));

                })
                .build()
                .register();
    }

    public static void init() {}
}
