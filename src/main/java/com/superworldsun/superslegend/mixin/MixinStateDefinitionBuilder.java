package com.superworldsun.superslegend.mixin;

import com.superworldsun.superslegend.fluid.LoggedFluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Function;

@Mixin(StateDefinition.Builder.class)
public abstract class MixinStateDefinitionBuilder<O, S extends StateHolder<O, S>> {
    @Shadow @Final private O owner;
    @Shadow @Final private Map<String, Property<?>> properties;

    @Inject(method = "create", at = @At("HEAD"))
    private void superslegend$addLoggedFluidProperty(Function<O, S> stateFactory,
                                                     StateDefinition.Factory<O, S> factory,
                                                     CallbackInfoReturnable<StateDefinition<O, S>> cir) {
        if (owner instanceof Block
                && properties.containsValue(BlockStateProperties.WATERLOGGED)
                && !properties.containsKey(LoggedFluid.PROPERTY.getName())) {
            properties.put(LoggedFluid.PROPERTY.getName(), LoggedFluid.PROPERTY);
        }
    }
}
