package io.github.dovehometeam.dovepack.events.mods;

import io.github.dovehometeam.dovepack.Const;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 20:07:52}
 */
@EventBusSubscriber(modid = Const.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

    }
}
