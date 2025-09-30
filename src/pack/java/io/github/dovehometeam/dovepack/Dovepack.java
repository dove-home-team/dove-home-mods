package io.github.dovehometeam.dovepack;

import com.tterrag.registrate.Registrate;
import io.github.dovehometeam.dovepack.init.PackComponents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import  io.github.dovehometeam.dovepack.init.PackItems;


@Mod(Const.MODID)
public class Dovepack {
    public static final Registrate REGISTRATE = Registrate.create(Const.MODID);
    public Dovepack(IEventBus modBus, ModContainer container) {
        PackComponents.init();
        PackItems.init();

    }
}