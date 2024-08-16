package fuzs.portablehole.neoforge.world.level.block;

import fuzs.portablehole.world.level.block.TemporaryHoleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;

public class NeoForgeTemporaryHoleBlock extends TemporaryHoleBlock {

    public NeoForgeTemporaryHoleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        // make it so plants won't pop-off, doesn't seem easily possible on Fabric
        return TriState.TRUE;
    }
}
