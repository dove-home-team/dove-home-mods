package io.github.dovehometeam.dovepack.config;

import io.github.dovehometeam.dovepack.Const;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 18:08:00}
 */
@Config(name = Const.MODID)
public class DovePackConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public Fluids fluidSetting;

    @Comment("Stores a list of mod recipes that are not deleted")
    public List<String> reservedRecipe;

    public DovePackConfig() {
        fluidSetting = new Fluids();
        reservedRecipe = Arrays.asList("kubejs", "crafttweaker");
    }

    @Config(name = "fluid")
    public static class Fluids implements ConfigData {
        public String limitedFluids = "#c:water";
        public String infiniteFluids = "";
    }
}
