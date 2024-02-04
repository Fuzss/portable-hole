package fuzs.portablehole.fabric.client.core;

import fuzs.portablehole.client.core.ClientAbstractions;
import fuzs.portablehole.fabric.client.renderer.texture.FilteredTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;

public class FabricClientAbstractions implements ClientAbstractions {

    @Override
    public void setFilterSave(AbstractTexture texture, boolean filter, boolean mipmap) {
        ((FilteredTexture) texture).portablehole$setFilterSave(filter, mipmap);
    }

    @Override
    public void restoreLastFilter(AbstractTexture texture) {
        ((FilteredTexture) texture).portablehole$restoreLastFilter();
    }
}
