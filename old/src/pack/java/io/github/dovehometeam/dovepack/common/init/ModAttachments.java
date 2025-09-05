package io.github.dovehometeam.dovepack.common.init;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.dovehometeam.dovepack.DovePack;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
 * @author baka4n
 * @code @Date 2025/8/29 02:05:31
 */
public class ModAttachments {
    public static final RegistryEntry<AttachmentType<?>, AttachmentType<Boolean>> FIRST_JOIN;
    static {
        FIRST_JOIN = DovePack.REGISTRATE.simple("first_join", NeoForgeRegistries.Keys.ATTACHMENT_TYPES, () -> AttachmentType
                .builder(() -> false)
                .serialize(Codec.BOOL)
                .sync(ByteBufCodecs.BOOL)
                .build());
    }

    public static void init() {}
}
