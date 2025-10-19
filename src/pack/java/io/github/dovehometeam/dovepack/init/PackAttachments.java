package io.github.dovehometeam.dovepack.init;

import com.mojang.serialization.Codec;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import static io.github.dovehometeam.dovepack.Dovepack.REGISTRATE;


public class PackAttachments {
    public static final RegistryEntry<AttachmentType<?>, AttachmentType<Boolean>> FIRST_JOIN =
            REGISTRATE.simple("first_join", NeoForgeRegistries.Keys.ATTACHMENT_TYPES, () -> AttachmentType
                    .builder(() -> false)
                    .serialize(Codec.BOOL)
                    .sync(ByteBufCodecs.BOOL)
                    .build());;
    public static void init() {}
}
