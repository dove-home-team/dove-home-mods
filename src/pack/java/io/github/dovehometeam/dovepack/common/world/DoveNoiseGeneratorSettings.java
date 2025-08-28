package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.*;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:39:16
 */
public enum DoveNoiseGeneratorSettings implements Supplier<ResourceKey<NoiseGeneratorSettings>> {
    DESERT_NOISE;
    private final ResourceKey<NoiseGeneratorSettings> key;

    DoveNoiseGeneratorSettings() {
        this.key = ResourceKey.create(Registries.NOISE_SETTINGS, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<NoiseGeneratorSettings> get() {
        return key;
    }

}


