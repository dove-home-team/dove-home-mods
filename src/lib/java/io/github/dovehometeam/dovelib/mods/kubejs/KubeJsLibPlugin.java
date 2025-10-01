package io.github.dovehometeam.dovelib.mods.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;

/**
 * @author baka4n
 * @code @Date 2025/10/1 10:00:03
 */
public class KubeJsLibPlugin implements KubeJSPlugin {
    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(EventLibGroups.GROUP);
    }
}
