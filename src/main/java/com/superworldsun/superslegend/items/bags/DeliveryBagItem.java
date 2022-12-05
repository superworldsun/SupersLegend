package com.superworldsun.superslegend.items.bags;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class DeliveryBagItem extends BagItem
{
	public DeliveryBagItem()
	{
		super(new Properties().tab(SupersLegendMain.RESOURCES));
	}
	
	@Override
	public boolean canHoldItem(ItemStack stack)
	{
		Item item = stack.getItem();
		return item == Items.PAPER || item == Items.BOOK ||
				item == Items.ENCHANTED_BOOK || item == Items.KNOWLEDGE_BOOK ||
				item == Items.WRITABLE_BOOK || item == Items.WRITTEN_BOOK ||
				 item == Items.MAP || item == ItemInit.BOOK_OF_MUDORA.get() ||
				item == ItemInit.ZELDAS_LULLABY_SHEET.get() || item == ItemInit.EPONAS_SONG_SHEET.get() ||
				item == ItemInit.SARIAS_SONG_SHEET.get() || item == ItemInit.SONG_OF_TIME_SHEET.get() ||
				item == ItemInit.SUNS_SONG_SHEET.get() || item == ItemInit.SONG_OF_STORMS_SHEET.get() ||
				item == ItemInit.MINUET_OF_FOREST_SHEET.get() || item == ItemInit.BOLERO_OF_FIRE_SHEET.get() ||
				item == ItemInit.SERENADE_OF_WATER_SHEET.get() || item == ItemInit.NOCTURNE_OF_SHADOW_SHEET.get() ||
				item == ItemInit.REQUIEM_OF_SPIRIT_SHEET.get() || item == ItemInit.SONG_OF_SOARING_SHEET.get() ||
				item == ItemInit.INVERTED_SONG_OF_TIME_SHEET.get() || item == ItemInit.SONATA_OF_AWAKENING_SHEET.get() ||
				item == ItemInit.SONG_OF_DOUBLE_TIME_SHEET.get() || item == ItemInit.PRELUDE_OF_LIGHT_SHEET.get() ||
				item == ItemInit.GORON_LULLABY_SHEET.get() || item == ItemInit.OATH_TO_ORDER_SHEET.get() ||
				item == ItemInit.NEW_WAVE_BOSSA_NOVA_SHEET.get() || item == ItemInit.ELEGY_OF_EMPTYNESS_SHEET.get() ||
				item == ItemInit.SONG_OF_HEALING_SHEET.get() || item == ItemInit.ALL_SONGS_SHEET.get() ||
				item == ItemInit.AMNEISA_SHEET.get() ||
				item == Items.FILLED_MAP;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.RED + "Holds all types of parchment and letters"));
	}
}
