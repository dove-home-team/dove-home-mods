package io.github.dovehometeam.mysticsadlibitum.common.java.enums;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.github.dovehometeam.dovelib.codec.CodecHandle;
import io.github.dovehometeam.mysticsadlibitum.Const;
import io.github.dovehometeam.mysticsadlibitum.common.java.interfaces.ISpell;
import io.netty.buffer.ByteBuf;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/30 11:29:53}
 */
@MethodsReturnNonnullByDefault
public enum Spell {
    BASE((level, player, stack) -> {}),
    ;
    public final ISpell spell;
    Spell(ISpell spell) {
        this.spell = spell;
    }

    public String i18n() {
        return Const.MODID + "spell." + name().toLowerCase();
    }

    public Component translate() {
        return Component.translatable(i18n());
    }



    public static class SpellCodec extends CodecHandle<Spell> {
        public static final SpellCodec INSTANCE = new SpellCodec();
        private final Codec<Spell> CODEC =
                Codec.STRING.comapFlatMap(val -> DataResult.success(Spell.valueOf(val)), Spell::name);
                ;
        @Override
        protected Spell decode(ByteBuf byteBuf) {
            return Spell.valueOf(ByteBufCodecs.STRING_UTF8.decode(byteBuf));
        }

        @Override
        protected void encode(ByteBuf o, Spell spell) {
            ByteBufCodecs.STRING_UTF8.encode(o, spell.name());
        }

        @Override
        public Codec<Spell> getCodec() {
            return CODEC;
        }
    }
}
