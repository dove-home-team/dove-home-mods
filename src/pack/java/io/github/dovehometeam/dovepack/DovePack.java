package io.github.dovehometeam.dovepack;

import io.github.dovehometeam.dovepack.config.DovePackConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/03 13:52:38}
 */
@Mod(Const.MODID)
public class DovePack {
    public DovePack(IEventBus modBus, ModContainer container) {
        gameEventBus(NeoForge.EVENT_BUS);
        modEventBus(modBus);
        AutoConfig.register(DovePackConfig.class, JanksonConfigSerializer::new);
    }


    public void gameEventBus(IEventBus eventBus) {

    }

    public void modEventBus(IEventBus eventBus) {

    }


}
