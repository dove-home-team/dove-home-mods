package io.github.dovehometeam.dovepack.common.block.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author baka4n
 * @code @Date 2025/8/24 11:04:20
 */
public abstract class MultiCheckBlockEntity extends BlockEntity {
    public MultiCheckBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public abstract int xRadius();
    public abstract int zRadius();
    public abstract int height();
}
