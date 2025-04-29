package io.github.dovehometeam.dovelib.codec;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.math.BigDecimal;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/26 18:40:57}
 */
public abstract class CodecHandle<T> {
    private final StreamCodec<ByteBuf, T> STREAM = StreamCodec.of(
            this::encode,
            this::decode
    );
    protected abstract T decode(ByteBuf byteBuf);
    protected abstract void encode(ByteBuf o, T t);
    public abstract Codec<T> getCodec();
    public StreamCodec<ByteBuf,T> getStreamCodec() {
        return STREAM;
    }
}
