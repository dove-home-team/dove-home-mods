package io.github.dovehometeam.dovepack.events.mods;

import io.github.dovehometeam.dovepack.Const;

import io.github.dovehometeam.dovepack.network.S2CPollutionPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 20:07:52}
 */
@EventBusSubscriber(modid = Const.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegisterEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

    }

    @SubscribeEvent
    public static void registerPacket(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(Const.MODID).versioned(ModList.get().getModFileById(Const.MODID).versionString()).optional();
        registrar.playToClient(S2CPollutionPacket.TYPE, S2CPollutionPacket.STREAM_CODEC, S2CPollutionPacket::handle);
    }
}
