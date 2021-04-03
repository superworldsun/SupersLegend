package superworldsun.superslegend.events;

//import net.minecraft.arrows.SharedMonsterAttributes;
import net.minecraft.advancements.criterion.ItemDurabilityTrigger;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import superworldsun.superslegend.SupersLegend;
import superworldsun.superslegend.entities.projectiles.arrows.EntityArrowAncient;
import superworldsun.superslegend.lists.ItemList;

@Mod.EventBusSubscriber(modid = SupersLegend.modid, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RegistryEvents {



	/*@SubscribeEvent
	public static void livingDeath(LivingDeathEvent event)
	{
		if (event.getEntity() instanceof PlayerEntity)
		{
			event.setCanceled(true);
		}
	}*/

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {
        Entity sourceEntity = event.getSource().getImmediateSource();
        if (sourceEntity instanceof EntityArrowAncient) {
            event.getDrops().clear();
        }
    }


	/*@SuppressWarnings("unused")
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void beforePlayerHurt(LivingAttackEvent event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof PlayerEntity)) {
			return;
		}
		PlayerEntity player = (PlayerEntity) entity;

		if (player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem().equals(ItemList.magic_armor_cap)&&
			player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem().equals(ItemList.magic_armor_tunic)&&
			player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem().equals(ItemList.magic_armor_leggings)&&
			player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem().equals(ItemList.magic_armor_boots))
		{
			
		}
	}*/

		/*@SubscribeEvent
		public static void pickupItem(EntityItemPickupEvent event) {
			event.getPlayer().inventory.addItemStackToInventory(new ItemStack(Items.SNOWBALL));
		}*/

	/*@SubscribeEvent
	public static void OnCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
		if (event.getCrafting().getItem() != Items.ANVIL) {
			event.getPlayer().inventory.addItemStackToInventory(new ItemStack(Items.SNOWBALL));
		}
	}*/



}