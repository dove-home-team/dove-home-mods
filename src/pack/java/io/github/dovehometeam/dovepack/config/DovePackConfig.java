package io.github.dovehometeam.dovepack.config;

import io.github.dovehometeam.dovepack.Const;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 18:08:00}
 */
@Config(name = Const.MODID)
public class DovePackConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    public Fluids fluidSetting = new Fluids();

    @Config(name = "fluid")
    public static class Fluids implements ConfigData {
        public String limitedFluids = "#c:water";
        public String infiniteFluids = "";
    }
}
