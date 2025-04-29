package io.github.dovehometeam.mysticsadlibitum.common.init;

import io.github.dovehometeam.mysticsadlibitum.Const;
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

    public static final DeferredItem<WandItem> STARTED_WAND = REGISTER.register("started_wand",
                () -> new WandItem(new Item.Properties(), false, 1, 0.15F, 0.25F, 0.33F, 0.47F, 80, 130));
    public static final DeferredItem<WandItem> STARTED_BOMB_WAND = REGISTER.register("started_bomb_wand",
                () -> new WandItem(new Item.Properties(), true, 1, 0.05F, 0.13F, 0.02F, 0.17F, 80, 110));

}
