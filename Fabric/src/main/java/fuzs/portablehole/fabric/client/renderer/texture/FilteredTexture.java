/*
 * This class is distributed as part of the Botania Mod.
 * Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 */
package fuzs.portablehole.fabric.client.renderer.texture;

public interface FilteredTexture {

	void portablehole$setFilterSave(boolean bilinear, boolean mipmap);

	void portablehole$restoreLastFilter();
}
