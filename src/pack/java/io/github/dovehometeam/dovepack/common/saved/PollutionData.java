package io.github.dovehometeam.dovepack.common.saved;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

/**
 * @author : baka4n
 * {@code @Date : 2025/05/06 23:02:07}
 */
public class PollutionData extends SavedData {

    public static PollutionData create() {
        return new PollutionData();
    }

    public static PollutionData load(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        PollutionData data = PollutionData.create();
        // Load saved data
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        return compoundTag;
    }
}
