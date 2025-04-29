package io.github.dovehometeam.dovestaff;

import io.github.dovehometeam.base.annotation.event.EventInject;
import io.github.dovehometeam.base.annotation.entrypoints.*;
import io.github.dovehometeam.base.annotation.event.GetArg;
import io.github.dovehometeam.dovestaff.common.init.ModComponents;
import io.github.dovehometeam.dovestaff.common.init.ModCreativeTabs;
import io.github.dovehometeam.dovestaff.common.init.ModItems;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 09:34:46}
 */
@Mod(Const.MODID)
public class DoveStaff {
    public DoveStaff(IEventBus modBus, ModContainer container) {
        modLoad(modBus);
        gameLoad(NeoForge.EVENT_BUS);


    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @ModEntryPoint
    public void modLoad(IEventBus bus) {
        ModComponents.class.getName();
        ModCreativeTabs.class.getName();
        ModItems.class.getName();
        ModComponents.REGISTER.register(bus);
        ModCreativeTabs.REGISTER.register(bus);
        ModItems.REGISTER.register(bus);
    }

    @GameEntryPoint
    public void gameLoad(IEventBus bus) {
    }
}
