package io.github.dovehometeam.mysticsadlibitum.common.items;

import io.github.dovehometeam.mysticsadlibitum.common.init.ModComponents;
import io.github.dovehometeam.mysticsadlibitum.common.java.enums.Spell;
import io.github.dovehometeam.mysticsadlibitum.common.java.records.NoitaWandProperties;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;


import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 19:22:17}
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class WandItem extends Item {
    public WandItem(
            Properties properties

    ) {
        super(properties
                .component(DataComponents.CONTAINER, ItemContainerContents.EMPTY));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);

    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        super.releaseUsing(stack, level, livingEntity, timeCharged);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        NoitaWandProperties noitaBase = stack.getOrDefault(ModComponents.NOITA_BASE, NoitaWandProperties.DEFAULT);
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.shuffle.tooltip"))
                .append(noitaBase.shuffle() ? Component.translatable("mystics.ad.libitum.shuffle.yes")
                        : Component.translatable("mystics.ad.libitum.shuffle.no")));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.spells.cast.tooltip"))
                .append(String.valueOf(noitaBase.spells_cast())));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.cast.delay.tooltip"))
                .append(String.valueOf(noitaBase.castDelay())));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.rechrg.time.tooltip"))
                .append(String.valueOf(noitaBase.rechargeTime())));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.mana.tooltip"))
                .append("%s/%s".formatted(noitaBase.mana(), noitaBase.maxMana())));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.mana_chg.spd.tooltip"))
                .append(String.valueOf(noitaBase.manaChargeSpeed())));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.capacity.tooltip"))
                .append(String.valueOf(noitaBase.capacity())));
    }
}
