package io.github.dovehometeam.dovepack.events;

import io.github.dovehometeam.base.annotation.event.GetArg;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.common.attachments.Pollution;
import io.github.dovehometeam.dovepack.common.init.DovePackAttachments;
import io.github.dovehometeam.dovepack.common.init.DovePackComponents;
import io.github.dovehometeam.dovepack.config.DovePackConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.block.CreateFluidSourceEvent;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 18:05:34}
 */
@EventBusSubscriber(modid = Const.MODID)
public class BlockEvents {

    @SubscribeEvent
    public static void setPlaceBlock(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player player) {
            BlockState placedBlock = event.getPlacedBlock();
            ItemStack main = player.getMainHandItem();
            ItemStack off = player.getOffhandItem();

            if (!placedBlock.is(Blocks.WATER)) return;
            float f;
            if (main.is(Items.WATER_BUCKET)) {
                f = main.getOrDefault(DovePackComponents.POLLUTION, 0.0F);
            } else {
                f = off.getOrDefault(DovePackComponents.POLLUTION, 0.0F);
            }
            Level level = (Level) event.getLevel();
            BlockPos pos = event.getPos();
            Pollution pollution = Pollution.onlyGetAndCreate(pos, level);
            pollution.set(pos, f);
            level.getChunkAt(pos).setData(DovePackAttachments.POLLUTION, pollution);
        }

    }

    @SubscribeEvent
    public static void sourceEvent(CreateFluidSourceEvent event) {
        FluidState fluidState = event.getFluidState();
        if (event.canConvert()) {
            DovePackConfig config = AutoConfig.getConfigHolder(DovePackConfig.class).getConfig();
            String[] split = config.fluidSetting.limitedFluids.split(",");

            for (String s : split) {
                if (s.startsWith("#")) {//tag check
                    TagKey<Fluid> fluidTagKey = TagKey.create(Registries.FLUID, ResourceLocation.parse(s.replace("#", "")));
                    if (fluidState.is(fluidTagKey)) {
                        event.setCanConvert(false);
                    }
                } else {
                    Fluid fluid = BuiltInRegistries.FLUID.get(ResourceLocation.parse(s));
                    if (fluidState.is(fluid)) {
                        event.setCanConvert(false);
                    }
                }
            }
        }
        else {
            DovePackConfig config = AutoConfig.getConfigHolder(DovePackConfig.class).getConfig();
            String[] split = config.fluidSetting.infiniteFluids.split(",");
            for (String s : split) {
                if (s.startsWith("#")) {//tag check
                    TagKey<Fluid> fluidTagKey = TagKey.create(Registries.FLUID, ResourceLocation.parse(s.replace("#", "")));
                    if (fluidState.is(fluidTagKey)) {
                        event.setCanConvert(true);
                    }
                } else {
                    Fluid fluid = BuiltInRegistries.FLUID.get(ResourceLocation.parse(s));
                    if (fluidState.is(fluid)) {
                        event.setCanConvert(true);
                    }
                }
            }
        }
    }
}
