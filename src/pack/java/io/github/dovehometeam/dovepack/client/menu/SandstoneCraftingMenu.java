package io.github.dovehometeam.dovepack.client.menu;

import io.github.dovehometeam.dovepack.common.block.DoveCraftingTableBaseBlock;
import io.github.dovehometeam.dovepack.common.block.SandstoneCraftingTableBlock;
import io.github.dovehometeam.dovepack.common.init.ModBlocks;
import lombok.RequiredArgsConstructor;
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

public class SandstoneCraftingMenu extends DoveCraftingBaseMenu<SandstoneCraftingTableBlock> {


    public SandstoneCraftingMenu(int containerId, Inventory playerInventory) {
        super(containerId, playerInventory);
    }

    public SandstoneCraftingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
    }

    @Override
    public Supplier<SandstoneCraftingTableBlock> block() {
        return ModBlocks.SANDSTONE_CRAFTING_TABLE;
    }
}