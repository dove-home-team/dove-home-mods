package io.github.dovehometeam.dovepack.init;

import com.mojang.datafixers.DSL;
import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.dovehometeam.dovepack.common.be.FormationBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static io.github.dovehometeam.dovepack.Dovepack.REGISTRATE;

public class PackBlockEntities {
    public static final RegistryEntry<BlockEntityType<?>, BlockEntityType<FormationBlockEntity>> FORMATION =
            REGISTRATE.simple("formation", Registries.BLOCK_ENTITY_TYPE, () -> BlockEntityType.Builder
                    .of(FormationBlockEntity::new,
                            Blocks.DEAD_BUSH
                    )
                    .build(DSL.remainderType()));
    public static void init() {}
}
