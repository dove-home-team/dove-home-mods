package io.github.dovehometeam.doveact.mods.kubejs;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import dev.latvian.mods.kubejs.typings.Param;
import io.github.dovehometeam.doveact.common.block.ActCraftingTableBaseBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * @author baka4n
 * @code @Date 2025/9/3 23:27:00
 */
public class RegistryActEventJs implements KubeEvent {
    @Info(
            value = "Add Crafting table groups",
            params = {
                    @Param(name = "item", value = "block item to set froup"),
                    @Param(name = "groups", value = "recipe group matcher")
            }
    )
    public void addGroup(ItemStack item, String... groups) {
        Item i = item.getItem();
        if (i instanceof BlockItem bItem && bItem.getBlock() instanceof ActCraftingTableBaseBlock<?> aBlock) {
            aBlock.getGroups().addAll(List.of(groups));
        }
    }
}
