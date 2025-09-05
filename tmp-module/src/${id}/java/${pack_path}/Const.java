package ${pack};

import net.minecraft.resources.ResourceLocation;

import java.lang.String;

public class Const {
    public static final String MODID = "dove_${id}";

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static ResourceLocation cLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }
}