package io.github.dovehometeam.dovepack.init;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static io.github.dovehometeam.dovepack.Dovepack.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/23 14:51:56
 */
public class PackItems {

    public static final ItemEntry<Item> CACTUS_CHARCOAL =
            REGISTRATE
                    .item("cactus_charcoal", Item::new)
                    .register();

    public static void init() {}
}
