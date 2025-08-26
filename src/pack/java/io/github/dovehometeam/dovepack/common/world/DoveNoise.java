package io.github.dovehometeam.dovepack.common.world;

import io.github.dovehometeam.dovepack.Const;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/26 14:43:50
 */
public enum DoveNoise implements Supplier<ResourceKey<NormalNoise.NoiseParameters>> {
    MOUNTAIN_NOISE,
    EROSION_NOISE,
    CONTINENT_NOISE,
    ;
    private final ResourceKey<NormalNoise.NoiseParameters> key;

    DoveNoise() {
        this.key = ResourceKey.create(Registries.NOISE, Const.modLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public ResourceKey<NormalNoise.NoiseParameters> get() {
        return key;
    }

    public static void bootstrapNoiseParameters(BootstrapContext<NormalNoise.NoiseParameters> context) {
        // 注册自定义噪声参数
        context.register(MOUNTAIN_NOISE.key, new NormalNoise.NoiseParameters(0, 1.0));
        context.register(EROSION_NOISE.key, new NormalNoise.NoiseParameters(1, 1.0));
        context.register(CONTINENT_NOISE.key, new NormalNoise.NoiseParameters(2, 1.0));
    }
}
