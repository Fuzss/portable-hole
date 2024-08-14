package fuzs.portablehole.client.core;

import fuzs.puzzleslib.api.core.v1.ServiceProviderHelper;
import net.minecraft.client.renderer.texture.AbstractTexture;

public interface ClientAbstractions {
    ClientAbstractions INSTANCE = ServiceProviderHelper.load(ClientAbstractions.class);

    void setFilterSave(AbstractTexture texture, boolean filter, boolean mipmap);

    void restoreLastFilter(AbstractTexture texture);
}
