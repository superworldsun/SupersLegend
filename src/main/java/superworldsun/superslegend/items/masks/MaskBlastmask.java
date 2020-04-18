package superworldsun.superslegend.items.masks;


import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import superworldsun.superslegend.Keybinds;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.items.NonEnchantArmor;
import superworldsun.superslegend.lists.ArmourMaterialList;



public class MaskBlastmask extends NonEnchantArmor {
    public MaskBlastmask(String name, EquipmentSlotType slot)
    {
        super(ArmourMaterialList.blastmask, slot, new Item.Properties().group(SupersLegend.supers_legend));
        setRegistryName(SupersLegend.modid, name);
    }

    @OnlyIn(Dist.CLIENT)
    private int bombCoolDown = 0;

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player)
    {
        if(bombCoolDown>0){
            bombCoolDown--;
        }
        if(Keybinds.bombMask.isPressed() && bombCoolDown <= 0) {
            world.createExplosion((Entity)null, player.posX, player.posY, player.posZ, 4.5f, Explosion.Mode.DESTROY);
            bombCoolDown = 100;
        }
    }

    public void addInformation(ItemStack stack, World world, java.util.List<ITextComponent> list, ITooltipFlag flag)
	{
		super.addInformation(stack, world, list, flag);				
		list.add(new StringTextComponent(TextFormatting.GRAY + "Bomb Blastic"));
	}
}
