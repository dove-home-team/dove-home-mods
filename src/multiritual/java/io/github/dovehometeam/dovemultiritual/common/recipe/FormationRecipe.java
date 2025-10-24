package io.github.dovehometeam.dovemultiritual.common.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.chars.CharArrayList;
import it.unimi.dsi.fastutil.chars.CharArraySet;
import it.unimi.dsi.fastutil.chars.CharSet;
import it.unimi.dsi.fastutil.chars.CharSets;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.github.dovehometeam.dovemultiritual.common.recipe.FormationRecipe.Data.CENTER;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FormationRecipe {

    @Getter
    private final BlockState result;
    @Getter
    private final BlockState center;
    @Getter
    private final Map<Vec3i, BlockState> blocks;

    private final Optional<Data> data;

    public static final MapCodec<FormationRecipe> MAP_CODEC = Data.MAP_CODEC.flatXmap(
            FormationRecipe::unpack,
            formation -> formation.data.map(DataResult::success).orElseGet(() -> DataResult.error(() -> "Cannot encode unpacked formation recipe"))
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BlockState> BLOCK_STATE_STREAM_CODEC = StreamCodec.ofMember(
            (state, buf) -> {
                ResourceLocation.STREAM_CODEC.encode(buf, BuiltInRegistries.BLOCK.getKey(state.getBlock()));
                BlockItemStateProperties empty = BlockItemStateProperties.EMPTY;
                state.getProperties().forEach(property -> empty.with(property, state));
                BlockItemStateProperties.STREAM_CODEC.encode(buf, empty);
            },
            buf -> {

                BlockState blockState = BuiltInRegistries.BLOCK.get(ResourceLocation.STREAM_CODEC.decode(buf)).defaultBlockState();
                BlockItemStateProperties.STREAM_CODEC.decode(buf).apply(blockState);
                return blockState;
            }
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, FormationRecipe> STREAM_CODEC = StreamCodec.ofMember(
            (formation, buf) -> {
                BLOCK_STATE_STREAM_CODEC.encode(buf, formation.center);
                BLOCK_STATE_STREAM_CODEC.encode(buf, formation.result);
                Map<Vec3i, BlockState> blocks = formation.blocks;
                buf.writeVarInt(blocks.size());
                for (Map.Entry<Vec3i, BlockState> entry : blocks.entrySet()) {
                    Vec3i vec3 = entry.getKey();
                    buf.writeInt(vec3.getX());
                    buf.writeInt(vec3.getY());
                    buf.writeInt(vec3.getZ());
                    BLOCK_STATE_STREAM_CODEC.encode(buf, entry.getValue());
                }



            },
            buf -> {
                BlockState center = BLOCK_STATE_STREAM_CODEC.decode(buf);
                BlockState result = BLOCK_STATE_STREAM_CODEC.decode(buf);
                int size = buf.readVarInt();
                Map<Vec3i, BlockState> blocks = HashMap.newHashMap(size);
                for (int i = 0; i < size; i++) {
                    Vec3i vec3i = new Vec3i(buf.readInt(), buf.readInt(), buf.readInt());
                    blocks.put(vec3i, BLOCK_STATE_STREAM_CODEC.decode(buf));
                }
                return new FormationRecipe(center, result, blocks, Optional.empty());
            }
    );

    private static DataResult<? extends FormationRecipe> unpack(Data data) {
        return DataResult.success(data.build());
    }

    public FormationRecipe(BlockState center, BlockState result, Map<Vec3i, BlockState> blocks, Optional<Data> data) {
        this.blocks = blocks;
        this.center = center;
        this.result = result;
        this.data = data;
    }

    /**
     * @author baka4n
     * @param result 配方返回
     * @param blocks 序列化拿对应的方块状态
     * @param layers_s String[]表示xz坐标的拿值， ...表示层数
     */
    public record Data(BlockState result, Map<Character, BlockState> blocks, List<List<String>> layers_s) {
        public static final char CENTER = 'C';//核心方块只能有一个
        private static final Codec<Character> SYMBOL_CODEC;
        public static final MapCodec<Data> MAP_CODEC;


        static {
            SYMBOL_CODEC = Codec.STRING.comapFlatMap((s) -> {
                if (s.length() != 1) {
                    return DataResult.error(() -> "Invalid key entry: '" + s + "' is an invalid symbol (must be 1 character only).");
                } else {
                    return " ".equals(s) ? DataResult.error(() -> "Invalid key entry: ' ' is a reserved symbol.") : DataResult.success(s.charAt(0));
                }
                }, String::valueOf);
            MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance
                    .group(
                            BlockState.CODEC.fieldOf("result").forGetter(f -> f.result),
                            ExtraCodecs.strictUnboundedMap(SYMBOL_CODEC, BlockState.CODEC).fieldOf("items").forGetter(f -> f.blocks),
                            Codec.STRING.listOf().listOf().fieldOf("pattern").forGetter(f -> f.layers_s)
                    )
                    .apply(instance, Data::new));

        }


            public FormationRecipe build() {
                if (!blocks.containsKey(CENTER)) throw new IllegalArgumentException("No define Center");
                boolean isC = false;

                int defX = 0, defY = 0, defZ = 0;
                for (int y = 0; y < layers_s.size(); y++) {
                    var layers = layers_s.get(y);
                    for (int z = 0; z < layers.size(); z++) {
                        String layer = layers.get(z);
                        for (int x = 0; x < layer.length(); x++) {
                            char c = layer.charAt(x);
                            if (c == CENTER) {
                                isC = true;
                                defX = x;
                                defY = y;
                                defZ = z;
                            }
                        }
                    }
                }
                if (!isC) throw new IllegalArgumentException("No Center");
                Map<Vec3i, BlockState> bMap = new HashMap<>();
                BlockState center = blocks.get(CENTER);
                for (int y = 0; y < layers_s.size(); y++) {
                    var layers = layers_s.get(y);
                    for (int z = 0; z < layers.size(); z++) {
                        String layer = layers.get(z);
                        for (int x = 0; x < layer.length(); x++) {
                            char c = layer.charAt(x);
                            if (!blocks.containsKey(c)) {
                                throw new IllegalArgumentException("No define " + c);
                            }
                            bMap.put(new Vec3i(x - defX, y - defY, z - defZ), blocks.get(c));
                        }
                    }
                }


                return new FormationRecipe(center, result, bMap, Optional.of(this));
            }
        }


}
