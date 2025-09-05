package io.github.dovehometeam.dovecoremod;

import net.neoforged.fml.loading.moddiscovery.locators.JarInJarDependencyLocator;
import net.neoforged.neoforgespi.locating.IDependencyLocator;
import net.neoforged.neoforgespi.locating.IDiscoveryPipeline;
import net.neoforged.neoforgespi.locating.IModFile;

import java.util.List;

/**
 * @author baka4n
 * @code @Date 2025/8/25 09:03:37
 */
public class JarJarClassload implements IDependencyLocator {

    private static final String EMBEDDED_JARS_PATH = "META-INF/jars/";

    @Override
    public String toString() {
        return Const.MODID + "Load Mods Jar In Jar";
    }

    @Override
    public void scanMods(List<IModFile> loadedMods, IDiscoveryPipeline pipeline) {
        for (IModFile loadedMod : loadedMods) {
            System.out.println("baka4n: "+loadedMod.getFileName());
        }
    }
}
