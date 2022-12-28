package com.superworldsun.superslegend.items.curios.head.masks;

import java.util.List;
import java.util.UUID;

import com.superworldsun.superslegend.items.custom.NonEnchantItem;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.client.model.armor.MaskOfTruthModel;
import com.superworldsun.superslegend.registries.ItemInit;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

@EventBusSubscriber(bus = Bus.FORGE, modid = SupersLegendMain.MOD_ID)
public class MaskMaskofTruth extends NonEnchantItem implements ICurioItem
{
	@OnlyIn(Dist.CLIENT)
	private Object model;
	// put your texture here
	private static final ResourceLocation TEXTURE = new ResourceLocation(SupersLegendMain.MOD_ID, "textures/models/armor/mask_of_truth.png");
	
	private static final String[] BEE_SPEECHES = { "BZZZZ off, im working here", "I only work for my queen" };
	private static final String[] BAT_SPEECHES = { "Don't worry, i don't suck blood", "I've seen purple rocks down y-19, you should look for it" };
	private static final String[] CAT_SPEECHES = { "Don't,worry, i'll keep you safe from those creepers and phantoms", "owo", "I hate Mondays" };
	private static final String[] COW_SPEECHES = { "Got Milk?", "My milk can cure any effect, try it if you feel sick" };
	private static final String[] CHICKEN_SPEECHES = { "I know what you're thinking, don't",
			"You can have my eggs, just spare me please", "I'm no coward, i just don't want to be here" };
	private static final String[] DONKEY_SPEECHES = { "I'm a donkey on the edge!", "you slay dragons, i lay dragons" };
	private static final String[] DOLPHIN_SPEECHES = { "Hello land friend, what brings you out here?", "Find any good treasure lately?" };
	private static final String[] FOX_SPEECHES = { "Ring-ding-ding-ding-dingeringeding", "nothing personal, but i only trust foxes" };
	private static final String[] HORSE_SPEECHES = { "Got any apples?", "Im so hungry i could eat a... never mind", "Don't,worry, I'll help you travel the world faster!" };
	private static final String[] LLAMA_SPEECHES = { "No... touchy", "Gah! You threw off my groove", "Hey, tiny" };
	private static final String[] MOOSHROOM_SPEECHES = { "I was certain creatures without mooshrooms was a myth", "Im a bit of a fungi" };
	private static final String[] MULE_SPEECHES = { "I was born to carry your burdens.", "Need a lift?" };
	private static final String[] OCELOT_SPEECHES = { "Merrawrr, im hunting for prey right now", "rawrr, got any fish on ya?" };
	private static final String[] PANDA_SPEECHES = { "Just because im a panda doesn't mean I know kung fu",
			"Dont talk much? Thats cool, I always fancy myself a silent protag as well", "This green stuff is great, you should try cooking it first" };
	private static final String[] PARROT_SPEECHES = { "Got any crackers?", "Know of any hot bees?", "Wheres the music?",
			"One day I would like to sail the seas on the shoulder of a brave pirate, that would be the best" };
	private static final String[] PIG_SPEECHES = { "One day i'll fly, just you wait", "I wonder whats above the clouds, do you ever ponder that as well?" };
	private static final String[] POLAR_SPEECHES = { "You're not cold are you?", "Its been a while since ive seen another creature" };
	private static final String[] RABBIT_SPEECHES = { "Im great at multiplication", "Its Duck season" };
	private static final String[] SHEEP_SPEECHES = { "My wool is very soft, its to di... shave for", "Did you know 1 out of 610 sheep are pink? I wish I was that lucky" };
	private static final String[] STRIDER_SPEECHES = { "You're a strange looking creature", "You don't look fireproof" };
	private static final String[] TRADER_SPEECHES = { "My master has the best deals in all the land.", "Master is very busy" };
	private static final String[] TURTLE_SPEECHES = { "You're a weird looking fish", "You got any of that seaweed on ya?" };
	private static final String[] WOLF_SPEECHES = { "Arf Arf, if you see any bony monsters, you'd show me right?", "I got the dog in me" };
	
	public MaskMaskofTruth(Properties properties)
	{
		super(properties);
	}
	
	@SubscribeEvent
	public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event)
	{
		if (event.getPlayer().level.isClientSide)
		{
			return;
		}
		
		ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_MASKOFTRUTH.get(), (LivingEntity) event.getEntity()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);
		
		if (maskStack.isEmpty())
		{
			return;
		}
		
		if (event.getHand() == Hand.OFF_HAND)
		{
			return;
		}
		
		if (event.getTarget().getType() == EntityType.BEE)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.BEE_LOOP, BEE_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.BAT)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.BAT_AMBIENT, BAT_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.CAT)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.CAT_AMBIENT, CAT_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.COW)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.COW_AMBIENT, COW_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.CHICKEN)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.CHICKEN_AMBIENT, CHICKEN_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.DONKEY)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.DONKEY_AMBIENT, DONKEY_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.DOLPHIN)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.DOLPHIN_AMBIENT, DOLPHIN_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.FOX)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.FOX_AMBIENT, FOX_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.HORSE)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.HORSE_AMBIENT, HORSE_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.LLAMA)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.LLAMA_AMBIENT, LLAMA_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.MOOSHROOM)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.COW_AMBIENT, MOOSHROOM_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.MULE)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.MULE_AMBIENT, MULE_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.OCELOT)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.OCELOT_AMBIENT, OCELOT_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.PANDA)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.PANDA_AMBIENT, PANDA_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.PARROT)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.PARROT_AMBIENT, PARROT_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.PIG)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.PIG_AMBIENT, PIG_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.POLAR_BEAR)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.POLAR_BEAR_AMBIENT, POLAR_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.RABBIT)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.RABBIT_AMBIENT, RABBIT_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.SHEEP)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.SHEEP_AMBIENT, SHEEP_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.STRIDER)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.STRIDER_AMBIENT, STRIDER_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.TRADER_LLAMA)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.LLAMA_AMBIENT, TRADER_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.TURTLE)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.TURTLE_AMBIENT_LAND, TURTLE_SPEECHES);
		}
		else if (event.getTarget().getType() == EntityType.WOLF)
		{
			sendRandomMessage(event.getPlayer(), event.getTarget(), SoundEvents.WOLF_AMBIENT, WOLF_SPEECHES);
		}
	}
	
	private static void sendRandomMessage(PlayerEntity player, Entity target, SoundEvent sound, String[] speeches)
	{
		int line = player.getRandom().nextInt(speeches.length);
		player.sendMessage(new StringTextComponent(speeches[line]), UUID.randomUUID());
		target.playSound(sound, 1.0F, 1.0F);
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
	{
		super.appendHoverText(stack, world, list, flag);
		list.add(new StringTextComponent(TextFormatting.GRAY + "A mask that is said to see into"));
		list.add(new StringTextComponent(TextFormatting.GRAY + "the hearts and minds of others"));
	}
	
	@Override
	public boolean canRender(String identifier, int index, LivingEntity livingEntity, ItemStack stack)
	{
		return true;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public void render(String identifier, int index, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity livingEntity, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		// put your model here
		if (!(this.model instanceof MaskOfTruthModel))
		{
			model = new MaskOfTruthModel<>();
		}
		
		MaskOfTruthModel<?> maskModel = (MaskOfTruthModel<?>) this.model;
		ICurio.RenderHelper.followHeadRotations(livingEntity, maskModel.base);
		IVertexBuilder vertexBuilder = ItemRenderer.getArmorFoilBuffer(renderTypeBuffer, maskModel.renderType(TEXTURE), false, stack.hasFoil());
		maskModel.renderToBuffer(matrixStack, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}