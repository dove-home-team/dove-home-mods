package io.github.dovehometeam.formationmaster;

import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.function.Predicate;

@Mod(Const.MODID)
public class FormationMaster {
    public FormationMaster(IEventBus modBus, ModContainer container) {
        NeoForge.EVENT_BUS.addListener((ServerTickEvent.Pre event)  -> {

        });
    }
}
