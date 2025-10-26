package io.github.dovehometeam.dovemultiritual.common.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import io.github.dovehometeam.dovemultiritual.client.render.block.RitualCoreBlockRender;
import io.github.dovehometeam.dovemultiritual.common.block.RitualCoreBlock;
import io.github.dovehometeam.dovemultiritual.common.block.entity.RitualCoreBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static io.github.dovehometeam.dovemultiritual.MultiRitual.REGISTRATE;

public class MRBlocks {
    public static final BlockEntry<RitualCoreBlock> RITUAL_CORE;
    public static final NonNullSupplier<BlockEntityType<RitualCoreBlockEntity>> RITUAL_CORE_BE;

    static {
        BlockEntityBuilder<RitualCoreBlockEntity, BlockBuilder<RitualCoreBlock, Registrate>> rcbe = REGISTRATE
                .block("ritual_core", RitualCoreBlock::new)
                .blockEntity(RitualCoreBlockEntity::new)
                .renderer(() -> RitualCoreBlockRender::new);
        RITUAL_CORE = rcbe
                .build()
                .blockstate((ctx, prov) -> {
                })//暂时黑紫块
                .item()
                .model((ctx, prov) -> {})
                .build()
                .register();
        RITUAL_CORE_BE = rcbe.asSupplier();

    }
    public static void init() {}
}
