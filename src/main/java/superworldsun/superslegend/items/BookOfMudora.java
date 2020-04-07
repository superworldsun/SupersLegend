package superworldsun.superslegend.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.init.SoundInit;

public class BookOfMudora extends Item{

	public BookOfMudora(Properties properties)
	{
		super(properties);
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
	 {
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:blue_rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:red_rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:silver_rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:spikes_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:gossip_stone_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:bush_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:chain_link_fence_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:deku_flower_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:pot_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:grate_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:grass_patch_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:torch_tower_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:heros_bow_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:moon_pearl_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:silver_scale_stash_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:golden_scale_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:rocs_feather_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:rocs_cape_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:empty_container_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:farores_wind_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:dins_fire_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:nayrus_love_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:zoras_flippers_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:deku_leaf_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:red_jelly_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:green_jelly_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:blue_jelly_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:red_potion_mix_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:green_potion_mix_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:blue_potion_mix_recipe")});
		
		
		BlockPos currentPos = player.getPosition();
		 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOK_OF_MUDORA, SoundCategory.PLAYERS, 1f, 1f);
		

		return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
	}
	
	
	@Override
	public void addInformation(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GREEN + "Knowledge is power"));
	}   
} 