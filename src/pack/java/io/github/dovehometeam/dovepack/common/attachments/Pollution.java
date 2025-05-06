package io.github.dovehometeam.dovepack.common.attachments;

import io.github.dovehometeam.dovepack.common.init.DovePackAttachments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ChunkLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 23:13:12}
 */
public class Pollution implements INBTSerializable<CompoundTag> {

    private final Map<BlockPos, Float> pollutions = new HashMap<>();

    public Pollution() {

    }

    public static Pollution onlyGetAndCreate(BlockPos pos, Level level) {
        LevelChunk chunk = level.getChunkAt(pos);
        if (chunk.hasData(DovePackAttachments.POLLUTION)) {
            return chunk.getData(DovePackAttachments.POLLUTION);
        }
        Pollution data = new Pollution();
        chunk.setData(DovePackAttachments.POLLUTION, data);
        return data;
    }

    public static Pollution getOrInit(BlockPos pos, Level level) {
        var data = onlyGetAndCreate(pos, level);
        data.set(pos, data.get(pos, level));//0%~20%污染度初始化
        return data;
    }

    public void add(BlockPos pos, Level level, float pollution) {
        pollutions.put(pos, get(pos, level) + pollution);
    }

    public void set(BlockPos pos, float pollution) {
        pollutions.put(pos, pollution);
    }

    public void remove(BlockPos pos) {
        pollutions.remove(pos);
    }

    public float get(BlockPos pos, Level level) {
        return pollutions.getOrDefault(pos, level.getRandom().nextFloat() / 5);
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<BlockPos, Float> entry : pollutions.entrySet()) {
            BlockPos key = entry.getKey();
            float value = entry.getValue();
            tag.putFloat(key.getX() + "_" + key.getY() + "_" + key.getZ(), value);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        for (String key : tag.getAllKeys()) {
            String[] split = key.split("_", 3);
            if (split.length == 3) {
                pollutions.put(
                        new BlockPos(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])),
                        tag.getFloat(key));
            }
        }
    }
}
