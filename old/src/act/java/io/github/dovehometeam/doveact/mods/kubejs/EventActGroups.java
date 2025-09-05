package io.github.dovehometeam.doveact.mods.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventHandler;

/**
 * @author baka4n
 * @code @Date 2025/9/3 23:58:19
 */
public class EventActGroups {
    public static EventGroup GROUP = EventGroup.of("Act");
    public static EventHandler REGISTRY = GROUP.common("registry", () -> RegistryActEventJs.class).hasResult();

}
