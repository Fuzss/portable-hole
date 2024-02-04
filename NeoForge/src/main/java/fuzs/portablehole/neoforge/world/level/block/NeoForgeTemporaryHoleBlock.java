package fuzs.portablehole.neoforge.world.level.block;

import fuzs.portablehole.world.level.block.TemporaryHoleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;

public class NeoForgeTemporaryHoleBlock extends TemporaryHoleBlock {

    public NeoForgeTemporaryHoleBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        // make it so plants won't pop-off, doesn't seem easily possible on Fabric
        return true;
    }
}
