package io.github.dovehometeam.dovedim.event;

import io.github.dovehometeam.dovedim.Const;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Const.MODID)
public class DataEvents {
    @SubscribeEvent
    public static void gatherEvent(GatherDataEvent event) {

    }
}