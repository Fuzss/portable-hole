package fuzs.portablehole.fabric.mixin.client;

import fuzs.portablehole.fabric.client.renderer.texture.FilteredTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractTexture.class)
abstract class AbstractTextureFabricMixin implements FilteredTexture {
    @Unique
    protected boolean portablehole$blur;
    @Unique
    protected boolean portablehole$mipmap;
    @Unique
    private boolean portablehole$lastBlur;
    @Unique
    private boolean portablehole$lastMipmap;

    @Shadow
    public abstract void setFilter(boolean blur, boolean mipmap);

    @Inject(method = "setFilter", at = @At("HEAD"))
    public void setFilter(boolean blur, boolean mipmap, CallbackInfo callback) {
        this.portablehole$blur = blur;
        this.portablehole$mipmap = mipmap;
    }

    @Override
    public void portablehole$setBlurMipmap(boolean blur, boolean mipmap) {
        this.portablehole$lastBlur = this.portablehole$blur;
        this.portablehole$lastMipmap = this.portablehole$mipmap;
        this.setFilter(blur, mipmap);
    }

    @Override
    public void portablehole$restoreLastBlurMipmap() {
        this.setFilter(this.portablehole$lastBlur, this.portablehole$lastMipmap);
    }
}
