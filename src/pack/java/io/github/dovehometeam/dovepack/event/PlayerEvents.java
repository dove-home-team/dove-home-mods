package io.github.dovehometeam.dovepack.event;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.init.ModAttachments;
import io.github.dovehometeam.dovepack.common.world.DoveLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashSet;

/**
 * @author baka4n
 * @code @Date 2025/8/29 02:01:02
 */
@EventBusSubscriber(modid = Const.MODID)
public class PlayerEvents {
    @SuppressWarnings("resource")
    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (!(player instanceof ServerPlayer serverPlayer)) return;
        ServerLevel serverLevel = serverPlayer.serverLevel();
        MinecraftServer server = serverLevel.getServer();
        if (!player.hasData(ModAttachments.FIRST_JOIN) || !player.getData(ModAttachments.FIRST_JOIN)) {
            ServerLevel desert = server.getLevel(DoveLevel.DESERT_DIM.get());
            if (desert != null) {
                BlockPos randomPos = getRandomizedSpawnPosition(desert);
                serverPlayer.setRespawnPosition(
                        DoveLevel.DESERT_DIM.get(), randomPos, 0.0F, true, false
                );
                DimensionTransition transition = serverPlayer.findRespawnPositionAndUseSpawnBlock(
                        false,
                        DimensionTransition.DO_NOTHING
                );
                if (serverPlayer.level() == transition.newLevel()) {
                    Vec3 pos = transition.pos();
                    serverPlayer.connection.teleport(pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
                } else {
                    serverPlayer.changeDimension(transition);
                }
            }
        }
    }

    private static BlockPos getRandomizedSpawnPosition(ServerLevel level) {
        RandomSource random = level.random;
        BlockPos worldSpawn = level.getSharedSpawnPos();

        // 在世界出生点周围随机偏移
        int offsetX = random.nextInt(20000) - 10000; // -10000 到 10000
        int offsetZ = random.nextInt(20000) - 10000; // -10000 到 10000

        return worldSpawn.offset(offsetX, 0, offsetZ);
    }
}
