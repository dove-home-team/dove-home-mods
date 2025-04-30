package io.github.dovehometeam.mysticsadlibitum.common.java.records;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.dovehometeam.dovelib.codec.CodecHandle;
import io.netty.buffer.ByteBuf;
import lombok.Builder;

/**
 * @author : baka4n
 * {@code @Date : 2025/04/30 10:46:30}
 */
@Builder
public record NoitaWandProperties(
        boolean shuffle,
        int capacity,//容量
        int maxMana,//最大法力值
        int mana,//当前法力值
        float castDelay,//施法延迟
        float rechargeTime,//充能延迟
        int manaChargeSpeed,//法力充能速度
        double spread,
        double speed,
        int spells_cast
) {
    public static final NoitaWandProperties DEFAULT = NoitaWandProperties.builder().build();
    public static class NoitaWandPropertiesCodec extends CodecHandle<NoitaWandProperties> {
        public static final NoitaWandPropertiesCodec INSTANCE = new NoitaWandPropertiesCodec();
        private final Codec<NoitaWandProperties> CODEC =
                RecordCodecBuilder.create(instance -> instance.group(
                        Codec.BOOL.fieldOf("shuffle").forGetter(p -> p.shuffle),
                        Codec.INT.fieldOf("capacity").forGetter(p -> p.capacity),
                        Codec.INT.fieldOf("maxMana").forGetter(p -> p.maxMana),
                        Codec.INT.fieldOf("mana").forGetter(p -> p.mana),
                        Codec.FLOAT.fieldOf("castDelay").forGetter(p -> p.castDelay),
                        Codec.FLOAT.fieldOf("rechargeTime").forGetter(p -> p.rechargeTime),
                        Codec.INT.fieldOf("manaChargeSpeed").forGetter(p -> p.manaChargeSpeed),
                        Codec.DOUBLE.fieldOf("spread").forGetter(p -> p.spread),
                        Codec.DOUBLE.fieldOf("speed").forGetter(p -> p.speed),
                        Codec.INT.fieldOf("spells_cast").forGetter(p -> p.spells_cast)
                ).apply(instance, NoitaWandProperties::new));
        @Override
        protected NoitaWandProperties decode(ByteBuf byteBuf) {
            return new NoitaWandProperties(
                    byteBuf.readBoolean(),
                    byteBuf.readInt(),
                    byteBuf.readInt(),
                    byteBuf.readInt(),
                    byteBuf.readFloat(),
                    byteBuf.readFloat(),
                    byteBuf.readInt(),
                    byteBuf.readDouble(),
                    byteBuf.readDouble(),
                    byteBuf.readInt()
            );
        }

        @Override
        protected void encode(ByteBuf o, NoitaWandProperties noitaWandProperties) {
            o.writeBoolean(noitaWandProperties.shuffle);
            o.writeInt(noitaWandProperties.capacity);
            o.writeInt(noitaWandProperties.maxMana);
            o.writeInt(noitaWandProperties.mana);
            o.writeFloat(noitaWandProperties.castDelay);
            o.writeFloat(noitaWandProperties.rechargeTime);
            o.writeInt(noitaWandProperties.manaChargeSpeed);
            o.writeDouble(noitaWandProperties.spread);
            o.writeDouble(noitaWandProperties.speed);
            o.writeInt(noitaWandProperties.spells_cast);
        }

        @Override
        public Codec<NoitaWandProperties> getCodec() {
            return CODEC;
        }
    }
}
