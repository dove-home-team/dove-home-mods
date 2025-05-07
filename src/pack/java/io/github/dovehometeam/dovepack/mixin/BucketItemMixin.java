package io.github.dovehometeam.dovepack.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import io.github.dovehometeam.dovepack.common.attachments.Pollution;
import io.github.dovehometeam.dovepack.common.init.DovePackComponents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/07 00:35:48}
 */
@Mixin(BucketItem.class)
public class BucketItemMixin {

    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/BucketPickup;getPickupSound(Lnet/minecraft/world/level/block/state/BlockState;)Ljava/util/Optional;",
                    shift = At.Shift.AFTER
            )
    )
    private void useBucketNull(Level level,
                          Player player,
                          InteractionHand hand,
                          CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir,
                          @Local BlockHitResult blockhitresult,
                          @Local BlockState blockstate1,//water
                          @Local BucketPickup pickup
                          ) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (blockstate1.is(Blocks.WATER) && itemstack.is(Items.BUCKET)) {
            BlockPos blockpos = blockhitresult.getBlockPos();


        }
    }

}
