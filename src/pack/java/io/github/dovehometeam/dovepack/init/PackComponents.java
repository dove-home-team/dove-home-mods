package io.github.dovehometeam.dovepack.init;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.dovehometeam.dovepack.Dovepack;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;

import static io.github.dovehometeam.dovepack.Dovepack.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/30 14:29:28
 */
public class PackComponents {
    public static final RegistryEntry<DataComponentType<?>, DataComponentType<Double>> CACTUS_CONVERSION_RATE = REGISTRATE
                .simple("cactus_conversion_rate", Registries.DATA_COMPONENT_TYPE,
                        () -> DataComponentType
                                .<Double>builder()
                                .persistent(Codec.DOUBLE)
                                .networkSynchronized(ByteBufCodecs.DOUBLE)
                                .build());
    public static void init() {}
}
