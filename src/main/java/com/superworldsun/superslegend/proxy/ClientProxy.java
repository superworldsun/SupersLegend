package com.superworldsun.superslegend.proxy;

import java.util.concurrent.Callable;

import com.superworldsun.superslegend.client.render.ister.ShadowBlockISTER;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item.Properties;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
	@Override
	public Properties setShadowBlockIster(Properties properties) {
		return properties.setISTER(ClientProxy::createShadowBlockISTER);
	}

	public static Callable<ItemStackTileEntityRenderer> createShadowBlockISTER() {
		return ShadowBlockISTER::new;
	}
}
