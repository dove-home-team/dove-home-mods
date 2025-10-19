package io.github.dovehometeam.dovepack.config;

import io.github.dovehometeam.dovepack.Const;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

import java.util.Arrays;
import java.util.List;

@Config(name = Const.MODID)
public class DovePackConfig implements ConfigData {
    @Comment("Stores a list of mod recipes that are not deleted")
    public List<String> reservedRecipe =
            Arrays.asList("kubejs", "crafttweaker");

}
