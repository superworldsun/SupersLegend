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

import javax.annotation.Nonnull;

public class BookOfMudora extends Item{

	public BookOfMudora(Properties properties)
	{
		super(properties);
	}

	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player,@Nonnull Hand hand)
	 {
		 player.getCooldownTracker().setCooldown(this, 15);

	 	//Blocks
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:blue_rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:red_rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:silver_rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:gold_rupee_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:spikes_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:gossip_stone_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:bush_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:chain_link_fence_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:deku_flower_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:pot_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:jar_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:grate_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:grass_patch_block_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:torch_tower_recipe")});

		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:shadow_block_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:false_shadow_block_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:hidden_shadow_block_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:tombstone_block_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:stone_tile_block_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:stone_path_block_recipe")});

		//Items
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:heros_bow_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:lynel_bow_x3_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:lynel_bow_x5_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:deku_shield_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:hylian_shield_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:fairy_ocarina_recipe")}); 
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ocarina_of_time_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:lens_of_truth_recipe")});

		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_screw_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_shaft_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_spring_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_core_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_core_giant_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_gear_recipe")});

			player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:arrow_bundle_recipe")});
			player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:fire_arrow_bundle_recipe")});
			player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:bomb_arrow_bundle_recipe")});
			player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:shock_arrow_bundle_recipe")});
			player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ice_arrow_bundle_recipe")});
			player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_arrow_bundle_recipe")});

		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:fire_arrow_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:bomb_arrow_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:shock_arrow_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ice_arrow_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_arrow_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:fire_arrow_recipe")});

		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:arrow_recipe")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:fire_arrow_recipe2")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:bomb_arrow_recipe2")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:shock_arrow_recipe2")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ice_arrow_recipe2")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:ancient_arrow_recipe2")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:fire_arrow_recipe2")});

		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:bomb_recipe")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:magnetic_glove")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:bit_bow_recipe")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:regular_boomerang_recipe")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:guardian_sword_recipe")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:giants_knife_recipe")});
		 	player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:biggorons_sword_recipe")});

		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:moon_pearl_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:silver_scale_stash_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:golden_scale_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:rocs_feather_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:empty_container_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:farores_wind_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:dins_fire_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:nayrus_love_recipe")});

		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:deku_leaf_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:red_jelly_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:green_jelly_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:blue_jelly_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:red_potion_mix_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:green_potion_mix_recipe")});
		 player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:blue_potion_mix_recipe")});

		//Armor
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:mask_hawkeyemask_recipe")});

		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:rocs_cape_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:zoras_flippers_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:magic_armor_cap_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:magic_armor_tunic_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:magic_armor_leggings_recipe")});
		player.unlockRecipes(new ResourceLocation[] {new ResourceLocation("superslegend:magic_armor_boots_recipe")});
		
		
		BlockPos currentPos = player.getPosition();
		 world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.BOOK_OF_MUDORA, SoundCategory.PLAYERS, 1f, 1f);
		

		return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
	}

	public void addInformation(@Nonnull ItemStack stack, World world,@Nonnull List<ITextComponent> list,@Nonnull ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GREEN + "Knowledge is power"));
	}   
} 