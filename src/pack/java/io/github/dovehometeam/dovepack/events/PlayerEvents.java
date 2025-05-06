package io.github.dovehometeam.dovepack.events;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.init.DovePackComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/07 00:29:26}
 */
@EventBusSubscriber(modid = Const.MODID)
public class PlayerEvents {
    @SubscribeEvent
    public static void tooltipShow(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.has(DovePackComponents.POLLUTION)) {
            event.getToolTip().add(Component.literal(
                    String.valueOf(stack.getOrDefault(DovePackComponents.POLLUTION, 0.0F))
            ));
        }
    }
}
