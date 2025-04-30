package io.github.dovehometeam.mysticsadlibitum.common.java.interfaces;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


/**
 * @author : baka4n
 * {@code @Date : 2025/04/30 11:27:47}
 */
@FunctionalInterface
public interface ISpell{
    void castSpell(Level level, Player player, ItemStack stack);//释放法术效果
}
