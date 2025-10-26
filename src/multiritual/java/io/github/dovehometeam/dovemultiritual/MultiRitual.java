package io.github.dovehometeam.dovemultiritual;

import com.tterrag.registrate.Registrate;
import io.github.dovehometeam.dovemultiritual.common.init.MRBlocks;
import io.github.dovehometeam.dovemultiritual.common.init.MRRecipe;
import io.github.dovehometeam.dovemultiritual.common.init.MRRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Const.MODID)
public class MultiRitual {
    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);
    public MultiRitual(IEventBus bus, ModContainer mod) {
        MRRegistries.init();
        MRRecipe.init();
        MRBlocks.init();
    }
}