package io.github.dovehometeam.dovepack.common.block;

import com.mojang.serialization.MapCodec;
import io.github.dovehometeam.dovelib.mods.vanilla.block.DoveCraftingTableBaseBlock;
import io.github.dovehometeam.dovelib.mods.vanilla.menu.DoveCraftingBaseMenu;
import io.github.dovehometeam.dovepack.client.menu.SandstoneCraftingMenu;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author baka4n
 * @code @Date 2025/8/22 09:37:14
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SandstoneCraftingTableBlock extends DoveCraftingTableBaseBlock<SandstoneCraftingTableBlock> {

    public static final MapCodec<SandstoneCraftingTableBlock> CODEC = simpleCodec(SandstoneCraftingTableBlock::new);

    public SandstoneCraftingTableBlock(Properties properties) {
        super(properties, "sandstone");
    }

    @Override
    public MapCodec<SandstoneCraftingTableBlock> codec() {
        return CODEC;
    }

    @Override
    public DoveCraftingBaseMenu<SandstoneCraftingTableBlock> menu(int containerId, Inventory playerInventory, Player player, Level level, BlockPos pos) {
        return new SandstoneCraftingMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos));
    }
}
