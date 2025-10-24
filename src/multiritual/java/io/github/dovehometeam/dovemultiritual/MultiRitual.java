package io.github.dovehometeam.dovemultiritual;

import com.tterrag.registrate.Registrate;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Const.MODID)
public class MultiRitual {
    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);
    public MultiRitual(IEventBus bus, ModContainer mod) {

    }
}