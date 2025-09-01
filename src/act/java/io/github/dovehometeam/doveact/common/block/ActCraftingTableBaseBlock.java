package io.github.dovehometeam.doveact.common.block;

import com.mojang.serialization.MapCodec;
import io.github.dovehometeam.doveact.client.menu.ActCraftingBaseMenu;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author baka4n
 * @code @Date 2025/8/23 10:01:03
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class ActCraftingTableBaseBlock<T extends ActCraftingTableBaseBlock<T>> extends Block {


    @Getter
    private final Set<String> groups;
    public final Component CONTAINER_TITLE;


    public abstract MapCodec<T> codec();

    public abstract ActCraftingBaseMenu<T> menu(int containerId, Inventory playerInventory, Player player, Level level, BlockPos pos);

    public ActCraftingTableBaseBlock(Properties properties, String... groups) {
        super(properties);
        this.groups = new HashSet<>(Arrays.asList(groups));
        CONTAINER_TITLE = Component.translatable("container.crafting." + groups[0]);
    }



    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId,
                                       playerInventory,
                                       player) ->
               menu(containerId, playerInventory, player, level, pos),
                CONTAINER_TITLE);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return InteractionResult.CONSUME;
        }
    }
}
