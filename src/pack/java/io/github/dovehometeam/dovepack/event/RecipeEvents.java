package io.github.dovehometeam.dovepack.event;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.config.DovePackConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Const.MODID)
public class RecipeEvents {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeEvents.class);

    @SuppressWarnings("CodeBlock2Expr")
    @SubscribeEvent
    public static void recipesReload(AddReloadListenerEvent event) {

        event.addListener((barrier,
                           manager,
                           filler,
                           reloadFilter,
                           backgroundExecutor,
                           gameExecutor) -> {

            return CompletableFuture.runAsync(() -> {
                filler.push("dove_recpie_preparation");
            }, backgroundExecutor).thenCompose(barrier::wait).thenRunAsync(() -> {
                reloadFilter.push("dove_recpie_removal");
                removeRecipes(event.getServerResources());
                reloadFilter.pop();
            }, gameExecutor);
        });
    }

    private static void removeRecipes(ReloadableServerResources server) {
        try {
            RecipeManager recipeManager = server.getRecipeManager();
            Collection<RecipeHolder<?>> allRecipes = recipeManager.getRecipes();
            List<RecipeHolder<?>> filterRecipes = new ArrayList<>();
            int totalRemoved = 0;
            int totalKept = 0;
            for (var recipeHolder : allRecipes) {
                ResourceLocation id = recipeHolder.id();
                if (shouldKeepRecipe(id)) {
                    filterRecipes.add(recipeHolder);
                    totalKept++;
                } else {
                    totalRemoved++;
                }
            }
            recipeManager.replaceRecipes(filterRecipes);
            LOGGER.info("The formula is filtered, Remove {} recipes, Retain {} recipes.", totalRemoved, totalKept);

        } catch (Exception e) {
            LOGGER.error("Failed to remove all recipes", e);
        }
    }

    private static boolean shouldKeepRecipe(ResourceLocation recipeId) {
        String namespace = recipeId.getNamespace();

        var dovePack = AutoConfig.getConfigHolder(DovePackConfig.class).getConfig();
        // 保留白名单模组的配方
        if (dovePack.reservedRecipe.contains(namespace)) {
            return true;
        }

        // 保留包含"dove"字符串的配方
        return namespace.contains("dove");

        // 移除其他所有配方
    }
}
