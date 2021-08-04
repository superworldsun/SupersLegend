package com.superworldsun.superslegend.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.superworldsun.superslegend.network.NetworkDispatcher;
import com.superworldsun.superslegend.network.message.SetGossipStoneTextMessage;

import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class GossipStoneScreen extends Screen
{
	private final BlockPos gossipStonePos;
	private TextFieldWidget textField;
	
	public GossipStoneScreen(BlockPos pos)
	{
		super(new StringTextComponent(""));
		gossipStonePos = pos;
	}
	
	@Override
	protected void init()
	{
		addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, DialogTexts.GUI_DONE, button -> onClose()));
		addButton(textField = new TextFieldWidget(font, width / 2 - 100, height / 2 - 10, 200, 20, new StringTextComponent("")));
	}
	
	@Override
	public void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks)
	{
		renderBackground(matrix);
		super.render(matrix, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void tick()
	{
		textField.tick();
	}
	
	@Override
	public void onClose()
	{
		NetworkDispatcher.networkChannel.sendToServer(new SetGossipStoneTextMessage(gossipStonePos, textField.getValue()));
		super.onClose();
	}
	
	@Override
	public boolean isPauseScreen()
	{
		return false;
	}
}
