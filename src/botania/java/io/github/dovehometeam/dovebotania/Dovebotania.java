package io.github.dovehometeam.dovebotania;

import com.tterrag.registrate.Registrate;
import io.github.dovehometeam.dovebotania.init.DoveBotaniaBlocks;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Const.MODID)
public class Dovebotania {
    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);
    public Dovebotania(IEventBus eventBus, ModContainer container) {
        DoveBotaniaBlocks.init();
    }
}