package fuzs.portablehole.core.particles;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class SparkleParticleType extends ParticleType<SparkleParticleData> {

    public SparkleParticleType() {
        super(false);
    }

    @NotNull
    @Override
    public MapCodec<SparkleParticleData> codec() {
        return SparkleParticleData.CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, SparkleParticleData> streamCodec() {
        return SparkleParticleData.STREAM_CODEC;
    }
}
