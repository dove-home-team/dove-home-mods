package io.github.dovehometeam.dovestaff.common.init;

import io.github.dovehometeam.dovestaff.Const;
import io.github.dovehometeam.dovestaff.common.items.StaffItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.math.BigDecimal;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/24 10:00:22}
 */
public class ModItems {
    public static final DeferredRegister.Items REGISTER =
            DeferredRegister.createItems(Const.MODID);
    public static final DeferredItem<StaffItem> WOOD_STAFF =
            REGISTER.register("wood_staff", () ->
                    new StaffItem(Tiers.WOOD, BigDecimal.ZERO));
    public static final DeferredItem<StaffItem> STONE_STAFF =
            REGISTER.register("stone_staff", () ->
                    new StaffItem(Tiers.STONE, BigDecimal.ZERO));
    public static final DeferredItem<StaffItem> IRON_STAFF =
            REGISTER.register("iron_staff", () ->
                    new StaffItem(Tiers.IRON, BigDecimal.ZERO));
    public static final DeferredItem<StaffItem> DIAMOND_STAFF =
            REGISTER.register("diamond_staff", () ->
                    new StaffItem(Tiers.DIAMOND, BigDecimal.ZERO));
    public static final DeferredItem<StaffItem> GOLD_STAFF =
            REGISTER.register("gold_staff", () ->
                    new StaffItem(Tiers.GOLD, BigDecimal.ZERO));
    public static final DeferredItem<StaffItem> NETHERITE_STAFF =
            REGISTER.register("netherite_staff", () ->
                    new StaffItem(Tiers.NETHERITE, BigDecimal.TEN));//蕴含着魔力

    public static void init() {}
}
