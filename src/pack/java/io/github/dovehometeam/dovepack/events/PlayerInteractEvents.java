package io.github.dovehometeam.dovepack.events;


import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.attachments.Pollution;
import io.github.dovehometeam.dovepack.common.init.DovePackComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 19:30:18}
 */
// @EventBusSubscriber(modid = Const.MODID)
public class PlayerInteractEvents {
//     @SubscribeEvent
//     public static void playerInteractEvent(PlayerInteractEvent.RightClickBlock event) {
//
//        Level level = event.getLevel();
//        BlockPos pos = event.getPos();
//        ItemStack stack = event.getItemStack();
//        if (!stack.is(Items.BUCKET)) return;
//        BlockState state = level.getBlockState(pos);
//        if (!state.is(Blocks.WATER)) return;
//        LevelChunk chunkAt = level.getChunkAt(pos);
//        Pollution data = Pollution.getOrInit(chunkAt, pos, level);
//        float v = data.get(pos);
//        ItemStack newStack = event.getEntity().getItemInHand(event.getHand());
//        if (newStack.is(Items.WATER_BUCKET)) {
//            newStack.set(DovePackComponents.POLLUTION, v);
//        }
//     }
}
