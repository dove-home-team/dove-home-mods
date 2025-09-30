package io.github.dovehometeam.dovelib.event.custom;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

/**
* @author baka4n
* @code @Date 2025/9/30 09:42:52
*/
@Getter
@Setter
public class FurnaceSlotResultEvent extends Event implements ICancellableEvent {

    private ItemStack input;//指定输入
    private ItemStack fuel;//燃料槽位
    private ItemStack output;//产出槽
    private boolean return_ = false;//是否直接返回

    public FurnaceSlotResultEvent(ItemStack input, ItemStack fuel, ItemStack output) {
        this.input = input;
        this.fuel = fuel;
        this.output = output;
    }
}
