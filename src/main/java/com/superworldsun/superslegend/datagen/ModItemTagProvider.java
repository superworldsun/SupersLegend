package com.superworldsun.superslegend.datagen;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class ModItemTagProvider extends ItemTagsProvider {
	public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
			CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper fileHelper) {
		super(output, lookupProvider, blockTags, SupersLegendMain.MOD_ID, fileHelper);
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider provider) {
		fillItemTag(ItemTags.MUSIC_DISCS, RecordItem.class);
	}

	/**
	 * Fills a specific tag with items of a given item class.
	 *
	 * @param tag       The tag to fill with items.
	 * @param itemClass The class of items to add to the tag.
	 *
	 * @example - Input: tag = ItemTags.MUSIC_DISCS, itemClass = RecordItem.class
	 */
	private void fillItemTag(TagKey<Item> tag, Class<? extends Item> itemClass) {
		// Get all items of the given item class and add them to the tag
		getModItems(itemClass).forEach(tag(tag)::add);
	}

	/**
	 * Retrieves all items of a given item class from the mod's item registry.
	 *
	 * @param itemClass The class of items to retrieve.
	 * @return A stream of items of the given item class.
	 *
	 * @example - Input: itemClass = RecordItem.class<br>
	 *          - Output: Stream of items of class RecordItem
	 */
	private Stream<Item> getModItems(Class<? extends Item> itemClass) {
		// Get all items from the mod's item registry
		Stream<Item> allItems = ItemInit.ITEMS.getEntries().stream().map(RegistryObject::get);
		// Filter the items to only include those of the given item class
		return allItems.filter(itemClass::isInstance).map(itemClass::cast);
	}
}