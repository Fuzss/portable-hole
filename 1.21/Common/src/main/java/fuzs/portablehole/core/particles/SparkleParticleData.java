/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package fuzs.portablehole.core.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.portablehole.init.ModRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

public class SparkleParticleData implements ParticleOptions {
    public static final MapCodec<SparkleParticleData> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codec.FLOAT.fieldOf("size").forGetter(d -> d.size),
                    Codec.FLOAT.fieldOf("r").forGetter(d -> d.r), Codec.FLOAT.fieldOf("g").forGetter(d -> d.g),
                    Codec.FLOAT.fieldOf("b").forGetter(d -> d.b), Codec.INT.fieldOf("m").forGetter(d -> d.m),
                    Codec.BOOL.fieldOf("no_clip").forGetter(d -> d.noClip),
                    Codec.BOOL.fieldOf("fake").forGetter(d -> d.fake),
                    Codec.BOOL.fieldOf("corrupt").forGetter(d -> d.corrupt)
            ).apply(instance, SparkleParticleData::new));
    public static final StreamCodec<FriendlyByteBuf, SparkleParticleData> STREAM_CODEC = StreamCodec.ofMember(
            SparkleParticleData::toNetwork, SparkleParticleData::fromNetwork);

    public final float size;
    public final float r, g, b;
    public final int m;
    public final boolean noClip;
    public final boolean fake;
    public final boolean corrupt;

    public static SparkleParticleData noClip(float size, float r, float g, float b, int m) {
        return new SparkleParticleData(size, r, g, b, m, true, false, false);
    }

    public static SparkleParticleData fake(float size, float r, float g, float b, int m) {
        return new SparkleParticleData(size, r, g, b, m, false, true, false);
    }

    public static SparkleParticleData corrupt(float size, float r, float g, float b, int m) {
        return new SparkleParticleData(size, r, g, b, m, false, false, true);
    }

    public static SparkleParticleData sparkle(float size, float r, float g, float b, int m) {
        return new SparkleParticleData(size, r, g, b, m, false, false, false);
    }

    private SparkleParticleData(float size, float r, float g, float b, int m, boolean noClip, boolean fake, boolean corrupt) {
        this.size = size;
        this.r = r;
        this.g = g;
        this.b = b;
        this.m = m;
        this.noClip = noClip;
        this.fake = fake;
        this.corrupt = corrupt;
    }

    @NotNull
    @Override
    public ParticleType<SparkleParticleData> getType() {
        return ModRegistry.SPARK_PARTICLE_TYPE.value();
    }

    private void toNetwork(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeFloat(this.size);
        friendlyByteBuf.writeFloat(this.r);
        friendlyByteBuf.writeFloat(this.g);
        friendlyByteBuf.writeFloat(this.b);
        friendlyByteBuf.writeInt(this.m);
        friendlyByteBuf.writeBoolean(this.noClip);
        friendlyByteBuf.writeBoolean(this.fake);
        friendlyByteBuf.writeBoolean(this.corrupt);
    }

    private static SparkleParticleData fromNetwork(FriendlyByteBuf friendlyByteBuf) {
        return new SparkleParticleData(friendlyByteBuf.readFloat(), friendlyByteBuf.readFloat(),
                friendlyByteBuf.readFloat(), friendlyByteBuf.readFloat(), friendlyByteBuf.readInt(),
                friendlyByteBuf.readBoolean(), friendlyByteBuf.readBoolean(), friendlyByteBuf.readBoolean()
        );
    }
}
