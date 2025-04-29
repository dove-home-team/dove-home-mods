package io.github.dovehometeam.dovestaff.common.init;

import io.github.dovehometeam.base.annotation.mods.group.ItemGroupLabel;
import io.github.dovehometeam.dovestaff.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 08:42:15}
 */

public enum ModCreativeTabs {
    @ItemGroupLabel
    STAFF(ModItems.DIAMOND_STAFF::toStack, (itemDisplayParameters, output) -> {});
    public static final DeferredRegister<CreativeModeTab> REGISTER = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Const.MODID);
    public final DeferredHolder<CreativeModeTab, CreativeModeTab> entry;
    ModCreativeTabs(Supplier<ItemStack> icon, CreativeModeTab.DisplayItemsGenerator gen) {
        entry = register().register(name().toLowerCase(Locale.ROOT), () ->
        {

            return CreativeModeTab
                    .builder()
                    .icon(icon)
                    .title(Component.translatable("itemGroup.%s.%s".formatted(
                            Const.MODID,
                            name().toLowerCase(Locale.ROOT)
                    )))
                    .displayItems(gen)
                    .build();
        });
    }

    public static DeferredRegister<CreativeModeTab> register() {
        return REGISTER;
    }
}
