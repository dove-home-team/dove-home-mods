package io.github.dovehometeam.dovebotania;

import net.minecraft.resources.ResourceLocation;

import java.lang.String;

public class Const {
    public static final String MODID = "dove_botania";

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static ResourceLocation cLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }
}