package io.github.dovehometeam.dweomercraft.common.java.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/01 00:48:28}
 */
@FunctionalInterface
public interface ISpell {
    void castSpell(Level level, Player player, ItemStack stack);
}
