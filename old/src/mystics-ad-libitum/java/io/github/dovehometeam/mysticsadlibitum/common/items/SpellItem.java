package io.github.dovehometeam.mysticsadlibitum.common.items;

import io.github.dovehometeam.mysticsadlibitum.common.init.ModComponents;
import io.github.dovehometeam.mysticsadlibitum.common.java.enums.Spell;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/30 11:26:52}
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SpellItem extends Item {

    public SpellItem(Properties properties) {
        super(properties.component(ModComponents.SPELL, Spell.BASE));
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.empty()
                .append(super.getName(stack))
                .append("[")
                .append(stack.getOrDefault(ModComponents.SPELL, Spell.BASE).translate())
                .append("]");
    }
}
