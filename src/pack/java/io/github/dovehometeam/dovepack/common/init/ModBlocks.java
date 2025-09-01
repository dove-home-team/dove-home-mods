package io.github.dovehometeam.dovepack.common.init;

import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.dovepack.Const;
import io.github.dovehometeam.dovelib.mods.botania.block.DovePetalApothecaryBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import vazkii.botania.api.BotaniaRegistries;
import vazkii.botania.common.block.BotaniaBlocks;

import static io.github.dovehometeam.dovepack.DovePack.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/8/20 14:30:36
 */
public class ModBlocks {

    public static final BlockEntry<DovePetalApothecaryBlock> APOTHECARY_SANDSTONE;
    static {

        REGISTRATE.defaultCreativeTab(BotaniaRegistries.BOTANIA_TAB_KEY);
        {
            APOTHECARY_SANDSTONE = REGISTRATE.block("apothecary_sandstone", DovePetalApothecaryBlock::new)
                    .initialProperties(() -> BotaniaBlocks.defaultAltar)
                    .blockstate((ctx, prov) -> {
                        BlockModelBuilder model = prov
                                .models()
                                .withExistingParent(ctx.getName(), Const.modLoc("block/botania_petal_apothecary"))
                                .texture("side", Const.modLoc("block/botania/" +ctx.getName() + "_side"))
                                .texture("bottom",  Const.modLoc("block/botania/" +ctx.getName() + "_bottom"))
                                .texture("top",  Const.modLoc("block/botania/" +ctx.getName() + "_top"))
                                .renderType(RenderType.cutout().name);
                        prov.simpleBlock(ctx.get(), model);

                    })
                    .properties(p -> p.mapColor(MapColor.SAND).sound(SoundType.STONE))
                    .item()
                    .build()
                    .register();
        }
    }



    public static void init() {

    }

}
