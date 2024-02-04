package fuzs.portablehole.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;

public class TemporaryHoleForgeBlock extends TemporaryHoleBlock {

    public TemporaryHoleForgeBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        // make it so plants won't pop-off, doesn't seem easily possible on Fabric
        return true;
    }
}
