/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package fuzs.portablehole.fabric.mixin.client;

import fuzs.portablehole.fabric.client.renderer.texture.FilteredTexture;
import net.minecraft.client.renderer.texture.AbstractTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractTexture.class)
abstract class AbstractTextureFabricMixin implements FilteredTexture {
	@Shadow
	protected boolean blur;

	@Shadow
	protected boolean mipmap;

	@Shadow
	public abstract void setFilter(boolean bilinear, boolean mipmap);

	@Unique
	private boolean lastBilinear;

	@Unique
	private boolean lastMipmap;

	@Override
	public void portablehole$setFilterSave(boolean bilinear, boolean mipmap) {
		this.lastBilinear = this.blur;
		this.lastMipmap = this.mipmap;
        this.setFilter(bilinear, mipmap);
	}

	@Override
	public void portablehole$restoreLastFilter() {
        this.setFilter(this.lastBilinear, this.lastMipmap);
	}
}
