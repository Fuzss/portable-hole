package fuzs.portablehole.neoforge.client.core;

import fuzs.portablehole.client.core.ClientAbstractions;
import net.minecraft.client.renderer.texture.AbstractTexture;

public class NeoForgeClientAbstractions implements ClientAbstractions {

    @Override
    public void setBlurMipmap(AbstractTexture abstractTexture, boolean blur, boolean mipmap) {
        abstractTexture.setBlurMipmap(blur, mipmap);
    }

    @Override
    public void restoreLastBlurMipmap(AbstractTexture abstractTexture) {
        abstractTexture.restoreLastBlurMipmap();
    }
}
