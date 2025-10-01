package io.github.dovehometeam.dovelib.mods.kubejs;

import dev.latvian.mods.kubejs.event.KubeEvent;
import dev.latvian.mods.kubejs.typings.Info;
import io.github.dovehometeam.dovelib.event.custom.FurnaceSlotResultEvent;
import net.minecraft.world.item.ItemStack;

/**
 * @author baka4n
 * @code @Date 2025/10/1 10:14:31
 */
@Info("Furnace Slot Result event")
public class FurnaceSlotResultEventJs implements KubeEvent {
    private final FurnaceSlotResultEvent event;


    public FurnaceSlotResultEventJs(FurnaceSlotResultEvent event) {
        this.event = event;
    }

    public ItemStack getInput() {
        return event.getInput();
    }

    public void setInput(ItemStack input) {
        event.setInput(input);
    }

    public ItemStack getOutput() {
        return event.getOutput();
    }

    public void setOutput(ItemStack output) {
        event.setOutput(output);
    }

    public ItemStack getFuel() {
        return event.getFuel();
    }

    public void setFuel(ItemStack fuel) {
        event.setFuel(fuel);
    }

    public boolean isReturn_() {
        return event.isReturn_();
    }

    public void setReturn_(boolean return_) {
        event.setReturn_(return_);
    }

}
