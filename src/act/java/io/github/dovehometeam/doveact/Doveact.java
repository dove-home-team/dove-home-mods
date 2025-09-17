package io.github.dovehometeam.doveact;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import io.github.dovehometeam.doveact.common.block.ActCraftingTableBaseBlock;
import io.github.dovehometeam.doveact.init.DoveActBlocks;
import io.github.dovehometeam.doveact.init.DoveActMenus;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Const.MODID)
public class Doveact {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);
    public Doveact(IEventBus modBus, ModContainer container) {

        DoveActBlocks.init();
        DoveActMenus.init();
        REGISTRATE.addDataGenerator(ProviderType.LANG, prov -> {
            REGISTRATE.getAll(Registries.BLOCK)
                    .stream()
                    .filter(b -> b.get() instanceof ActCraftingTableBaseBlock)

                    .forEach(b -> {
                        prov.add(((ActCraftingTableBaseBlock)b.get()).key, RegistrateLangProvider.toEnglishName(b.getId().getPath()));
                    });
        });
    }
}