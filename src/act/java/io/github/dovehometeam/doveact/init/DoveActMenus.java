package io.github.dovehometeam.doveact.init;

import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.MenuEntry;
import io.github.dovehometeam.doveact.client.menu.*;
import io.github.dovehometeam.doveact.client.screen.ActCraftingBaseScreen;
import io.github.dovehometeam.doveact.common.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;

import static io.github.dovehometeam.doveact.Doveact.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/1 10:41:52
 */
public class DoveActMenus {

    public static final MenuEntry<ActCraftingBaseMenu> SANDSTONE_CRAFTING =
            registerAct("sandstone_crafting", DoveActBlocks.SANDSTONE_CRAFTING_TABLE);
    public static final MenuEntry<ActCraftingBaseMenu> IRON_CRAFTING =
            registerAct("iron_crafting", DoveActBlocks.IRON_CRAFTING_TABLE);
    public static final MenuEntry<ActCraftingBaseMenu> GOLD_CRAFTING =
            registerAct("gold_crafting", DoveActBlocks.GOLD_CRAFTING_TABLE);
     public static final MenuEntry<ActCraftingBaseMenu> DIAMOND_CRAFTING =
            registerAct("diamond_crafting", DoveActBlocks.DIAMOND_CRAFTING_TABLE);
     public static final MenuEntry<ActCraftingBaseMenu> STONE_CRAFTING =
             registerAct("stone_crafting", DoveActBlocks.STONE_CRAFTING_TABLE);
     public static final MenuEntry<ActCraftingBaseMenu> GLOWSTONE_CRAFTING =
             registerAct("glowstone_crafting", DoveActBlocks.GLOWSTONE_CRAFTING_TABLE);

    public static
    MenuEntry<ActCraftingBaseMenu> registerAct(
            String name,
            BlockEntry<ActCraftingTableBaseBlock> block
    ) {
        return REGISTRATE.menu(
                name,
                (type, windowId, inv, buf) -> {
                    ContainerLevelAccess access = ContainerLevelAccess.NULL;
                    if (buf != null) {
                        Level level = inv.player.level();
                        BlockPos decode = BlockPos.STREAM_CODEC.decode(buf);
                        access = ContainerLevelAccess.create(level, decode);
                    }
                    return new ActCraftingBaseMenu((MenuType<ActCraftingBaseMenu>) REGISTRATE.get(name, Registries.MENU).get(), windowId, inv, access, block);
                },
                () ->ActCraftingBaseScreen<ActCraftingTableBaseBlock, ActCraftingBaseMenu>::new
        ).register();
    }

    public static void init() {}
}
