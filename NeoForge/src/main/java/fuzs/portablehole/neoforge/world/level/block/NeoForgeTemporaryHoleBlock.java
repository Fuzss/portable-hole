package fuzs.portablehole.neoforge.world.level.block;

import fuzs.portablehole.common.world.level.block.TemporaryHoleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.TriState;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Make it so plants won't pop-off.
 * <p>
 * On Fabric this can only partially be handled via {@link BlockTags#SUPPORTS_VEGETATION}.
 */
public class NeoForgeTemporaryHoleBlock extends TemporaryHoleBlock {

    public NeoForgeTemporaryHoleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        return TriState.TRUE;
    }
}
