package com.superworldsun.superslegend.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SetWaypointNameMessage;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class WaypointCreationScreen extends Screen
{
	private final BlockPos waypointPos;
	private TextFieldWidget nameTextField;
	private Button doneButton;
	
	public WaypointCreationScreen(BlockPos pos)
	{
		super(new StringTextComponent("screen.waipoints.title"));
		waypointPos = pos;
	}
	
	@Override
	protected void init()
	{
		addButton(nameTextField = new TextFieldWidget(font, width / 2 - 100, height / 2 - 10, 200, 20, new StringTextComponent("")));
		addButton(doneButton = new Button(width / 2 - 50, height / 2 + 20, 100, 20, DialogTexts.GUI_DONE, button -> onClose()));
		doneButton.active = false;
	}
	
	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		renderBackground(matrix);
		font.drawShadow(matrix, new TranslationTextComponent("screen.waipoints.enter_name"), width / 2 - 100, height / 2 - 25, 0xFFFFFF);
		super.render(matrix, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void tick()
	{
		nameTextField.tick();
		doneButton.active = !nameTextField.getValue().isEmpty();
	}
	
	@Override
	public void onClose()
	{
		if (!nameTextField.getValue().isEmpty())
		{
			NetworkDispatcher.networkChannel.sendToServer(new SetWaypointNameMessage(waypointPos, nameTextField.getValue()));
		}
		
		super.onClose();
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
}
