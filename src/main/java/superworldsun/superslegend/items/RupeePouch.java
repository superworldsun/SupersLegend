package superworldsun.superslegend.items;

/*import net.minecraft.arrows.player.*;
import net.minecraft.inventory.container.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class RupeePouch extends UItem{
	
	private final RupeePouch rupeepouch;
	
	public RupeePouch(Backpack backpack) {
		super("backpack_" + backpack.getName(), UsefulBackpacksItemGroups.GROUP, new Properties().maxStackSize(1).rarity(backpack.getRarity()));
		this.backpack = backpack;
		
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		final ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote && player instanceof ServerPlayerEntity) {
			NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
				
				@Override
				public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player) {
					return new BackpackContainer(id, playerInventory, new BackpackInventory(stack, rupeepouch.getInventorySize()), rupeepouch);
				}
				
				@Override
				public ITextComponent getDisplayName() {
					return stack.getDisplayName();
				}
			}, buffer -> buffer.writeEnumValue(rupeepouch));
			
		}
		return new ActionResult<>(ActionResultType.SUCCESS, stack);
	}
}*/