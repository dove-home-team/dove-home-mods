package io.github.dovehometeam.dovelib.mods.kubejs;

import dev.latvian.mods.kubejs.event.EventGroup;
import dev.latvian.mods.kubejs.event.EventResult;
import dev.latvian.mods.kubejs.event.EventTargetType;
import dev.latvian.mods.kubejs.event.TargetedEventHandler;
import dev.latvian.mods.kubejs.plugin.builtin.event.ItemEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * @author baka4n
 * @code @Date 2025/10/1 09:59:38
 */
public class EventLibGroups {
    public static final EventGroup GROUP = EventGroup.of("DoveLibs");

    public static final TargetedEventHandler<ResourceKey<Item>> FURNACE_SLOT_RESULT = GROUP.common("furnace_sloy_resuly", () -> FurnaceSlotResultEventJs.class)
            .supportsTarget(ItemEvents.TARGET);
}
