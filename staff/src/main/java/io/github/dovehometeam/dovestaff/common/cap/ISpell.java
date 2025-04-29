package io.github.dovehometeam.dovestaff.common.cap;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 19:24:19}
 */
@FunctionalInterface
public interface ISpell {
    void castSpell(Level level, Player player, ItemStack stack, BigDecimal mana);
}
