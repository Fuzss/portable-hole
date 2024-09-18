package fuzs.portablehole.neoforge.client.core;

import fuzs.portablehole.client.core.ClientAbstractions;
import net.minecraft.client.renderer.texture.AbstractTexture;

public class NeoForgeClientAbstractions implements ClientAbstractions {

    @Override
    public void setFilterSave(AbstractTexture texture, boolean filter, boolean mipmap) {
        texture.setBlurMipmap(filter, mipmap);
    }

    @Override
    public void restoreLastFilter(AbstractTexture texture) {
        texture.restoreLastBlurMipmap();
    }
}
