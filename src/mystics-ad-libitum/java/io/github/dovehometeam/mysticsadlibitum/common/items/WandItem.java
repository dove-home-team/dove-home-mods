package io.github.dovehometeam.mysticsadlibitum.common.items;

import io.github.dovehometeam.mysticsadlibitum.common.init.ModComponents;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
    public final float min_cast_delay, max_cast_delay;
    public final float min_rechrg_time, max_rechrg_time;
    public final int min_max_mana,max_mana;
    public final int min_mana_chg_spd, max_mana_chg_spd;
    public WandItem(
            Properties properties,
            boolean shuffle,
            int spells_cast,
            float min_cast_delay, float max_cast_delay,
            float min_rechrg_time, float max_rechrg_time,
            int min_max_mana, int max_mana,
            int min_mana_chg_spd, int max_mana_chg_spd
    ) {
        super(properties
                .component(ModComponents.SHUFFLE, shuffle)
                .component(ModComponents.SPELLS_CAST, spells_cast)
        );
        this.min_cast_delay = min_cast_delay;
        this.max_cast_delay = max_cast_delay;
        this.min_rechrg_time = min_rechrg_time;
        this.max_rechrg_time = max_rechrg_time;
        this.min_max_mana = min_max_mana;
        this.max_mana = max_mana;
        this.min_mana_chg_spd = min_mana_chg_spd;
        this.max_mana_chg_spd = max_mana_chg_spd;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if (!stack.has(ModComponents.CAST_DELAY)) {
            stack.set(ModComponents.CAST_DELAY, Mth.nextFloat(entity.getRandom(), min_cast_delay, max_cast_delay));
        }
        if (!stack.has(ModComponents.RECHRG_TIME)) {
            stack.set(ModComponents.RECHRG_TIME, Mth.nextFloat(entity.getRandom(), min_rechrg_time, max_rechrg_time));
        }
        if (!stack.has(ModComponents.MANA_MAX)) {
            stack.set(ModComponents.MANA_MAX, Mth.nextInt(entity.getRandom(), min_max_mana, max_mana));
            stack.set(ModComponents.MANA, 0);
        }
        if (!stack.has(ModComponents.MANA_CHG_SPD)) {
            stack.set(ModComponents.MANA_CHG_SPD, Mth.nextInt(entity.getRandom(), min_mana_chg_spd, max_mana_chg_spd));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.shuffle.tooltip"))
                .append(String.valueOf(stack.getOrDefault(ModComponents.SHUFFLE, false))));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.spells.cast.tooltip"))
                .append(String.valueOf(stack.getOrDefault(ModComponents.SPELLS_CAST, 1))));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.cast.delay.tooltip"))
                .append(String.valueOf(stack.getOrDefault(ModComponents.CAST_DELAY, min_cast_delay))));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.rechrg.time.tooltip"))
                .append(String.valueOf(stack.getOrDefault(ModComponents.RECHRG_TIME, min_rechrg_time))));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.mana.tooltip"))
                .append("%s/%s".formatted(stack.getOrDefault(ModComponents.MANA, 0), stack.getOrDefault(ModComponents.MANA_MAX, min_max_mana))));
        components.add(Component.empty()
                .append(Component.translatable("mystics.ad.libitum.mana_chg.spd.tooltip"))
                .append(String.valueOf(stack.getOrDefault(ModComponents.MANA_CHG_SPD, min_mana_chg_spd))));
    }
}
