package io.github.dovehometeam.dovepack.network;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.init.DovePackAttachments;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/07 04:53:14}
 */
@MethodsReturnNonnullByDefault
public record S2CPollutionPacket(ChunkPos chunkPos, Map<BlockPos, Float> pollutionMap) implements CustomPacketPayload {
    public static final Type<S2CPollutionPacket> TYPE = new Type<>(Const.modLoc("pollution"));
    public static final StreamCodec<RegistryFriendlyByteBuf, S2CPollutionPacket> STREAM_CODEC = CustomPacketPayload.codec(
            S2CPollutionPacket::write, S2CPollutionPacket::new
    );
    public S2CPollutionPacket(FriendlyByteBuf buf) {
        this(
                new ChunkPos(buf.readVarLong()),
                buf.readMap(byteBuf -> FriendlyByteBuf.readBlockPos(byteBuf), FriendlyByteBuf::readFloat)
        );
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeVarLong(chunkPos.toLong());
        buf.writeMap(pollutionMap,
                (b, p) -> FriendlyByteBuf.writeBlockPos(b, p),
                FriendlyByteBuf::writeFloat);
    }

    public static void handle(S2CPollutionPacket packet,IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                LevelChunk chunk = level.getChunk(packet.chunkPos.x, packet.chunkPos.z);
                Map<BlockPos, Float> data = chunk.getData(DovePackAttachments.POLLUTION);
                data.putAll(packet.pollutionMap);
            }
        });
    }


    public static void syncChunkPollution(ServerPlayer player, LevelChunk chunk) {
        Map<BlockPos, Float> data = chunk.getData(DovePackAttachments.POLLUTION);
        if (!data.isEmpty()) {
            player.connection.send(new S2CPollutionPacket(chunk.getPos(), data));
        }
    }

    public static void syncPollutionUpdate(ServerLevel level, BlockPos pos, float value) {
        LevelChunk chunk = level.getChunkAt(pos);
        for (ServerPlayer player : level.players()) {
            ChunkPos pos1 = chunk.getPos();
            if (player.chunkPosition().equals(pos1)) {
                player.connection.send(new S2CPollutionPacket(pos1, Map.of(pos, value)));
            }
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


}
