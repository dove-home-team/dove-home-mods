package io.github.dovehometeam.dovepack.event;

import io.github.dovehometeam.dovelib.event.custom.FurnaceSlotResultEvent;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovepack.init.PackAttachments;
import io.github.dovehometeam.dovepack.init.PackComponents;
import io.github.dovehometeam.dovepack.init.PackItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent;


/**
 * @author baka4n
 * @code @Date 2025/9/23 15:27:55
 */
@EventBusSubscriber(modid = Const.MODID)
public class PlayerEvents {

    public static final ResourceKey<Level> DESERT_DIM
            = ResourceKey.create(Registries.DIMENSION,  io.github.dovehometeam.dovedim.Const.modLoc("desert_dim"));

    private static BlockPos getRandomizedSpawnPosition(ServerLevel level) {
        RandomSource random = level.random;
        BlockPos worldSpawn = level.getSharedSpawnPos();

        // 在世界出生点周围随机偏移
        int offsetX = random.nextInt(20000) - 10000; // -10000 到 10000
        int offsetZ = random.nextInt(20000) - 10000; // -10000 到 10000

        return worldSpawn.offset(offsetX, 0, offsetZ);
    }

    @SuppressWarnings("resource")
    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();

        if (!(player instanceof ServerPlayer serverPlayer)) return;
        ServerLevel serverLevel = serverPlayer.serverLevel();
        MinecraftServer server = serverLevel.getServer();
        if (!player.hasData(PackAttachments.FIRST_JOIN) || !player.getData(PackAttachments.FIRST_JOIN)) {
            ServerLevel desert = server.getLevel(DESERT_DIM);
            if (desert != null) {
                BlockPos randomPos = getRandomizedSpawnPosition(desert);
                serverPlayer.setRespawnPosition(
                        DESERT_DIM, randomPos, 0.0F, true, false
                );
                DimensionTransition transition = serverPlayer.findRespawnPositionAndUseSpawnBlock(
                        false,
                        DimensionTransition.DO_NOTHING
                );
                if (serverPlayer.level() == transition.newLevel()) {
                    Vec3 pos = transition.pos();
                    serverPlayer.connection.teleport(pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
                } else {
                    serverPlayer.changeDimension(transition);
                }
            }
        }
    }


    @SubscribeEvent
    public static void onFurnaceFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().is(PackItems.CACTUS_CHARCOAL)) {
            event.setBurnTime(2400);//仙人掌碳燃烧值
        }
    }

    @SubscribeEvent
    public static void onFurnaceSlotEvent(FurnaceSlotResultEvent event) {
        ItemStack input = event.getInput();
        ItemStack fuel = event.getFuel();
        if (input.is(Items.CACTUS)) {
            if (fuel.is(Items.CHARCOAL)) {
                double cactus_conversion = 100.00 / fuel.getCount();
                long defaultCactus = Double.doubleToLongBits(100);
                if (
                        Double.doubleToLongBits(cactus_conversion) == defaultCactus
                        || (fuel.has(PackComponents.CACTUS_CONVERSION_RATE) && Double.doubleToLongBits(fuel.getOrDefault(PackComponents.CACTUS_CONVERSION_RATE, 100.00) + cactus_conversion) >= defaultCactus)
                ) {
                    ItemStack stack = PackItems.CACTUS_CHARCOAL.asStack(fuel.getCount());
                    event.setFuel(stack);
                } else {
                    if (fuel.has(PackComponents.CACTUS_CONVERSION_RATE)) {
                        fuel.set(PackComponents.CACTUS_CONVERSION_RATE, fuel.getOrDefault(PackComponents.CACTUS_CONVERSION_RATE, 100.00) + cactus_conversion);
                        return;
                    }
                    fuel.set(PackComponents.CACTUS_CONVERSION_RATE, cactus_conversion);
                    event.setFuel(fuel);
                }
            }
        }
    }

}
