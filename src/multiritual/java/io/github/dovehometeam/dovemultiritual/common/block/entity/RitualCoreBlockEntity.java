package io.github.dovehometeam.dovemultiritual.common.block.entity;

import com.mojang.datafixers.util.Pair;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.common.asm.enumextension.NamedEnum;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RitualCoreBlockEntity extends BlockEntity {
    public Type type = Type.DEFAULT;
    public float rotateX = 0.0F, rotateY = 0.0F, rotateZ = 0.0F;
    public Map<Data, Float> dataRotateX = new HashMap<>();
    public Map<Data, Float> dataRotateY = new HashMap<>();
    public Map<Data, Float> dataRotateZ = new HashMap<>();
    public RitualCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        for (Data datum : this.type.data) {
            this.dataRotateX.put(datum, datum.rotateX);
            this.dataRotateY.put(datum, datum.rotateY);
            this.dataRotateZ.put(datum, datum.rotateZ);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("type", type.getSerializedName());

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.type = Type.fromString(tag.getString("type"));

    }

    public record Data(int argb,int segments, float radius,float thickness, Type type, float relaX, float relaY, float relaZ, float rotateX,float rotateY, float rotateZ, boolean isRunX, boolean isRunY, boolean isRunZ, boolean clockwiseX, boolean clockwiseY, boolean clockwiseZ) {
        public static Data square(int argb, float radius, float thickness, float relaX, float relaY, float relaZ, float rotateX,float rotateY, float rotateZ, boolean isRunX, boolean isRunY, boolean isRunZ, boolean clockwiseX, boolean clockwiseY, boolean clockwiseZ) {
            return new Data(argb, 4, radius, thickness, Type.SQUARE, relaX, relaY, relaZ, rotateX, rotateY, rotateZ, isRunX, isRunY, isRunZ, clockwiseX, clockwiseY, clockwiseZ);
        }



        public static Data triangle(int argb, float radius, float thickness, float relaX, float relaY, float relaZ, float rotateX,float rotateY, float rotateZ, boolean isRunX, boolean isRunY, boolean isRunZ, boolean clockwiseX, boolean clockwiseY, boolean clockwiseZ) {
            return new Data(argb, 3, radius, thickness, Type.TRIANGLE, relaX, relaY, relaZ, rotateX, rotateY, rotateZ, isRunX, isRunY, isRunZ, clockwiseX, clockwiseY, clockwiseZ);
        }

        public static Data pentagonal(int argb, float radius, float thickness, float relaX, float relaY, float relaZ, float rotateX,float rotateY, float rotateZ, boolean isRunX, boolean isRunY, boolean isRunZ, boolean clockwiseX, boolean clockwiseY, boolean clockwiseZ) {
            return new Data(argb, 5, radius, thickness, Type.TRIANGLE, relaX, relaY, relaZ, rotateX, rotateY, rotateZ, isRunX, isRunY, isRunZ, clockwiseX, clockwiseY, clockwiseZ);
        }

        public enum Type {
            RING,
            SQUARE,
            TRIANGLE,
            PENTAGONAL,
        }
    }


    @NamedEnum
    public enum Type implements StringRepresentable {
        DEFAULT("default",
                false,
                true,
                false,
                true,
                true,
                true,
                new Data(0xFFFC0CB, 256, 6, 0.3f, Data.Type.RING, 0, 0, 0, 0, 0, 0,false, false, false, true, true, true),
                new Data(0xFFFC0CB, 256, 5F, 0.3f, Data.Type.RING, 0, 0, 0, 0, 0, 0, false, false, false, true, true, true),
                Data.triangle(0xFFFC0CB, 5F, 0.3f, 0, 0, 0, 0, 0, 0,true, false, false, true, true, true),
                Data.triangle(0xFFFC0CB, 5F, 0.3f, 0, 0, 0, 45,180, 0, false, true, false, false, false, false)
        ),
        ;//基础法阵
        final String name;
        public final boolean isRunX, isRunY, isRunZ, clockwiseX, clockwiseY, clockwiseZ;
        public final Data[] data;
        Type(String name, boolean isRunX, boolean isRunY, boolean isRunZ, boolean clockwiseX, boolean clockwiseY, boolean clockwiseZ, Data... data) {
            this.isRunX = isRunX;
            this.isRunY = isRunY;
            this.isRunZ = isRunZ;
            this.name = name;
            this.data = data;
            this.clockwiseX = clockwiseX;
            this.clockwiseY = clockwiseY;
            this.clockwiseZ = clockwiseZ;

        }

        @Override
        public String getSerializedName() {
            return name;
        }

        public static Type fromString(String name) {
            for (Type value : Type.values()) {
                if (value.getSerializedName().equals(name)) {
                    return value;
                }
            }
            return DEFAULT;//基础类型
        }
    }
}
