package fuzs.portablehole.mixin;

import fuzs.portablehole.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlowingFluid.class)
abstract class FlowingFluidMixin extends Fluid {

    @Inject(method = "canHoldFluid", at = @At("HEAD"), cancellable = true)
    private void canHoldFluid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluid, CallbackInfoReturnable<Boolean> callback) {
        if (state.is(ModRegistry.TEMPORARY_HOLE_BLOCK.get())) callback.setReturnValue(false);
    }
}
