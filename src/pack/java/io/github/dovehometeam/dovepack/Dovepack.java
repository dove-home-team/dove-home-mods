package io.github.dovehometeam.dovepack;

import com.mojang.datafixers.util.Pair;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.DataIngredient;
import io.github.dovehometeam.dovepack.config.DovePackConfig;
import io.github.dovehometeam.dovepack.init.PackComponents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import  io.github.dovehometeam.dovepack.init.PackItems;
import net.neoforged.neoforge.common.Tags;


import java.util.Map;
import java.util.function.Supplier;


@Mod(Const.MODID)
public class Dovepack {
    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);
    public Dovepack(IEventBus modBus, ModContainer container) {
        AutoConfig.register(DovePackConfig.class, Toml4jConfigSerializer::new);
        PackComponents.init();
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
        });
    }
}