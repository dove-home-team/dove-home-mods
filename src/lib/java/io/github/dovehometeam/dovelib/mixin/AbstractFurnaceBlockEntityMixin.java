package io.github.dovehometeam.dovelib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import io.github.dovehometeam.dovelib.event.custom.FurnaceSlotResultEvent;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.neoforged.neoforge.common.NeoForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author baka4n
 * @code @Date 2025/9/30 10:45:53
 */
@Mixin(AbstractFurnaceBlockEntity.class)
public class AbstractFurnaceBlockEntityMixin {
    @Inject(method = "burn", at= @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"), cancellable = true)
    private static void burn(RegistryAccess registryAccess,
                             RecipeHolder<?> recipe,
                             NonNullList<ItemStack> inventory,
                             int maxStackSize,
                             AbstractFurnaceBlockEntity furnace,
                             CallbackInfoReturnable<Boolean> cir,
                             @Local(name = "itemstack") LocalRef<ItemStack> input,
                             @Local(name = "itemstack2") LocalRef<ItemStack> result
    ) {
        FurnaceSlotResultEvent event = new FurnaceSlotResultEvent(input.get(), inventory.get(1), result.get());
        NeoForge.EVENT_BUS.post(event);
        input.set(event.getInput());
        result.set(event.getOutput());
        inventory.set(1, event.getFuel());
        if (event.isReturn_()) {
            cir.setReturnValue(true);
        }
    }
}
