package io.github.dovehometeam.dovepack.common.init;

import com.mojang.serialization.Codec;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.attachments.Pollution;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
* @author : baka4n
* {@code @Date : 2025/05/06 23:09:39}
*/public class DovePackComponents {
    public static final DeferredRegister.DataComponents REGISTER =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Const.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> POLLUTION =
            REGISTER.register("pollution", () -> DataComponentType.<Float>builder()
                    .persistent(Codec.FLOAT)
                    .networkSynchronized(ByteBufCodecs.FLOAT)
                    .build());
    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
