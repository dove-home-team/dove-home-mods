package io.github.dovehometeam.mysticsadlibitum.common.codec;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import io.github.dovehometeam.dovelib.codec.CodecHandle;
import io.netty.buffer.ByteBuf;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/29 20:31:34}
 */
public class PairDoubleCodec extends CodecHandle<Pair<Double, Double>> {
    public static final PairDoubleCodec INSTANCE = new PairDoubleCodec();
    private final Codec<Pair<Double, Double>> CODEC = Codec.pair(Codec.DOUBLE, Codec.DOUBLE);



    @Override
    protected Pair<Double, Double> decode(ByteBuf byteBuf) {
        return new Pair<>(byteBuf.readDouble(), byteBuf.readDouble());
    }

    @Override
    protected void encode(ByteBuf o, Pair<Double, Double> doubleDoublePair) {
        o.writeDouble(doubleDoublePair.getFirst());
        o.writeDouble(doubleDoublePair.getSecond());
    }

    @Override
    public Codec<Pair<Double, Double>> getCodec() {
        return CODEC;
    }
}
