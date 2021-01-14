package superworldsun.superslegend.items.armors;

import com.sun.javafx.geom.Vec3d;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.init.SoundInit;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;
import superworldsun.superslegend.lists.PotionList;

import java.util.Random;
import java.util.Vector;


public class HoverBoots extends ArmorItem {
    public HoverBoots(String name, EquipmentSlotType slot) 
    
    {
        super(ArmourMaterialList.hoverboots, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.YELLOW + "No road needed"));
		list.add(new StringTextComponent(TextFormatting.GREEN + "Sprint To Hover over Gaps"));
		
		
	}

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
    {


                boolean isBootsOn = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.hover_boots);

            	if(isBootsOn && player.isSprinting() && !player.isOnGround())
            	{
					player.addPotionEffect(new EffectInstance(PotionList.hover_boots_effect, 10, 0, true, false));

                	{
                		// Work in progress
                		//Add possible more ice effect, work on better checks and balances to the way players should properly hover.


                		//player.addVelocity(0, 0.01f, 0);
						//player.setVelocity(0, 0.1f, 0);
						Vector3d v = player.getMotion();
						player.setMotion(v.x, v.y * -0.1D, v.z);

						BlockPos currentPos = player.getPosition();
						world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundInit.HOVER_BOOTS, SoundCategory.PLAYERS, 1f, 1f);
						Random rand = player.world.rand;

						for (int i = 0; i < 45; i++)
				        {
				        	player.world.addParticle(ParticleTypes.CLOUD,
				                    player.getPosX() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 0,
				                    player.getPosY() + rand.nextFloat() * 0 - 0,
				                    player.getPosZ() + (rand.nextBoolean() ? -1 : 1) * Math.pow(rand.nextFloat(), 2) * 0,
				                    0, 0, 0);
				        }
					 
                	}
          		
          		  }

            	if(isBootsOn && !player.isSprinting())
            	{
            		player.removePotionEffect(PotionList.hover_boots_effect);

            	}
                
     }
}
            
        