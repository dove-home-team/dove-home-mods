package {package_name};

import net.minecraft.resources.ResourceLocation;

import java.lang.String;

public class Const {
    public static final String MODID = "{modid}";

    public ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}