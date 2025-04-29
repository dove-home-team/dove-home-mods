package io.github.dovehometeam.mysticsadlibitum.common.init;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.PairMapCodec;
import io.github.dovehometeam.mysticsadlibitum.Const;
import io.github.dovehometeam.mysticsadlibitum.common.codec.PairDoubleCodec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 19:22:53}
 */
public class ModComponents {
    public static final DeferredRegister.DataComponents REGISTER =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Const.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> SHUFFLE =
            REGISTER.registerComponentType("shuffle", builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> SPELLS_CAST =
            REGISTER.registerComponentType("spells_cast", builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> CAST_DELAY =
            REGISTER.registerComponentType("cast_delay", builder ->
                    builder
                            .persistent(Codec.FLOAT)
                            .networkSynchronized(ByteBufCodecs.FLOAT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Float>> RECHRG_TIME =
            REGISTER.registerComponentType("rechrg_time", builder ->
                    builder
                            .persistent(Codec.FLOAT)
                            .networkSynchronized(ByteBufCodecs.FLOAT));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MANA =
            REGISTER.registerComponentType("mana", builder ->
                    builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MANA_MAX =
            REGISTER.registerComponentType("mana_max", builder ->
                    builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> MANA_CHG_SPD
            = REGISTER.registerComponentType("mana_chg_spd", builder ->
                    builder
                            .persistent(Codec.INT)
                            .networkSynchronized(ByteBufCodecs.INT));



    //Mana chg. Spd
}
