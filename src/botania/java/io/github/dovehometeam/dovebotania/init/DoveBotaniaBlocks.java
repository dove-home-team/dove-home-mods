package io.github.dovehometeam.dovebotania.init;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.dovebotania.Const;
import io.github.dovehometeam.dovebotania.common.block.BotaniaPetalApothecaryBlock;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import vazkii.botania.common.block.BotaniaBlocks;

import java.util.ArrayList;
import java.util.List;

import static io.github.dovehometeam.dovebotania.Dovebotania.REGISTRATE;

/**
 * @author baka4n
 * @code @Date 2025/9/5 14:18:20
 */
public class DoveBotaniaBlocks {
    public static final List<BlockEntry<BotaniaPetalApothecaryBlock>> APOTHECARIES =
            petalDefaultRegistries(
                    "apothecary_sandstone"
            );

    public static List<BlockEntry<BotaniaPetalApothecaryBlock>> petalDefaultRegistries(String... names) {
        ArrayList<BlockEntry<BotaniaPetalApothecaryBlock>> blockEntries = new ArrayList<>();
        for (String name : names) {
            blockEntries.add(petalDefaultRegister(name));
        }
        return blockEntries;
    }

    public static BlockEntry<BotaniaPetalApothecaryBlock> petalDefaultRegister(String name) {

        return REGISTRATE
                .block(name, BotaniaPetalApothecaryBlock::new)
                .initialProperties(() -> BotaniaBlocks.defaultAltar)
                .lang(RegistrateLangProvider.toEnglishName(name))
                .blockstate((ctx, prov) -> {
                    BlockModelBuilder model = prov
                            .models()
                            .withExistingParent(ctx.getName(), Const.modLoc("block/botania_petal_apothecary"))
                            .texture("side", Const.modLoc("block/" + name + "_side"))
                            .texture("bottom",  Const.modLoc("block/" + name + "_bottom"))
                            .texture("top",  Const.modLoc("block/" + name + "_top"))
                            .renderType(RenderType.cutout().name);
                    prov.simpleBlock(ctx.get(), model);
                })
                .item()
                .build()
                .register();

    }


    public static void init() {}
}
