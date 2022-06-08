package com.superworldsun.superslegend.client.screen;

import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.AttemptTeleportationMessage;
import com.superworldsun.superslegend.waypoints.Waypoint;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointsScreen extends Screen
{
	public WaypointsScreen()
	{
		super(new TranslationTextComponent("screen.waypoints.title"));
	}
	
	@Override
	protected void init()
	{
		List<Waypoint> waypoints = WaypointsProvider.get(minecraft.player).getWaypoints();
		int buttonsTop = height / 2 - 10 - waypoints.size() / 4 * 25;
		
		for (int i = 0; i < waypoints.size(); i++)
		{
			Waypoint waypoint = waypoints.get(i);
			ITextComponent buttonText = new StringTextComponent(waypoint.getName());
			addButton(new Button(width / 2 - 105 + i % 2 * 115, buttonsTop + i * 2 * 25, 100, 20, buttonText, button -> attemtTeleporting(waypoint)));
		}
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	private void attemtTeleporting(Waypoint waypoint)
	{
		NetworkDispatcher.networkChannel.sendToServer(new AttemptTeleportationMessage(waypoint.getStatuePosition()));
		onClose();
	}
}
