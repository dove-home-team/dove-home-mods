package io.github.dovehometeam.doveact.client.menu;

import io.github.dovehometeam.doveact.common.block.SandstoneCraftingTableBlock;

import io.github.dovehometeam.doveact.init.DoveActBlocks;
import io.github.dovehometeam.doveact.init.DoveActMenus;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerLevelAccess;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/22 09:50:49
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault

public class SandstoneCraftingMenu extends ActCraftingBaseMenu<SandstoneCraftingTableBlock> {


    public SandstoneCraftingMenu(int containerId, Inventory playerInventory) {
        super(DoveActMenus.SANDSTONE_CRAFTING.get(),containerId, playerInventory);
    }

    public SandstoneCraftingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(DoveActMenus.SANDSTONE_CRAFTING.get(),containerId, playerInventory, access);
    }

    @Override
    public Supplier<SandstoneCraftingTableBlock> block() {
        return DoveActBlocks.SANDSTONE_CRAFTING_TABLE;
    }
}