package io.github.dovehometeam.dovemultiritual.common.block;

import com.mojang.serialization.MapCodec;
import io.github.dovehometeam.dovemultiritual.common.block.entity.RitualCoreBlockEntity;
import io.github.dovehometeam.dovemultiritual.common.init.MRBlocks;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RitualCoreBlock extends BaseEntityBlock {

    public static final MapCodec<RitualCoreBlock> CODEC = simpleCodec(RitualCoreBlock::new);
    public RitualCoreBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return MRBlocks.RITUAL_CORE_BE.get().create(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, MRBlocks.RITUAL_CORE_BE.get(), level.isClientSide ? RitualCoreBlock::clientTick : RitualCoreBlock::serverTick);
    }

    private static void serverTick(Level level, BlockPos blockPos, BlockState blockState, RitualCoreBlockEntity ritualCoreBlockEntity) {

    }

    public static float rotation(boolean isRun, boolean clockwise, float rotate) {
        if (isRun) {
            if (clockwise) {
                rotate--;
            } else {
                rotate++;
            }
        }
        return rotate;
    }

    private static void clientTick(Level level, BlockPos blockPos, BlockState blockState, RitualCoreBlockEntity be) {
        be.rotateX = rotation(be.type.isRunX, be.type.clockwiseX, be.rotateX);
        be.rotateY = rotation(be.type.isRunY, be.type.clockwiseY, be.rotateY);
        be.rotateZ = rotation(be.type.isRunZ, be.type.clockwiseZ, be.rotateZ);

        for (Map.Entry<RitualCoreBlockEntity.Data, Float> entry : be.dataRotateX.entrySet()) {
            RitualCoreBlockEntity.Data k = entry.getKey();
            Float v = entry.getValue();
            if (k.isRunX()) {
                if (k.clockwiseX()) {
                    v--;
                } else  {
                    v++;
                }
                be.dataRotateX.put(k, v);
            }
        }

        for (Map.Entry<RitualCoreBlockEntity.Data, Float> entry : be.dataRotateY.entrySet()) {
            RitualCoreBlockEntity.Data k = entry.getKey();
            Float v = entry.getValue();
            if (k.isRunY()) {
                if (k.clockwiseY()) {
                    v--;
                } else  {
                    v++;
                }
                be.dataRotateY.put(k, v);
            }
        }

        for (Map.Entry<RitualCoreBlockEntity.Data, Float> entry : be.dataRotateZ.entrySet()) {
            RitualCoreBlockEntity.Data k = entry.getKey();
            Float v = entry.getValue();
            if (k.isRunX()) {
                if (k.clockwiseZ()) {
                    v--;
                } else  {
                    v++;
                }
                be.dataRotateZ.put(k, v);
            }
        }
    }
}
