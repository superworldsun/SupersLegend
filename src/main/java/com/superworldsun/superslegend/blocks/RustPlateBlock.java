package com.superworldsun.superslegend.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;

import java.util.List;
import java.util.stream.Stream;

public class RustPlateBlock extends CustomShapeBlock {

    private static final VoxelShape SHAPE = Stream
            .of(box(6, 0.5, 6, 7, 1.5, 7),
                    box(6, 1.5, 6, 7, 2.5, 7),
                    box(6, 0.5, 9, 7, 1.5, 10),
                    box(8, 0.5, 9, 9, 1.5, 10),
                    box(8, 0.5, 6, 9, 1.5, 7),
                    box(5, 0, 5, 10, 1, 11))
            .reduce((v1, v2) -> {
                return VoxelShapes.join(v1, v2, IBooleanFunction.OR);
            }).get();

    public RustPlateBlock(Properties properties) {
        super(properties);
        runCalculation(SHAPE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES.get(this).get(state.getValue(HORIZONTAL_FACING));
    }

    @Override
    public void appendHoverText(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag
            flagIn) {
        tooltip.add(new StringTextComponent("A rusted plate-block."));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}


