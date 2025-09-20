package io.github.dovehometeam.dovebotania.init;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.dovebotania.Const;
import io.github.dovehometeam.dovebotania.common.block.BotaniaPetalApothecaryBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import vazkii.botania.common.block.BotaniaBlocks;
import vazkii.botania.common.lib.BotaniaTags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static io.github.dovehometeam.dovebotania.Dovebotania.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/5 14:18:20
 */
public class DoveBotaniaBlocks {
    public static final List<BlockEntry<BotaniaPetalApothecaryBlock>> APOTHECARIES = new ArrayList<>();
    public static final BlockEntry<BotaniaPetalApothecaryBlock> SANDSTONE = petalDefaultRegister(
            "apothecary_sandstone",
            ResourceLocation.withDefaultNamespace("sandstone"),
            ResourceLocation.withDefaultNamespace("sandstone_bottom"),
            ResourceLocation.withDefaultNamespace("sandstone_top"),
            () -> DataIngredient.tag(Tags.Items.SANDSTONE_BLOCKS)
    );
    public static final BlockEntry<BotaniaPetalApothecaryBlock> IRON = petalDefaultRegister(
            "apothecary_iron",
            ResourceLocation.withDefaultNamespace("iron_block"),
            ResourceLocation.withDefaultNamespace("iron_block"),
            ResourceLocation.withDefaultNamespace("iron_block"),
            () -> DataIngredient.items(Items.IRON_BLOCK)
    );
    public static final BlockEntry<BotaniaPetalApothecaryBlock> GOLD = petalDefaultRegister(
            "apothecary_gold",
            ResourceLocation.withDefaultNamespace("gold_block"),
            ResourceLocation.withDefaultNamespace("gold_block"),
            ResourceLocation.withDefaultNamespace("gold_block"),
            () -> DataIngredient.items(Items.GOLD_BLOCK)
    );
    public static final BlockEntry<BotaniaPetalApothecaryBlock> DIAMOND = petalDefaultRegister(
            "apothecary_diamond",
            ResourceLocation.withDefaultNamespace("diamond_block"),
            ResourceLocation.withDefaultNamespace("diamond_block"),
            ResourceLocation.withDefaultNamespace("diamond_block"),
            () -> DataIngredient.items(Items.DIAMOND)
    );


    @SafeVarargs
    public static BlockEntry<BotaniaPetalApothecaryBlock> petalDefaultRegister(String name,
                                                                               ResourceLocation side,
                                                                               ResourceLocation bottom,
                                                                               ResourceLocation top,
                                                                               Supplier<DataIngredient>... ss) {

        BlockEntry<BotaniaPetalApothecaryBlock> register = REGISTRATE
                .block(name, BotaniaPetalApothecaryBlock::new)
                .initialProperties(() -> BotaniaBlocks.defaultAltar)
                .lang(RegistrateLangProvider.toEnglishName(name))
                .blockstate((ctx, prov) -> {
                    BlockModelBuilder model = prov
                            .models()
                            .withExistingParent(ctx.getName(), Const.modLoc("block/petal_apothecary_default"))
                            .texture("side", side.withPrefix("block/"))
                            .texture("bottom", bottom.withPrefix("block/"))
                            .texture("top", top.withPrefix("block/"))
                            .renderType(RenderType.cutout().name);
                    prov.simpleBlock(ctx.get(), model);
                })
                .item()
                .recipe((ctx, prov) -> {
                    DataIngredient petals = DataIngredient.tag(BotaniaTags.Items.PETALS);
                    int i = 0;
                    for (var s : ss) {
                        DataIngredient ing = s.get();
                        ShapedRecipeBuilder
                                .shaped(RecipeCategory.MISC, ctx.get())
                                .pattern("CPC")
                                .pattern(" C ")
                                .pattern("CCC")
                                .define('P', petals.toVanilla())
                                .define('C', ing.toVanilla())
                                .unlockedBy("has_" + ctx.getName() + "_" + ing.getId(), ing.getCriterion(prov))
                                .save(prov, ctx.getId().withSuffix("_" + i));
                        i++;
                    }
                })
                .build()
                .register();
        APOTHECARIES.add(register);
        return register;

    }


    public static void init() {}
}
