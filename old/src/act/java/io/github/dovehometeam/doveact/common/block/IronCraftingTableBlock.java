package io.github.dovehometeam.doveact.common.block;

import com.mojang.serialization.MapCodec;
import io.github.dovehometeam.doveact.client.menu.ActCraftingBaseMenu;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * @author baka4n
 * @code @Date 2025/9/4 00:22:28
 */
@MethodsReturnNonnullByDefault
public class IronCraftingTableBlock extends ActCraftingTableBaseBlock<IronCraftingTableBlock> {
    public static final MapCodec<IronCraftingTableBlock> CODEC = simpleCodec(IronCraftingTableBlock::new);


    public IronCraftingTableBlock(Properties properties) {
        super(properties, "iron");
    }

    @Override
    public MapCodec<IronCraftingTableBlock> codec() {
        return CODEC;
    }

    @Override
    public ActCraftingBaseMenu<IronCraftingTableBlock> menu(int containerId, Inventory playerInventory, Player player, Level level, BlockPos pos) {
        return null;
    }
}
