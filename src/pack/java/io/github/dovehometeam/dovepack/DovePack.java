package io.github.dovehometeam.dovepack;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerLifecycleEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/03 13:52:38}
 */
@Mod(Const.MODID)
public class DovePack {
    public DovePack(IEventBus modBus, ModContainer container) {
        NeoForge.EVENT_BUS.addListener((ServerTickEvent.Pre event) -> {
            MinecraftServer server = event.getServer();
            GameRules.BooleanValue rule = server.getGameRules().getRule(GameRules.RULE_WATER_SOURCE_CONVERSION);
            if (rule.get()) {
                rule.set(false, server);
            }
        });

    }
}
