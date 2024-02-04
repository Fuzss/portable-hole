package fuzs.portablehole.client.core;

import fuzs.portablehole.client.renderer.texture.ExtendedTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;

public class ClientAbstractionsFabric implements ClientAbstractions {

    @Override
    public void setFilterSave(AbstractTexture texture, boolean filter, boolean mipmap) {
        ((ExtendedTexture) texture).setFilterSave(filter, mipmap);
    }

    @Override
    public void restoreLastFilter(AbstractTexture texture) {
        ((ExtendedTexture) texture).restoreLastFilter();
    }
}
