package io.github.dovehometeam.dovepack.common.be;

import io.github.dovehometeam.dovepack.init.PackBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FormationBlockEntity extends BlockEntity {
    public FormationBlockEntity(BlockPos pos, BlockState blockState) {
        super(PackBlockEntities.FORMATION.get(), pos, blockState);
    }
}
