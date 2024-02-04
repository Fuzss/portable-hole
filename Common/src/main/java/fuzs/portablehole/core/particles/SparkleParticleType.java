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
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class SparkleParticleType extends ParticleType<SparkleParticleData> {
	public SparkleParticleType() {
		super(false, SparkleParticleData.DESERIALIZER);
	}

	@NotNull
	@Override
	public Codec<SparkleParticleData> codec() {
		return SparkleParticleData.CODEC;
	}

	// moved client particle factory to client particle class (FXSparkle)
}
