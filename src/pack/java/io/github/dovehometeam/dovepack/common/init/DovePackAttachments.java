package io.github.dovehometeam.dovepack.common.init;

import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.attachments.Pollution;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

/**
* @author : baka4n
* {@code @Date : 2025/05/06 23:09:39}
*/public class DovePackAttachments {
    public static final DeferredRegister<AttachmentType<?>> REGISTER =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, Const.MODID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Pollution>> POLLUTION =
            REGISTER.register("pollution", () -> AttachmentType.builder(Pollution::new).build());
    public static void init(IEventBus bus) {
        REGISTER.register(bus);
    }
}
