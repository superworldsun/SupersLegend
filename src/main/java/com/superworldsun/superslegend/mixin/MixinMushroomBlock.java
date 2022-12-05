package com.superworldsun.superslegend.mixin;

import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.spongepowered.asm.mixin.Mixin;

import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.MushroomBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(MushroomBlock.class)
public abstract class MixinMushroomBlock extends BushBlock
{
	// This constructor is fake and never used
	protected MixinMushroomBlock()
	{
		super(null);
	}
	
	@OnlyIn(value = Dist.CLIENT)
	@Override
	public void animateTick(BlockState state, World world, BlockPos pos, Random random)
	{
		Minecraft client = Minecraft.getInstance();
		ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_MASKOFSCENTS.get(), client.player).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		if (!maskStack.isEmpty())
		{
			double radius = 20;
			
			if (client.player.distanceToSqr(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5) < radius * radius)
			{
				double particleX = pos.getX() + 0.5 + world.random.nextDouble() * 0.4 - 0.2;
				double particleY = pos.getY() + 0.2 + world.random.nextDouble() * 0.4 - 0.1;
				double particleZ = pos.getZ() + 0.5 + world.random.nextDouble() * 0.4 - 0.2;
				double particleMotionY = world.random.nextDouble() * 0.1 + 0.1;
				world.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ, 0, particleMotionY, 0);
				world.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ, 0, particleMotionY, 0);
				world.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ, 0, particleMotionY, 1);
				world.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ, 0, particleMotionY, 1);
				world.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ, 0, particleMotionY, 2);
				world.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ, 0, particleMotionY, 2);
				world.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ, 0, particleMotionY, 3);
				world.addParticle(ParticleTypes.MYCELIUM, particleX, particleY, particleZ, 0, particleMotionY, 3);
			}
		}
	}
}
