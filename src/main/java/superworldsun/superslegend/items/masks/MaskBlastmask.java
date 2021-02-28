package superworldsun.superslegend.items.masks;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;
import superworldsun.superslegend.lists.ItemList;



public class MaskBlastmask extends NonEnchantArmor {
    public MaskBlastmask(String name, EquipmentSlotType slot) 

    {
        super(ArmourMaterialList.blastmask, slot, new Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }
    

/*@SubscribeEvent
public void Event(LivingAttackEvent event) {

	if (event.getEntity() instanceof PlayerEntity) {
		PlayerEntity player = (PlayerEntity) event.getEntity();

	if(event.getSource().isExplosion() && player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_blastmask));
	{
		event.setCanceled(true);	
	}

	}
}*/
    
    
    /*@Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) 
    {
    	
    	
    	
        if (!world.isRemote)
        	
        	  {
                boolean isHelmeton = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.mask_blastmask);
                
                if(isHelmeton)
                	
                	player.isImmuneToExplosions()
              }
        
    }*/

   /* protected void applyCustomModifiers(ItemStack stack, PlayerEntity player) {}

	
	public void removeModifiers(ItemStack stack, PlayerEntity player) {}

	
	public void explode1(PlayerEntity player, ItemStack stack, World world, double x, double y, double z) {}



		public void explode(PlayerEntity player, ItemStack stack, World world, double x, double y, double z) {
			if (player.capabilities.isCreativeMode || !isCooling(world, stack)) {
				CustomExplosion.createExplosion(new BombEntity(world), world, x, y, z, 3.0F, 10.0F, false);
				setNextUse(world, stack, 40);
			} else {
				world.playSoundEffect(x, y, z, Sounds.CLICK, 0.3F, 0.6F);
			}
	
		private boolean isCooling(World world, ItemStack stack) {
			return (stack.hasTagCompound() && world.getTotalWorldTime() < stack.getTagCompound().getInteger("nextUse"));
		
		private void setNextUse(World world, ItemStack stack, int time) {
			if (!stack.hasTagCompound()) { stack.setTagCompound(new NBTTagCompound()); }
			stack.getTagCompound().setLong("nextUse", world.getTotalWorldTime() + time);
		}*/
	
	
    
    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "Bomb Blastic"));
	}
}
