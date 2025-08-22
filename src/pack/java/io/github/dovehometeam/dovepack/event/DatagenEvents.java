package io.github.dovehometeam.dovepack.event;

import io.github.dovehometeam.dovepack.Const;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * @author baka4n
 * @code @Date 2025/8/20 22:12:36
 */
@EventBusSubscriber(modid = Const.MODID)
public class DatagenEvents {
    @SubscribeEvent
    public static void gatherEvent(GatherDataEvent event) {

    }
}
