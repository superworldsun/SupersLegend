package com.superworldsun.superslegend.light;

import com.superworldsun.superslegend.mixin.MixinPlayerEntity;

/**
 * Can be used with mixins to add light emitter to vanilla objects<br>
 * Example: {@link MixinPlayerEntity}
 * 
 * @author Daripher
 *
 */
public interface ILightEmitterContainer
{
	AbstractLightEmitter getLightEmitter();
}
