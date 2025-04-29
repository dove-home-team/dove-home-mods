package io.github.dovehometeam.dovestaff.common.codec;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.github.dovehometeam.dovelib.codec.CodecHandle;
import io.github.dovehometeam.dovestaff.common.enums.Spell;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/26 19:00:12}
 */
public class SpellCodec extends CodecHandle<Spell> {
    public static final SpellCodec INSTANCE = new SpellCodec();
    private final Codec<Spell> CODEC = Codec.STRING.comapFlatMap(val -> DataResult.success(Spell.valueOf(val)), Spell::name);
    @Override
    protected Spell decode(ByteBuf byteBuf) {
        return Spell.valueOf(ByteBufCodecs.STRING_UTF8.decode(byteBuf));
    }

    @Override
    protected void encode(ByteBuf o, Spell spell) {
        ByteBufCodecs.STRING_UTF8.encode(o, spell.toString());
    }

    @Override
    public Codec<Spell> getCodec() {
        return CODEC;
    }
}
