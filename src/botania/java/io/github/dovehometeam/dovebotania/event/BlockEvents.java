package io.github.dovehometeam.dovebotania.event;

import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.dovebotania.Const;
import io.github.dovehometeam.dovebotania.Dovebotania;
import io.github.dovehometeam.dovebotania.common.block.BotaniaPetalApothecaryBlock;
import io.github.dovehometeam.dovebotania.init.DoveBotaniaBlocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import vazkii.botania.common.block.block_entity.BotaniaBlockEntities;

/**
 * @author baka4n
 * @code @Date 2025/9/5 15:30:04
 */
@EventBusSubscriber(modid = Const.MODID)
public class BlockEvents {
    @SubscribeEvent
    public static void addBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        for (BlockEntry<BotaniaPetalApothecaryBlock> apothecary : DoveBotaniaBlocks.APOTHECARIES) {
            event.modify(BotaniaBlockEntities.ALTAR, apothecary.get());
        }
    }
}
