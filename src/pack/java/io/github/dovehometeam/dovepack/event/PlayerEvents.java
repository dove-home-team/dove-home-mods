package io.github.dovehometeam.dovepack.event;

import io.github.dovehometeam.dovelib.event.custom.FurnaceSlotResultEvent;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.init.PackComponents;
import io.github.dovehometeam.dovepack.init.PackItems;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.FurnaceBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;


/**
 * @author baka4n
 * @code @Date 2025/9/23 15:27:55
 */
@EventBusSubscriber(modid = Const.MODID)
public class PlayerEvents {

    @SubscribeEvent
    public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(PackItems.CACTUS_CHARCOAL)) {
            event.setBurnTime(2400);//仙人掌碳燃烧值
        }
    }

    @SubscribeEvent
    public static void onFurnaceSlotEvent(FurnaceSlotResultEvent event) {
        ItemStack input = event.getInput();
        ItemStack fuel = event.getFuel();
        if (input.is(Items.CACTUS)) {
            if (fuel.is(Items.CHARCOAL)) {
                double cactus_conversion = 100.00 / fuel.getCount();
                long defaultCactus = Double.doubleToLongBits(100);
                if (
                        Double.doubleToLongBits(cactus_conversion) == defaultCactus
                        || (fuel.has(PackComponents.CACTUS_CONVERSION_RATE) && Double.doubleToLongBits(fuel.getOrDefault(PackComponents.CACTUS_CONVERSION_RATE, 100.00) + cactus_conversion) >= defaultCactus)
                ) {
                    ItemStack stack = PackItems.CACTUS_CHARCOAL.asStack(fuel.getCount());
                    event.setFuel(stack);
                } else {
                    if (fuel.has(PackComponents.CACTUS_CONVERSION_RATE)) {
                        fuel.set(PackComponents.CACTUS_CONVERSION_RATE, fuel.getOrDefault(PackComponents.CACTUS_CONVERSION_RATE, 100.00) + cactus_conversion);
                        return;
                    }
                    fuel.set(PackComponents.CACTUS_CONVERSION_RATE, cactus_conversion);
                    event.setFuel(fuel);
                }
            }
        }
    }

}
