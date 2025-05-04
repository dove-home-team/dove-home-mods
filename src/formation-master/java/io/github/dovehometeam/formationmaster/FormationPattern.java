package io.github.dovehometeam.formationmaster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockPredicate;

import java.util.List;
import java.util.function.Supplier;

public enum FormationPattern {
    BASE(9, () -> Blocks.DEAD_BUSH, () -> Blocks.SAND,
            Circle._9x9_c);
    public final BlockPattern build;

    FormationPattern(int diameter, Supplier<Block> center, Supplier<Block> edge, String... patterns) {
        build = BlockPatternBuilder
                .start()
                .aisle(patterns)
                .where('B', BlockInWorld.hasState(BlockPredicate.forBlock(center.get())))
                .where('A', BlockInWorld.hasState(BlockPredicate.forBlock(edge.get())))
                .build();
    }


}
