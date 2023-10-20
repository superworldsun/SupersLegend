package com.superworldsun.superslegend.items.curios.head.masks;

import com.superworldsun.superslegend.registries.ItemInit;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

public class MaskOfTruth extends Item implements ICurioItem {
    public MaskOfTruth(Properties pProperties) {
        super(pProperties);
    }

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

    @SubscribeEvent
    public static void onPlayerEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getEntity().level().isClientSide)
        {
            return;
        }

        ItemStack maskStack = CuriosApi.getCuriosHelper().findEquippedCurio(ItemInit.MASK_MASKOFTRUTH.get(), (LivingEntity) event.getEntity()).map(ImmutableTriple::getRight).orElse(ItemStack.EMPTY);

        if (maskStack.isEmpty())
        {
            return;
        }

        if (event.getHand() == InteractionHand.OFF_HAND)
        {
            return;
        }

        if (event.getTarget().getType() == EntityType.BEE)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.BEE_LOOP, BEE_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.BAT)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.BAT_AMBIENT, BAT_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.CAT)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.CAT_AMBIENT, CAT_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.COW)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.COW_AMBIENT, COW_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.CHICKEN)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.CHICKEN_AMBIENT, CHICKEN_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.DONKEY)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.DONKEY_AMBIENT, DONKEY_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.DOLPHIN)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.DOLPHIN_AMBIENT, DOLPHIN_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.FOX)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.FOX_AMBIENT, FOX_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.HORSE)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.HORSE_AMBIENT, HORSE_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.LLAMA)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.LLAMA_AMBIENT, LLAMA_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.MOOSHROOM)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.COW_AMBIENT, MOOSHROOM_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.MULE)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.MULE_AMBIENT, MULE_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.OCELOT)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.OCELOT_AMBIENT, OCELOT_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.PANDA)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.PANDA_AMBIENT, PANDA_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.PARROT)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.PARROT_AMBIENT, PARROT_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.PIG)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.PIG_AMBIENT, PIG_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.POLAR_BEAR)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.POLAR_BEAR_AMBIENT, POLAR_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.RABBIT)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.RABBIT_AMBIENT, RABBIT_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.SHEEP)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.SHEEP_AMBIENT, SHEEP_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.STRIDER)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.STRIDER_AMBIENT, STRIDER_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.TRADER_LLAMA)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.LLAMA_AMBIENT, TRADER_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.TURTLE)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.TURTLE_AMBIENT_LAND, TURTLE_SPEECHES);
        }
        else if (event.getTarget().getType() == EntityType.WOLF)
        {
            sendRandomMessage(event.getEntity(), event.getTarget(), SoundEvents.WOLF_AMBIENT, WOLF_SPEECHES);
        }
    }

    //TODO, fix sendmessage
    private static void sendRandomMessage(Player player, Entity target, SoundEvent sound, String[] speeches)
    {
        int line = player.getRandom().nextInt(speeches.length);
        //player.sendMessage(new StringTextComponent(speeches[line]), UUID.randomUUID());
        target.playSound(sound, 1.0F, 1.0F);
    }
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        tooltip.add(Component.literal("A mask that is said to see into").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("the hearts and minds of others").withStyle(ChatFormatting.GRAY));
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }
}
