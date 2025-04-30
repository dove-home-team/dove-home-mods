package io.github.dovehometeam.dweomercraft;

import io.github.dovehometeam.base.annotation.entrypoints.GameEntryPoint;
import io.github.dovehometeam.base.annotation.entrypoints.ModEntryPoint;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 00:45:38}
 */
@Mod(Const.MODID)
public class Dweomercraft {
    public Dweomercraft(IEventBus modBus, ModContainer container) {
        modEventBus(modBus);
    }

    @ModEntryPoint
    public void modEventBus(IEventBus modBus) {

    }

    @GameEntryPoint
    public void gameEventBus(IEventBus modBus) {

    }
}
