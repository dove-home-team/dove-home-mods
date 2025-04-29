package io.github.dovehometeam.dovestaff.common.init.tag;

import io.github.dovehometeam.dovestaff.Const;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.Locale;
import java.util.function.Supplier;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 13:50:00}
 */
public enum ModBlockTags implements Supplier<TagKey<Block>> {
    ,
    ;
    public final TagKey<Block> tag;
    ModBlockTags() {
        tag = BlockTags.create(Const.cLoc(name().toLowerCase(Locale.ROOT)));
    }

    @Override
    public TagKey<Block> get() {
        return tag;
    }
}
