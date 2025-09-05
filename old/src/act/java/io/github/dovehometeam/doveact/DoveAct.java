package io.github.dovehometeam.doveact;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.doveact.common.block.SandstoneCraftingTableBlock;
import io.github.dovehometeam.doveact.init.DoveActBlocks;
import io.github.dovehometeam.doveact.init.DoveActMenus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

/**
 * @author baka4n
 * @code @Date 2025/9/1 09:42:25
 */
@Mod(Const.MODID)
public class DoveAct {
    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);
    public DoveAct(IEventBus modBus, ModContainer container) {
        DoveActBlocks.init();
        DoveActMenus.init();
        REGISTRATE.addDataGenerator(ProviderType.LANG, prov -> {
            prov.add(DoveActBlocks.SANDSTONE_CRAFTING_TABLE.get().key, "Sandstone Crafting Table");
        });
    }
}
