package io.github.dovehometeam.doveact.mods.kubejs;

import dev.latvian.mods.kubejs.event.EventGroupRegistry;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;

/**
 * @author baka4n
 * @code @Date 2025/9/1 10:12:32
 */
public class KubeJsActPlugin implements KubeJSPlugin {

    @Override
    public void registerEvents(EventGroupRegistry registry) {
        registry.register(EventActGroups.GROUP);
    }
}
