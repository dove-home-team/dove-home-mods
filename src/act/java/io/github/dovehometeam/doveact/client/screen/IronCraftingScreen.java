package io.github.dovehometeam.doveact.client.screen;

import io.github.dovehometeam.doveact.client.menu.IronCraftingMenu;
import io.github.dovehometeam.doveact.client.menu.SandstoneCraftingMenu;
import io.github.dovehometeam.doveact.common.block.IronCraftingTableBlock;
import io.github.dovehometeam.doveact.common.block.SandstoneCraftingTableBlock;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author baka4n
 * @code @Date 2025/8/22 16:48:37
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class IronCraftingScreen extends ActCraftingBaseScreen<IronCraftingTableBlock, IronCraftingMenu> {

    public IronCraftingScreen(IronCraftingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
