package io.github.dovehometeam.mysticsadlibitum.common.init;

import io.github.dovehometeam.mysticsadlibitum.Const;
import io.github.dovehometeam.mysticsadlibitum.common.items.SpellItem;
import io.github.dovehometeam.mysticsadlibitum.common.items.WandItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 19:15:05}
 */
public class ModItems {
    public static final DeferredRegister.Items REGISTER = DeferredRegister.createItems(Const.MODID);

    public static final DeferredItem<WandItem> WAND = REGISTER.register("wand",
                () -> new WandItem(new Item.Properties()));
    public static final DeferredItem<SpellItem> SPELL_RUNESTONE = REGISTER.register("spell_runestone",
            () -> new SpellItem(new Item.Properties()));//法术符文

}
