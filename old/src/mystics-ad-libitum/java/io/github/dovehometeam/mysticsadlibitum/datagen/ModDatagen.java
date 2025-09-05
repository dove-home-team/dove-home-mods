package io.github.dovehometeam.mysticsadlibitum.datagen;


import io.github.dovehometeam.mysticsadlibitum.Const;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 19:41:05}
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = Const.MODID)
public class ModDatagen {
    @SubscribeEvent
    public static void onEvent(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        for (ModLanguage value : ModLanguage.values()) {
            generator.addProvider(event.includeClient(), value.apply(packOutput));
        }
    }
}
