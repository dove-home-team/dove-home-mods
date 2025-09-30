package io.github.dovehometeam.dovepack.mixin.accessors;

import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * @author baka4n
 * @code @Date 2025/9/27 11:55:53
 */
@Mixin(AbstractFurnaceBlockEntity.class)
public interface BurnTime {
    @Accessor("litTime")
    int getBurnTime();

    @Accessor("cookingProgress")
    int getCookingProgress();
}
