package com.superworldsun.superslegend.blocks;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.blocks.entity.HiddenShadowBlockEntity;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID, value = Dist.CLIENT)
public class HiddenShadowBlock extends ShadowBlock {

    public HiddenShadowBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HiddenShadowBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    /**
     * Keep the invisible block from revealing itself through Minecraft's selection outline.
     * While the Lens of Truth is actively revealing the block, the outline is allowed again,
     */
    @SubscribeEvent
    public static void onRenderBlockHighlight(RenderHighlightEvent.Block event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null || minecraft.player == null) {
            return;
        }

        BlockPos targetPos = event.getTarget().getBlockPos();
        if (!(minecraft.level.getBlockState(targetPos).getBlock() instanceof HiddenShadowBlock)) {
            return;
        }

        boolean usingLens = minecraft.player.isUsingItem()
                && minecraft.player.getUseItem().getItem() == ItemInit.LENS_OF_TRUTH.get();
        if (!usingLens) {
            event.setCanceled(true);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (level.getBlockEntity(pos) instanceof HiddenShadowBlockEntity hiddenShadowBlockEntity) {
            BlockState disguise = hiddenShadowBlockEntity.getDisguise();
            if (disguise != null && !(disguise.getBlock() instanceof ShadowBlock)) {
                return disguise.getShape(level, pos, context);
            }
        }
        return super.getShape(state, level, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (level.getBlockEntity(pos) instanceof HiddenShadowBlockEntity hiddenShadowBlockEntity) {
            BlockState disguise = hiddenShadowBlockEntity.getDisguise();
            if (disguise != null) {
                return disguise.getCollisionShape(level, pos, context);
            }
        }
        return super.getCollisionShape(state, level, pos, context);
    }
}
