package io.github.dovehometeam.doveact.common.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.doveact.client.menu.ActCraftingBaseMenu;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Supplier;

/**
 * @author baka4n
 * @code @Date 2025/8/23 10:01:03
 */
@SuppressWarnings("unchecked")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ActCraftingTableBaseBlock extends Block {


    @Getter
    private final Set<String> groups;
    public final String key;
    public final Component CONTAINER_TITLE;
    public static final MapCodec<ActCraftingTableBaseBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance
                    .group(
                            propertiesCodec(),
                            ResourceLocation.CODEC.fieldOf("menu").forGetter(b -> BuiltInRegistries.MENU.getKey(b.menu.get())),
                            Codec.STRING.listOf().fieldOf("groups").forGetter(b -> b.groups.stream().toList())
                    ).apply(instance, (p, menu, groups) -> new ActCraftingTableBaseBlock(p, () -> (MenuType<ActCraftingBaseMenu>) BuiltInRegistries.MENU.get(menu), groups.toArray(String[]::new)))
    );
    private final Supplier<MenuType<ActCraftingBaseMenu>> menu;

    public MapCodec<ActCraftingTableBaseBlock> codec() {
        return CODEC;
    }

    public ActCraftingBaseMenu menu(int containerId, Inventory playerInventory, Player player, Level level, BlockPos pos) {
        return new ActCraftingBaseMenu(menu.get(), containerId, playerInventory, ContainerLevelAccess.create(level, pos), () -> this);
    }

    public ActCraftingTableBaseBlock(Properties properties, Supplier<MenuType<ActCraftingBaseMenu>> menu, String... groups) {
        super(properties);
        this.groups = new HashSet<>(Arrays.asList(groups));
        this.menu = menu;
        key = "container.crafting." + groups[0];
        CONTAINER_TITLE = Component.translatable(key);
    }



    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return new SimpleMenuProvider((containerId,
                                       playerInventory,
                                       player) ->
               menu(containerId, playerInventory, player, level, pos),
                CONTAINER_TITLE);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            player.openMenu(state.getMenuProvider(level, pos));
            player.awardStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return InteractionResult.CONSUME;
        }
    }
}
