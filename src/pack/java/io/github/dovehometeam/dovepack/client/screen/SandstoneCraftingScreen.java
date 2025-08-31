package io.github.dovehometeam.dovepack.client.screen;

import io.github.dovehometeam.dovelib.mods.vanilla.screen.DoveCraftingBaseScreen;
import io.github.dovehometeam.dovepack.client.menu.SandstoneCraftingMenu;
import io.github.dovehometeam.dovepack.common.block.SandstoneCraftingTableBlock;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * @author baka4n
 * @code @Date 2025/8/22 16:48:37
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SandstoneCraftingScreen extends DoveCraftingBaseScreen<SandstoneCraftingTableBlock, SandstoneCraftingMenu> {

    public SandstoneCraftingScreen(SandstoneCraftingMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }
}
