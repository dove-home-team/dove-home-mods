package io.github.dovehometeam.dovepack.client.init;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.MenuBuilder;
import com.tterrag.registrate.util.entry.MenuEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import io.github.dovehometeam.dovepack.DovePack;
import io.github.dovehometeam.dovepack.client.menu.SandstoneCraftingMenu;
import io.github.dovehometeam.dovepack.client.screen.SandstoneCraftingScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;

/**
 * @author baka4n
 * @code @Date 2025/8/22 10:50:50
 */
public class ModMenus {
    public static final
    MenuEntry<SandstoneCraftingMenu> SANDSTONE_CRAFTING = DovePack.REGISTRATE.menu("sandstone_crafting",
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
    public static void init() {}

}
