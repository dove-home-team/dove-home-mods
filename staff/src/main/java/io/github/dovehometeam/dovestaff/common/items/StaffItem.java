package io.github.dovehometeam.dovestaff.common.items;

import io.github.dovehometeam.dovestaff.common.enums.Spell;
import io.github.dovehometeam.dovestaff.common.init.ModComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.math.BigDecimal;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 10:03:27}
 */
public class StaffItem extends TieredItem {
    public StaffItem(Tier tier, BigDecimal DEFAULT_MANA) {
        super(tier, new Properties()
                .component(ModComponents.MANA, DEFAULT_MANA)
                .component(ModComponents.CD, 0)
                .component(ModComponents.SPELLS, Spell.NONE)
                .component(ModComponents.MANA_CHARGE, BigDecimal.ZERO)
        );
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!(entity instanceof Player player)) return;
        int cd = stack.getOrDefault(ModComponents.CD, 0);
        if (cd > 0) {
            stack.set(ModComponents.CD, cd - 1);
        }
        if (player.getTicksUsingItem() == 0) {
            BigDecimal mana_charge = stack.getOrDefault(ModComponents.MANA_CHARGE, BigDecimal.ZERO);
            if (mana_charge.compareTo(BigDecimal.ZERO) <= 0) return;
            BigDecimal sub = BigDecimal.valueOf(player.getRandom().nextDouble() * 5);
            BigDecimal mana = stack.getOrDefault(ModComponents.MANA, BigDecimal.ZERO);
            Spell spell = stack.getOrDefault(ModComponents.SPELLS, Spell.NONE);
            if (mana_charge.compareTo(sub) <= 0) {
                stack.set(ModComponents.MANA, mana.add(mana_charge.multiply(spell.coefficient)));
                stack.set(ModComponents.MANA_CHARGE, BigDecimal.ZERO);
            } else {
                stack.set(ModComponents.MANA, mana.add(sub.multiply(spell.coefficient)));
                stack.set(ModComponents.MANA_CHARGE, mana_charge.subtract(sub));
            }
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return stack.getOrDefault(ModComponents.SPELLS, Spell.NONE).maxChargeTime;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (stack.getOrDefault(ModComponents.CD, 0) != 0) {
            return InteractionResultHolder.fail(stack);
        }
        if (usedHand == InteractionHand.MAIN_HAND) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(stack);
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!(livingEntity instanceof Player player)) return;
        Spell spell = stack.getOrDefault(ModComponents.SPELLS, Spell.NONE);
        if (remainingUseDuration == 0) return;
        if (remainingUseDuration % 20 == 19){
            BigDecimal mana = stack.getOrDefault(ModComponents.MANA, BigDecimal.ZERO);
            if (mana.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal mana_charge = stack.getOrDefault(ModComponents.MANA_CHARGE, BigDecimal.ZERO);

                BigDecimal add = BigDecimal.valueOf(player.getRandom().nextDouble() * 5);
                if (add.compareTo(mana) < 0) {
                    stack.set(ModComponents.MANA, mana.subtract(add));
                    stack.set(ModComponents.MANA_CHARGE, mana_charge.add(add));
                } else {
                    stack.set(ModComponents.MANA_CHARGE, mana);
                    stack.set(ModComponents.MANA, BigDecimal.ZERO);
                }
            } else {
                BigDecimal mana_charge = stack.getOrDefault(ModComponents.MANA_CHARGE, BigDecimal.ZERO);
                stack.setDamageValue(stack.getDamageValue() + 1);
                stack.set(ModComponents.MANA_CHARGE, mana_charge.add(spell.coefficient));
            }

        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (!(livingEntity instanceof Player player)) return;
        Spell spell = stack.getOrDefault(ModComponents.SPELLS, Spell.NONE);
        spell.spell.castSpell(level, player, stack, stack.getOrDefault(ModComponents.MANA_CHARGE, BigDecimal.ZERO));
        stack.set(ModComponents.MANA_CHARGE, BigDecimal.ZERO);
    }
}
