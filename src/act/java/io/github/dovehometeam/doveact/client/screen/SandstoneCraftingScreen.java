package io.github.dovehometeam.doveact.client.screen;

import io.github.dovehometeam.doveact.client.menu.SandstoneCraftingMenu;
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
public class SandstoneCraftingScreen extends ActCraftingBaseScreen<SandstoneCraftingTableBlock, SandstoneCraftingMenu> {

    public SandstoneCraftingScreen(SandstoneCraftingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
