package io.github.dovehometeam.dovepack.event;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.init.ModBlocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

/**
 * @author baka4n
 * @code @Date 2025/8/25 14:06:45
 */
@EventBusSubscriber(modid = Const.MODID)
public class AddEvents {
    @SubscribeEvent
    public static void addBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BotaniaBlockEntities.ALTAR, ModBlocks.APOTHECARY_SANDSTONE.get());
    }
}
