package fuzs.portablehole.forge.world.level.block;

import fuzs.portablehole.world.level.block.TemporaryHoleBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.IPlantable;

public class ForgeTemporaryHoleBlock extends TemporaryHoleBlock {

    public ForgeTemporaryHoleBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        // make it so plants won't pop-off, doesn't seem easily possible on Fabric
        return true;
    }
}
