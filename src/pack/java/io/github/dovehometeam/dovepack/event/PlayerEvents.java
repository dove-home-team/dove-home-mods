package io.github.dovehometeam.dovepack.event;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.init.ModAttachments;
import io.github.dovehometeam.dovepack.common.world.DoveLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
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
        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel)) return;
        MinecraftServer server = serverLevel.getServer();
        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (!player.hasData(ModAttachments.FIRST_JOIN)) {
            ServerLevel desert = server.getLevel(DoveLevel.DESERT_DIM.get());
            if (desert != null) {
                player.teleportTo(desert, 0, 70, 0, new HashSet<>(), player.getYRot(),player.getXRot());
                player.setData(ModAttachments.FIRST_JOIN, true);
                serverPlayer.setRespawnPosition(DoveLevel.DESERT_DIM.get(), new BlockPos(0, 70, 0), serverPlayer.getRespawnAngle(), true, false);
            }
        }
    }
}
