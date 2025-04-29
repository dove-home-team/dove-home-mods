package io.github.dovehometeam.dovestaff.common.init;

import com.mojang.serialization.Codec;
import io.github.dovehometeam.dovelib.codec.BigDecimalCodec;
import io.github.dovehometeam.dovestaff.Const;
import io.github.dovehometeam.dovestaff.common.codec.SpellCodec;
import io.github.dovehometeam.dovestaff.common.enums.Spell;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.math.BigDecimal;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 10:11:07}
 */
public class ModComponents {
    public static final DeferredRegister.DataComponents REGISTER =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Const.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BigDecimal>> MANA =
            REGISTER.registerComponentType("mana", bigDecimalBuilder -> bigDecimalBuilder
                    .persistent(BigDecimalCodec.INSTANCE.getCodec())
                    .networkSynchronized(BigDecimalCodec.INSTANCE.getStreamCodec()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BigDecimal>> MANA_CHARGE =
            REGISTER.registerComponentType("mana_charge", bigDecimalBuilder -> bigDecimalBuilder
                    .persistent(BigDecimalCodec.INSTANCE.getCodec())
                    .networkSynchronized(BigDecimalCodec.INSTANCE.getStreamCodec()));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> CD =
            REGISTER.registerComponentType("cd", builder -> builder
                    .persistent(Codec.INT)
                    .networkSynchronized(ByteBufCodecs.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Spell>> SPELLS =
            REGISTER.registerComponentType("spells", builder -> builder
                    .persistent(SpellCodec.INSTANCE.getCodec())
                    .networkSynchronized(SpellCodec.INSTANCE.getStreamCodec()));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Boolean>> CHARGED =
            REGISTER.registerComponentType("charged", builder -> builder
                    .persistent(Codec.BOOL)
                    .networkSynchronized(ByteBufCodecs.BOOL));
    public static void init() {}
}
