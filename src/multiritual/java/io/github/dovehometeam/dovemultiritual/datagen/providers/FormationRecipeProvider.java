package io.github.dovehometeam.dovemultiritual.datagen.providers;

import com.google.gson.JsonElement;
import io.github.dovehometeam.dovemultiritual.common.recipe.FormationRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FormationRecipeProvider implements DataProvider {
    private final String modId;
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;

    private final Map<ResourceLocation, Build> builds = new HashMap<>();

    public FormationRecipeProvider(String modId, PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        this.modId = modId;
        this.output = output;
        this.registries = registries;
    }

    public void init() {

    }

    public FormationRecipeProvider add(ResourceLocation name, Consumer<Build> builder) {
        Build t = new Build();
        builder.accept(t);
        builds.put(name, t);
        return this;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        builds.clear();
        init();

        return null;
    }

    @Override
    public String getName() {
        return "Formation Recipe";
    }

    public static class Build {
        public BlockState result;
        public Map<Character, BlockState> blocks;
        public List<List<String>> layers_s;
        public static Build builder() {
            return new Build();
        }

        public Build result(BlockState result) {
            this.result = result;
            return this;
        }

        public Build charSet(Character c, BlockState block) {
            blocks.put(c, block);
            return this;
        }

        public Build lastLayer(String layer) {
            layers_s.getLast().add(layer);
            return this;
        }

        public Build newLayer(String layer) {
            ArrayList<String> e = new ArrayList<>();
            e.add(layer);
            layers_s.add(e);
            return this;
        }
    }
}
