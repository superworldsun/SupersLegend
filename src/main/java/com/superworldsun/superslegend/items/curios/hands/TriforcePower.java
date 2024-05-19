package com.superworldsun.superslegend.items.curios.hands;

import com.superworldsun.superslegend.SupersLegendMain;
import com.superworldsun.superslegend.items.customclass.HandsItem;

import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SupersLegendMain.MOD_ID)
public class TriforcePower extends HandsItem {

    public TriforcePower(Properties properties) {
        super(properties);
    }

    //TODO eventually make into curio item with abilties

    /*public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        @SuppressWarnings("unused")
        ItemStack stack = player.getItemInHand(hand);
        {
            BlockPos currentPos = player.blockPosition();
            world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1f, 1f);
            //player.addEffect(new EffectInstance(Effect.byId(20), 44, 0, false, false));
            //player.addEffect(new EffectInstance(Effect.byId(19), 6000, 0, false, false));
            player.addEffect(new EffectInstance(Effect.byId(5), 6000, 0, false, true));
            player.addEffect(new EffectInstance(Effect.byId(3), 6000, 0, false, false));
            player.getCooldowns().addCooldown(this, 100);
        }
        return new ActionResult<>(ActionResultType.PASS, player.getItemInHand(hand));
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> list, ITooltipFlag flag)
    {
        super.appendHoverText(stack, world, list, flag);
        list.add(new StringTextComponent(TextFormatting.RED + "This gives the you Power to destroy"));
        list.add(new StringTextComponent(TextFormatting.GREEN + "Right-click to use"));
    }*/
}