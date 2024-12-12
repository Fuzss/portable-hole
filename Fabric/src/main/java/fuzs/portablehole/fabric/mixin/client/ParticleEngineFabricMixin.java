/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package fuzs.portablehole.fabric.mixin.client;

import com.google.common.collect.ImmutableList;
import fuzs.portablehole.client.particle.FXSparkle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleRenderType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ParticleEngine.class)
abstract class ParticleEngineFabricMixin {
    @Shadow
    @Mutable
    private static List<ParticleRenderType> RENDER_ORDER;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void addTypes(CallbackInfo callback) {
        RENDER_ORDER = ImmutableList.<ParticleRenderType>builder()
                .addAll(RENDER_ORDER)
                .add(FXSparkle.NORMAL_RENDER)
                .build();
    }
}
