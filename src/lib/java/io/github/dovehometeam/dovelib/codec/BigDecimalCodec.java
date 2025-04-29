package io.github.dovehometeam.dovelib.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.math.BigDecimal;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/26 18:44:10}
 */
public class BigDecimalCodec extends CodecHandle<BigDecimal> {
    public static final BigDecimalCodec INSTANCE = new BigDecimalCodec();
    private final Codec<BigDecimal> CODEC = Codec.STRING.comapFlatMap(val -> DataResult.success(new BigDecimal(val)), String::valueOf);

    @Override
    public BigDecimal decode(ByteBuf byteBuf) {
        return new BigDecimal(ByteBufCodecs.STRING_UTF8.decode(byteBuf));
    }

    @Override
    public void encode(ByteBuf o, BigDecimal bigDecimal) {
        ByteBufCodecs.STRING_UTF8.encode(o, bigDecimal.toString());
    }

    @Override
    public Codec<BigDecimal> getCodec() {
        return CODEC;
    }

    @Override
    public StreamCodec<ByteBuf, BigDecimal> getStreamCodec() {
        return null;
    }
}
