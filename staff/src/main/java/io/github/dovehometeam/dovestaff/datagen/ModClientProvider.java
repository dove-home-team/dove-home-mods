package io.github.dovehometeam.dovestaff.datagen;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.util.concurrent.CompletableFuture;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/25 19:01:21}
 */
public class ModClientProvider implements DataProvider {
    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return CompletableFuture.allOf();
    }

    @Override
    public String getName() {
        return "Minecrft Client Datagen by baka4n";
    }
}
