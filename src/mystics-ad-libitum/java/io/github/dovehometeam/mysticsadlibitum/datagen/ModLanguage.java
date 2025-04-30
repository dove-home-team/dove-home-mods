package io.github.dovehometeam.mysticsadlibitum.datagen;

import io.github.dovehometeam.mysticsadlibitum.Const;
import io.github.dovehometeam.mysticsadlibitum.common.init.ModItems;
import io.github.dovehometeam.mysticsadlibitum.common.java.enums.Spell;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Locale;
import java.util.function.Consumer;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 19:47:34}
 */
public enum ModLanguage {
    EN_US(lang -> {
        lang.add("mystics.ad.libitum.shuffle.tooltip", "Shuffle: ");
        lang.add("mystics.ad.libitum.spells.cast.tooltip", "Spells/Cast: ");
        lang.add("mystics.ad.libitum.cast.delay.tooltip", "Cast Delay: ");
        lang.add("mystics.ad.libitum.rechrg.time.tooltip", "Rechrg. Time: ");
        lang.add("mystics.ad.libitum.mana.tooltip", "MANA/MANA Max: ");
        lang.add("mystics.ad.libitum.mana_chg.spd.tooltip", "Mana chg. Spd: ");
        lang.add("mystics.ad.libitum.capacity.tooltip", "Capacity: ");
        lang.add(ModItems.WAND.get(), "Wand");
        lang.add(ModItems.SPELL_RUNESTONE.get(), "Spell Runestone");
        lang.add(Spell.BASE.i18n(), "base");
    }),
    ZH_CN(lang -> {
        lang.add("mystics.ad.libitum.shuffle.tooltip", "乱序: ");
        lang.add("mystics.ad.libitum.spells.cast.tooltip", "法术数/释放: ");
        lang.add("mystics.ad.libitum.cast.delay.tooltip", "施法延迟: ");
        lang.add("mystics.ad.libitum.rechrg.time.tooltip", "充能延迟: ");
        lang.add("mystics.ad.libitum.mana.tooltip", "法力/最大法力值: ");
        lang.add("mystics.ad.libitum.mana_chg.spd.tooltip", "法力充能速度: ");
        lang.add("mystics.ad.libitum.capacity.tooltip", "容量: ");
        lang.add(ModItems.WAND.get(), "魔杖");
        lang.add(ModItems.SPELL_RUNESTONE.get(), "符石");
        lang.add(Spell.BASE.i18n(), "基础");
    }),
    ;
    private final Consumer<LanguageProvider> language;
    ModLanguage (Consumer<LanguageProvider> language) {
        this.language = language;
    }


    public LanguageProvider apply(PackOutput output) {
        return new LanguageProvider(output, Const.MODID, name().toLowerCase(Locale.ROOT)) {
            @Override
            protected void addTranslations() {
                language.accept(this);
            }
        };
    }
}
