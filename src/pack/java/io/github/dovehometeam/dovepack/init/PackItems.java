package io.github.dovehometeam.dovepack.init;

import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.awt.*;
import java.util.Map;

import static io.github.dovehometeam.dovepack.Dovepack.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/23 14:51:56
 */
public class PackItems {

    public static final ItemEntry<Item> CACTUS_CHARCOAL =
            REGISTRATE
                    .item("cactus_charcoal", Item::new)
                    .register();
    public static final ItemEntry<Item> SANDSTONE_STICK =
            REGISTRATE
                    .item("sandstone_stick", Item::new)
                    .color(() -> () -> (itemStack, i) -> 0xFFFEFA00)
                    .model((ctx, prov) -> {
                        prov.generated(ctx, BuiltInRegistries.ITEM.getKey(Items.STICK).withPrefix("item/"));
                    })
                    .recipe((ctx, prov) -> {
                        Map<Integer, String[]> groups = Map.of(
                                2, new String[] {"sandstone", "stone", "glowstone", "lapis"},
                                3, new String[] {"iron", "gold"},
                                5, new String[] {"diamond", "emerald"},
                                8, new String[] {"netherite"}
                        );
                        groups.forEach((key, value) -> {
                            for (String s : value) {
                                DataIngredient sandstone = DataIngredient.tag(Tags.Items.SANDSTONE_BLOCKS);
                                ShapedRecipeBuilder
                                        .shaped(RecipeCategory.MISC, ctx.get(), key)
                                        .group(s + "_" + ctx.getId())
                                        .pattern("#")
                                        .pattern("#")
                                        .define('#', sandstone.toVanilla())
                                        .unlockedBy("has_sandstone", sandstone.getCriterion(prov))
                                        .save(prov, prov.safeId(ctx.get()).withPrefix(s + "/"));
                            }
                        });

                    })
                    .register();

    public static void init() {}
}
