package io.github.dovehometeam.dovepack.common.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.attachments.Pollution;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
* @author : baka4n
* {@code @Date : 2025/05/06 23:09:39}
*/public class DovePackAttachments {
    public static final DeferredRegister<AttachmentType<?>> REGISTER =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Const.MODID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Map<BlockPos, Float>>> POLLUTION =
            REGISTER.register("pollution", () -> AttachmentType.
                    <Map<BlockPos, Float>>builder(() -> new HashMap<>())
                    .serialize(new IAttachmentSerializer<CompoundTag, Map<BlockPos, Float>>() {
                        @Override
                        public Map<BlockPos, Float> read(IAttachmentHolder iAttachmentHolder, CompoundTag tag, HolderLookup.Provider provider) {
                            Map<BlockPos, Float> map = new HashMap<>();
                            ListTag positions = tag.getList("Positions", Tag.TAG_LONG);
                            ListTag values = tag.getList("Values", Tag.TAG_FLOAT);
                            if (positions.size() != values.size()) {
                                throw new IllegalArgumentException("positions and values must be the same size");
                            }
                            for (int i = 0; i < positions.size(); i++) {
                                long asLong = ((LongTag) positions.get(i)).getAsLong();
                                float asFloat = ((FloatTag) values.get(i)).getAsFloat();
                                map.put(BlockPos.of(asLong), asFloat);
                            }
                            return map;
                        }

                        @Override
                        public CompoundTag write(Map<BlockPos, Float> blockPosFloatMap, HolderLookup.Provider provider) {
                            CompoundTag tag = new CompoundTag();
                            ListTag positions = new ListTag();
                            ListTag values = new ListTag();
                            blockPosFloatMap.forEach((pos, pollution) -> {
                                positions.add(LongTag.valueOf(pos.asLong()));
                                values.add(FloatTag.valueOf(pollution));
                            });
                            tag.put("positions", positions);
                            tag.put("values", values);
                            return tag;
                        }
                    })
                    .build());

    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
