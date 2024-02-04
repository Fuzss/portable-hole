package fuzs.portablehole.client.core;

import net.minecraft.client.renderer.texture.AbstractTexture;

public class ClientAbstractionsForge implements ClientAbstractions {

    @Override
    public void setFilterSave(AbstractTexture texture, boolean filter, boolean mipmap) {
        texture.setBlurMipmap(filter, mipmap);
    }

    @Override
    public void restoreLastFilter(AbstractTexture texture) {
        texture.restoreLastBlurMipmap();
    }
}
