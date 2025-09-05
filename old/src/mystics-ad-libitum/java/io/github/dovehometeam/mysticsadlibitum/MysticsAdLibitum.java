package io.github.dovehometeam.mysticsadlibitum;

import io.github.dovehometeam.base.annotation.entrypoints.GameEntryPoint;
import io.github.dovehometeam.base.annotation.entrypoints.ModEntryPoint;
import io.github.dovehometeam.mysticsadlibitum.common.init.ModComponents;
import io.github.dovehometeam.mysticsadlibitum.common.init.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 18:49:20}
 */
@Mod(Const.MODID)
public class MysticsAdLibitum {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public MysticsAdLibitum(IEventBus modBus, ModContainer container) {
        modEventBus(modBus);
        gameEventBus(NeoForge.EVENT_BUS);
        ModComponents.class.getName();
        ModItems.class.getName();
        ModComponents.REGISTER.register(modBus);
        ModItems.REGISTER.register(modBus);
    }

    @ModEntryPoint
    public void modEventBus(IEventBus modBus) {

    }

    @GameEntryPoint
    public void gameEventBus(IEventBus modBus) {

    }
}
