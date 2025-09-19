package io.github.dovehometeam.dovebotania.init;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import io.github.dovehometeam.dovebotania.Const;
import io.github.dovehometeam.dovebotania.common.block.BotaniaPetalApothecaryBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
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
    public static final List<BlockEntry<BotaniaPetalApothecaryBlock>> APOTHECARIES = new ArrayList<>();
    public static final BlockEntry<BotaniaPetalApothecaryBlock> SANDSTONE = petalDefaultRegister(
            "apothecary_sandstone",
            ResourceLocation.withDefaultNamespace("sandstone"),
            ResourceLocation.withDefaultNamespace("sandstone"),
            ResourceLocation.withDefaultNamespace("sandstone")
    );

    public static BlockEntry<BotaniaPetalApothecaryBlock> petalDefaultRegister(String name, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {

        BlockEntry<BotaniaPetalApothecaryBlock> register = REGISTRATE
                .block(name, BotaniaPetalApothecaryBlock::new)
                .initialProperties(() -> BotaniaBlocks.defaultAltar)
                .lang(RegistrateLangProvider.toEnglishName(name))
                .blockstate((ctx, prov) -> {
                    BlockModelBuilder model = prov
                            .models()
                            .withExistingParent(ctx.getName(), Const.modLoc("block/petal_apothecary_default"))
                            .texture("side", side.withPrefix("block/"))
                            .texture("bottom", bottom.withPrefix("block/"))
                            .texture("top", top.withPrefix("block/"))
                            .renderType(RenderType.cutout().name);
                    prov.simpleBlock(ctx.get(), model);
                })
                .item()
                .build()
                .register();
        APOTHECARIES.add(register);
        return register;

    }


    public static void init() {}
}
