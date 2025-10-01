package io.github.dovehometeam.dovelib;

import io.github.dovehometeam.dovelib.event.custom.FurnaceSlotResultEvent;
import io.github.dovehometeam.dovelib.mods.kubejs.EventLibGroups;
import io.github.dovehometeam.dovelib.mods.kubejs.FurnaceSlotResultEventJs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Const.MODID)
public class Dovelib {
    public Dovelib(IEventBus modBus) {
        NeoForge.EVENT_BUS.<FurnaceSlotResultEvent>addListener(event -> {

            EventLibGroups.FURNACE_SLOT_RESULT.post(new FurnaceSlotResultEventJs(event));
        });
    }
}