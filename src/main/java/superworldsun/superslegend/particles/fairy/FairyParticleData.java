package superworldsun.superslegend.particles.fairy;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
//import superworldsun.superslegend.init.ParticleInit;

import javax.annotation.Nonnull;
import java.util.Locale;

/*public class FairyParticleData implements IParticleData {
    public static final Codec<FairyParticleData> CODEC = RecordCodecBuilder.create((group) -> {
        return group.group(Codec.FLOAT.fieldOf("r").forGetter(FairyParticleData::getRed),
                Codec.FLOAT.fieldOf("g").forGetter(FairyParticleData::getGreen),
                Codec.FLOAT.fieldOf("b").forGetter(FairyParticleData::getBlue),
                Codec.FLOAT.fieldOf("scale").forGetter(FairyParticleData::getScale)).apply(group, FairyParticleData::new);
    });

    @SuppressWarnings("deprecation")
    public static final IParticleData.IDeserializer<FairyParticleData> DESERIALIZER = new IParticleData.IDeserializer<FairyParticleData>() {
        public FairyParticleData deserialize(@Nonnull ParticleType<FairyParticleData> particleTypeIn, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float f = (float) reader.readDouble();
            reader.expect(' ');
            float f1 = (float) reader.readDouble();
            reader.expect(' ');
            float f2 = (float) reader.readDouble();
            reader.expect(' ');
            float f3 = (float) reader.readDouble();
            return new FairyParticleData(f, f1, f2, f3);
        }

        public FairyParticleData read(@Nonnull ParticleType<FairyParticleData> particleTypeIn, PacketBuffer buffer) {
            return new FairyParticleData(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat());
        }
    };

    private final float red;
    private final float green;
    private final float blue;
    private final float scale;

    public FairyParticleData(float r, float g, float b, float scale) {
        this.red = r;
        this.green = g;
        this.blue = b;
        this.scale = scale;
    }

    public void write(PacketBuffer buffer) {
        buffer.writeFloat(this.red);
        buffer.writeFloat(this.green);
        buffer.writeFloat(this.blue);
        buffer.writeFloat(this.scale);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f", Registry.PARTICLE_TYPE.getKey(this.getType()), this.red, this.green, this.blue, this.scale);
    }

    @Nonnull
    public ParticleType<FairyParticleData> getType() {
        return ParticleInit.FAIRY.get();
    }

    @OnlyIn(Dist.CLIENT)
    public float getRed() {
        return this.red;
    }

    @OnlyIn(Dist.CLIENT)
    public float getGreen() {
        return this.green;
    }

    @OnlyIn(Dist.CLIENT)
    public float getBlue() {
        return this.blue;
    }

    @OnlyIn(Dist.CLIENT)
    public float getScale() {
        return this.scale;
    }
}
}
 }*/