package io.github.dovehometeam.dovestaff.common.enums;

import io.github.dovehometeam.dovestaff.common.cap.ISpell;
import net.minecraft.util.StringRepresentable;

import java.math.BigDecimal;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 18:32:06}
 */

public enum Spell implements StringRepresentable {
    NONE,
    FIRE_BALL(20*20, (level, player, stack, mana) -> {

    }),//20 second
    ;
    public final BigDecimal HALF = BigDecimal.valueOf(0.5);
    public final int maxChargeTime;//tick
    public final BigDecimal coefficient;
    public final ISpell spell;
    Spell() {
        this.maxChargeTime = 20*20;
        spell = (level, player, stack, mana) -> {};
        this.coefficient = HALF;
    }

    Spell(int maxChargeTime, ISpell spell) {
        this.maxChargeTime = maxChargeTime;
        this.spell = spell;
        this.coefficient = HALF;
    }

    Spell(int maxChargeTime, ISpell spell, double coefficient) {
        this.maxChargeTime = maxChargeTime;
        this.spell = spell;
        this.coefficient = BigDecimal.valueOf(coefficient);
    }


    @Override
    public String getSerializedName() {
        return name();
    }
}
