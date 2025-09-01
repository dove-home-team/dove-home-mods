package io.github.dovehometeam.dovepack;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.DataIngredient;
import io.github.dovehometeam.dovepack.common.init.ModAttachments;
import io.github.dovehometeam.dovepack.common.init.ModBlocks;
import io.github.dovehometeam.dovepack.config.DovePackConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

import java.util.Map;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/03 13:52:38}
 */
@Mod(Const.MODID)
public class DovePack {

    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);

    public DovePack(IEventBus modBus, ModContainer container) {
        ModBlocks.init();
        ModAttachments.init();

        AutoConfig.register(DovePackConfig.class, JanksonConfigSerializer::new);
        REGISTRATE.addDataGenerator(ProviderType.LANG, prov -> {
            prov.add("container.crafting.sandstone", "Sandstone Crafting Table");
        });
        REGISTRATE.addDataGenerator(ProviderType.RECIPE, prov -> {
            Map<DataIngredient, Pair<RecipeCategory, ? extends Item>> sandstoneSquare4 = Map.of(
                    DataIngredient.items(Items.SAND), Pair.of(RecipeCategory.MISC, Items.SANDSTONE),
                    DataIngredient.items(Items.RED_SAND), Pair.of(RecipeCategory.MISC, Items.RED_SANDSTONE)
            );
            sandstoneSquare4.forEach((ingredient, ctx) -> {

                ShapedRecipeBuilder builder = ShapedRecipeBuilder.shaped(ctx.getFirst(), ctx.getSecond()).define('X', ingredient.toVanilla());
                builder.pattern("XX").pattern("XX");
                builder.unlockedBy("has_" + prov.safeName(ingredient), ingredient.getCriterion(prov)).group("sandstone").save(prov, prov.safeId(ctx.getSecond()).withSuffix("_sandstone"));
            });
        });
    }

}
