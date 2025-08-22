package io.github.dovehometeam.dovepack.common.block;

import com.mojang.serialization.MapCodec;
import io.github.dovehometeam.dovepack.client.menu.SandstoneCraftingMenu;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;

/**
 * @author baka4n
 * @code @Date 2025/8/22 09:37:14
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SandstoneCraftingTableBlock extends CraftingTableBlock {

    public static final MapCodec<SandstoneCraftingTableBlock> CODEC = simpleCodec(SandstoneCraftingTableBlock::new);
    public static final Component CONTAINER_TITLE = Component.translatable("container.crafting.sandstone");

    @Override
    public MapCodec<SandstoneCraftingTableBlock> codec() {
        return CODEC;
    }

    public SandstoneCraftingTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId, playerInventory, player) -> new SandstoneCraftingMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos)), CONTAINER_TITLE);
    }


}
