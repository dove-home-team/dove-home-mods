package io.github.dovehometeam.doveact.init;

import com.tterrag.registrate.util.entry.MenuEntry;
import io.github.dovehometeam.doveact.client.menu.IronCraftingMenu;
import io.github.dovehometeam.doveact.client.menu.SandstoneCraftingMenu;
import io.github.dovehometeam.doveact.client.screen.IronCraftingScreen;
import io.github.dovehometeam.doveact.client.screen.SandstoneCraftingScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;

import static io.github.dovehometeam.doveact.DoveAct.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/1 10:41:52
 */
public class DoveActMenus {

    public static final MenuEntry<SandstoneCraftingMenu> SANDSTONE_CRAFTING = REGISTRATE.menu("sandstone_crafting",
            (type,
             windowId,
             inv,
             buf) -> {
                ContainerLevelAccess access = ContainerLevelAccess.NULL;
                if (buf != null) {
                    Level level = inv.player.level();
                    BlockPos decode = BlockPos.STREAM_CODEC.decode(buf);
                    access = ContainerLevelAccess.create(level, decode);
                }
                return new SandstoneCraftingMenu(windowId, inv, access);
            },
            () -> SandstoneCraftingScreen::new).register();
    public static final MenuEntry<IronCraftingMenu> IRON_CRAFTING = REGISTRATE.menu("iron_crafting",
            (type,
             windowId,
             inv,
             buf) -> {
                ContainerLevelAccess access = ContainerLevelAccess.NULL;
                if (buf != null) {
                    Level level = inv.player.level();
                    BlockPos decode = BlockPos.STREAM_CODEC.decode(buf);
                    access = ContainerLevelAccess.create(level, decode);
                }
                return new IronCraftingMenu(windowId, inv, access);
            },
            () -> IronCraftingScreen::new).register();

    public static void init() {}
}
