package fuzs.portablehole.fabric.client.core;

import fuzs.portablehole.client.core.ClientAbstractions;
import fuzs.portablehole.fabric.client.renderer.texture.FilteredTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;

public class FabricClientAbstractions implements ClientAbstractions {

    @Override
    public void setBlurMipmap(AbstractTexture abstractTexture, boolean blur, boolean mipmap) {
        ((FilteredTexture) abstractTexture).portablehole$setBlurMipmap(blur, mipmap);
    }

    @Override
    public void restoreLastBlurMipmap(AbstractTexture abstractTexture) {
        ((FilteredTexture) abstractTexture).portablehole$restoreLastBlurMipmap();
    }
}
