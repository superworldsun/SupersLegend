package com.superworldsun.superslegend.client.screen;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.client.screen.widgets.RemoveButton;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.AttemptTeleportationMessage;
import com.superworldsun.superslegend.network.message.RemoveWaypointMessage;
import com.superworldsun.superslegend.waypoints.Waypoint;
import com.superworldsun.superslegend.waypoints.WaypointsProvider;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointsScreen extends Screen
{
	private List<Button> waypointsButtons = new ArrayList<>();
	private List<Waypoint> waypoints = new ArrayList<>();
	private Button removeWaypointButton;
	private Waypoint lastHoveredWaypoint;
	private int buttonsTop;
	
	public WaypointsScreen()
	{
		super(new TranslationTextComponent("screen.waypoints.title"));
	}
	
	@Override
	protected void init()
	{
		waypoints.clear();
		waypointsButtons.clear();
		List<Waypoint> waypoints = WaypointsProvider.get(minecraft.player).getWaypoints();
		buttonsTop = height / 2 - 10 - waypoints.size() / 4 * 25;
		int buttonsLeft = width / 2 - 110;
		
		for (int i = 0; i < waypoints.size(); i++)
		{
			Waypoint waypoint = waypoints.get(i);
			ITextComponent buttonText = new StringTextComponent(waypoint.getName());
			Button waypointButton = new Button(buttonsLeft + i % 2 * 120, buttonsTop + i / 2 * 25, 100, 20, buttonText, button -> attemtTeleporting(waypoint));
			addButton(waypointButton);
			this.waypointsButtons.add(waypointButton);
			this.waypoints.add(waypoint);
		}
		
		addButton(new Button(width - 110, height - 30, 100, 20, DialogTexts.GUI_CANCEL, button -> onClose()));
		addButton(removeWaypointButton = new RemoveButton(-30, -30, button -> removeWaypoint(), this::renderRemoveTooltip));
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		renderBackground(matrixStack);
		drawCenteredString(matrixStack, font, getTitle(), width / 2, buttonsTop - 15, 0xFFFFFF);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		
		for (int i = 0; i < waypointsButtons.size(); i++)
		{
			Button hoveredButton = waypointsButtons.get(i);
			
			if (hoveredButton.isMouseOver(mouseX, mouseY))
			{
				removeWaypointButton.y = hoveredButton.y;
				removeWaypointButton.x = hoveredButton.x - 25 + (i % 2) * 130;
				lastHoveredWaypoint = waypoints.get(i);
			}
		}
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
	
	private void renderRemoveTooltip(Button button, MatrixStack matrixStack, int mouseX, int mouseY)
	{
		renderTooltip(matrixStack, new TranslationTextComponent("screen.waypoints.remove_tooltip" + (hasShiftDown() ? "_shift" : "")), mouseX, mouseY);
	}
	
	private void removeWaypoint()
	{
		if (hasShiftDown())
		{
			NetworkDispatcher.networkChannel.sendToServer(new RemoveWaypointMessage(lastHoveredWaypoint.getStatuePosition()));
		}
	}
	
	private void attemtTeleporting(Waypoint waypoint)
	{
		NetworkDispatcher.networkChannel.sendToServer(new AttemptTeleportationMessage(waypoint.getStatuePosition()));
		onClose();
	}
}
