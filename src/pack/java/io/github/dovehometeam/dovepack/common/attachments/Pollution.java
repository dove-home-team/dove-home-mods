package io.github.dovehometeam.dovepack.common.attachments;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.init.DovePackAttachments;
import io.github.dovehometeam.dovepack.network.S2CPollutionPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.*;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ChunkTicketLevelUpdatedEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 23:13:12}
 */
@EventBusSubscriber(modid = Const.MODID)
public class Pollution {
    public static float getPollution(Level level, BlockPos pos) {
        LevelChunk chunk = level.getChunkAt(pos);
        return chunk.getData(DovePackAttachments.POLLUTION).getOrDefault(pos, 0.0F);
    }

    public static void setPollution(ServerLevel level, BlockPos pos, float pollution) {
        float clamp = Mth.clamp(pollution, 0.0F, 1.0F);
        LevelChunk chunk = level.getChunkAt(pos);
        chunk.getData(DovePackAttachments.POLLUTION).put(pos, clamp);
        S2CPollutionPacket.syncPollutionUpdate(level, pos, clamp);
    }
    @SubscribeEvent
    public static void onChunkWatch(ChunkWatchEvent.Watch event) {
        ServerPlayer player = event.getPlayer();
        LevelChunk chunk = event.getChunk();
        S2CPollutionPacket.syncChunkPollution(player, chunk);
    }

    @SubscribeEvent
    public static void onBlockUpdate(BlockEvent.NeighborNotifyEvent event) {
        if (event.getState().is(Blocks.WATER) &&
        event.getLevel() instanceof ServerLevel serverLevel) {
            BlockPos pos = event.getPos();
            boolean neraLava = false;
            for (Direction value : Direction.values()) {
                if (serverLevel.getBlockState(pos.relative(value)).getBlock() == Blocks.LAVA) {
                    neraLava = true;
                    break;
                }
            }
            if (neraLava) {
                float pollution = getPollution(serverLevel, pos);
                setPollution(serverLevel, pos, pollution + 0.1F);
            }
        }
    }

    @SubscribeEvent
    public static void serverTick(ServerTickEvent.Post event) {

        for (ServerLevel level : event.getServer().getAllLevels()) {
            if (level.getGameTime() % 200 == 0) {
                processNaturalPurification(level);
            }
        }
    }
    static Method getChunks;
    static {

        try {
            getChunks = ChunkMap.class.getDeclaredMethod("getChunks");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        getChunks.setAccessible(true);
    }

    private static void processNaturalPurification(ServerLevel level) {
        try {

            Object invoke = getChunks.invoke(null);
            if (invoke instanceof Iterable<?> i) {
                for (Object o : i) {
                    if (o instanceof ChunkHolder holder && holder.getTickingChunk() != null) {
                        LevelChunk chunk = holder.getTickingChunk();
                        Map<BlockPos, Float> data = chunk.getData(DovePackAttachments.POLLUTION);
                        Map<BlockPos, Float> update = new HashMap<>();
                        data.forEach((pos, value) -> {
                            if (value > 0 && level.random.nextFloat() < 0.05F) {
                                float newValue = Math.max(0, value - 0.01F);
                                update.put(pos, newValue);
                            }
                        });
                        if (!update.isEmpty()) {
                            data.putAll(update);
                            update.forEach((pos, value) -> S2CPollutionPacket.syncPollutionUpdate(level, pos, value));
                        }
                    }
                }
            }
        } catch (Exception ignored) {}


    }

}
