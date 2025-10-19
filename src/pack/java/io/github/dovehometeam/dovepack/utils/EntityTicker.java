package io.github.dovehometeam.dovepack.utils;

import io.github.dovehometeam.dovepack.common.be.FormationBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Unique;

public interface EntityTicker {
    void dove_home_mods$tick(Level level, BlockPos blockPos, BlockState blockState, FormationBlockEntity formationBlockEntity);

    @javax.annotation.Nullable
    static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> serverType, BlockEntityType<E> clientType, BlockEntityTicker<? super E> ticker) {
        //noinspection unchecked
        return clientType == serverType ? (BlockEntityTicker<A>) ticker : null;
    }
}
