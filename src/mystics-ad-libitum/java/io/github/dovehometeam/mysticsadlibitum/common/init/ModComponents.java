package io.github.dovehometeam.mysticsadlibitum.common.init;

import io.github.dovehometeam.mysticsadlibitum.Const;
import io.github.dovehometeam.mysticsadlibitum.common.java.enums.Spell;
import io.github.dovehometeam.mysticsadlibitum.common.java.records.NoitaWandProperties;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 19:22:53}
 */
public class ModComponents {
    public static final DeferredRegister.DataComponents REGISTER =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Const.MODID);
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<NoitaWandProperties>> NOITA_BASE
            = REGISTER.registerComponentType("noita_base", builder -> builder
            .persistent(NoitaWandProperties.NoitaWandPropertiesCodec.INSTANCE.getCodec())
            .networkSynchronized(NoitaWandProperties.NoitaWandPropertiesCodec.INSTANCE.getStreamCodec()));
    public static final DeferredHolder<DataComponentType<?>,DataComponentType<Spell>> SPELL
            = REGISTER.registerComponentType("spell", builder -> builder
            .persistent(Spell.SpellCodec.INSTANCE.getCodec())
            .networkSynchronized(Spell.SpellCodec.INSTANCE.getStreamCodec()));


}
