package io.github.dovehometeam.dovepack.client.menu;

import io.github.dovehometeam.dovepack.client.init.ModMenus;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;

/**
 * @author baka4n
 * @code @Date 2025/8/22 09:50:49
 */
@ParametersAreNonnullByDefault
public class SandstoneCraftingMenu extends RecipeBookMenu<CraftingInput, CraftingRecipe> {
    public static final int RESULT_SLOT = 0;
    private static final int CRAFT_SLOT_START = 1;
    private static final int CRAFT_SLOT_END = 10;
    private static final int INV_SLOT_START = 10;
    private static final int INV_SLOT_END = 37;
    private static final int USE_ROW_SLOT_START = 37;
    private static final int USE_ROW_SLOT_END = 46;
    public final CraftingContainer craftSlots;
    public final ResultContainer resultSlots;
    public final ContainerLevelAccess access;
    public final Player player;
    public boolean placingRecipe;

    public SandstoneCraftingMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    public SandstoneCraftingMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenus.SANDSTONE_CRAFTING.get(), containerId);
        this.craftSlots = new TransientCraftingContainer(this, 3, 3);
        this.resultSlots = new ResultContainer();
        this.access = access;
        this.player = playerInventory.player;
        this.addSlot(new ResultSlot(playerInventory.player, this.craftSlots, this.resultSlots, 0, 124, 35));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 3; ++j) {
                this.addSlot(new Slot(this.craftSlots, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for(int k = 0; k < 3; ++k) {
            for(int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for(int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }

    }

    protected static void slotChangedCraftingGrid(AbstractContainerMenu menu, Level level, Player player, CraftingContainer craftSlots, ResultContainer resultSlots, @Nullable RecipeHolder<CraftingRecipe> recipe) {
        if (!level.isClientSide) {
            CraftingInput craftinginput = craftSlots.asCraftInput();
            ServerPlayer serverplayer = (ServerPlayer)player;
            ItemStack itemstack = ItemStack.EMPTY;
            Optional<RecipeHolder<CraftingRecipe>> optional =
                    Objects.requireNonNull(level
                                    .getServer())
                            .getRecipeManager()
                            .getRecipeFor(RecipeType.CRAFTING, craftinginput, level, recipe)
                            .filter(holder -> holder.value().getGroup().equals("sandstone"));
            if (optional.isPresent()) {
                RecipeHolder<CraftingRecipe> recipeholder = optional.get();
                CraftingRecipe craftingrecipe = recipeholder.value();
                if (resultSlots.setRecipeUsed(level, serverplayer, recipeholder)) {
                    ItemStack itemstack1 = craftingrecipe.assemble(craftinginput, level.registryAccess());
                    if (itemstack1.isItemEnabled(level.enabledFeatures())) {
                        itemstack = itemstack1;
                    }
                }
            }

            resultSlots.setItem(0, itemstack);
            menu.setRemoteSlot(0, itemstack);
            serverplayer.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), 0, itemstack));
        }

    }

    public void slotsChanged(Container inventory) {
        if (!this.placingRecipe) {
            this.access.execute((p_344363_, p_344364_) -> slotChangedCraftingGrid(this, p_344363_, this.player, this.craftSlots, this.resultSlots, (RecipeHolder)null));
        }

    }

    public void beginPlacingRecipe() {
        this.placingRecipe = true;
    }

    public void finishPlacingRecipe(RecipeHolder<CraftingRecipe> recipe) {
        this.placingRecipe = false;
        this.access.execute((p_344361_, p_344362_) -> slotChangedCraftingGrid(this, p_344361_, this.player, this.craftSlots, this.resultSlots, recipe));
    }

    public void fillCraftSlotsStackedContents(StackedContents itemHelper) {
        this.craftSlots.fillStackedContents(itemHelper);
    }

    public void clearCraftingContent() {
        this.craftSlots.clearContent();
        this.resultSlots.clearContent();
    }

    public boolean recipeMatches(RecipeHolder<CraftingRecipe> recipe) {
        return recipe.value().matches(this.craftSlots.asCraftInput(), this.player.level());
    }

    public void removed(Player player) {
        super.removed(player);
        this.access.execute((p_39371_, p_39372_) -> this.clearContainer(player, this.craftSlots));
    }

    public boolean stillValid(Player player) {
        return stillValid(this.access, player, Blocks.CRAFTING_TABLE);
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == 0) {
                this.access.execute((p_39378_, p_39379_) -> itemstack1.getItem().onCraftedBy(itemstack1, p_39378_, player));
                if (!this.moveItemStackTo(itemstack1, 10, 46, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index >= 10 && index < 46) {
                if (!this.moveItemStackTo(itemstack1, 1, 10, false)) {
                    if (index < 37) {
                        if (!this.moveItemStackTo(itemstack1, 37, 46, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(itemstack1, 10, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(itemstack1, 10, 46, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
            if (index == 0) {
                player.drop(itemstack1, false);
            }
        }

        return itemstack;
    }

    public boolean canTakeItemForPickAll(ItemStack stack, Slot slot) {
        return slot.container != this.resultSlots && super.canTakeItemForPickAll(stack, slot);
    }

    public int getResultSlotIndex() {
        return 0;
    }

    public int getGridWidth() {
        return this.craftSlots.getWidth();
    }

    public int getGridHeight() {
        return this.craftSlots.getHeight();
    }

    public int getSize() {
        return 10;
    }

    public RecipeBookType getRecipeBookType() {
        return RecipeBookType.CRAFTING;
    }

    public boolean shouldMoveToInventory(int slotIndex) {
        return slotIndex != this.getResultSlotIndex();
    }
}
