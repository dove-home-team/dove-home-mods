package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:43:50
 */
public enum DoveNoise implements Supplier<ResourceKey<NormalNoise.NoiseParameters>> {

    ;
    private final ResourceKey<NormalNoise.NoiseParameters> key;

    DoveNoise() {
        this.key = ResourceKey.create(Registries.NOISE, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<NormalNoise.NoiseParameters> get() {
        return key;
    }
}
