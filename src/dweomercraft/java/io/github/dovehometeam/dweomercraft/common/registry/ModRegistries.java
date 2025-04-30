package io.github.dovehometeam.dweomercraft.common.registry;

import io.github.dovehometeam.dweomercraft.Const;
import io.github.dovehometeam.dweomercraft.common.java.interfaces.ISpell;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 00:50:16}
 */
public class ModRegistries {
    public static final ResourceKey<Registry<ISpell>> SPELL_REGISTRY_KEY
            = ResourceKey.createRegistryKey(Const.modLoc("spell"));
    public static final  Registry<ISpell> SPELL_REGISTRY = new RegistryBuilder<>(SPELL_REGISTRY_KEY)
            .sync(true)
            .defaultKey(Const.modLoc("empty"))
            .maxId(1000)
            .create();

    public static void registerRegistry(NewRegistryEvent event) {
        event.register(SPELL_REGISTRY);
    }
}
